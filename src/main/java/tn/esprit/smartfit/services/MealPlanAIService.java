// MealPlanAIService.java
package tn.esprit.smartfit.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.smartfit.entities.*;
import tn.esprit.smartfit.ripositories.AlimentsRepository;

import java.time.LocalDate;
import java.util.*;
@Service
public class MealPlanAIService {

    private final String AI_API_URL = "http://localhost:5000/generate_meal_plan";

    @Autowired
    private AlimentsRepository alimentsRepository;

    public PlanAlimentaire generateMealPlan(Client client) {
        try {
            System.out.println("🔄 === CALLING PYTHON AI ===");
            System.out.println("📤 Client: " + client.getNom() + ", " + client.getObjectif());

            // Create request data
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("id", client.getId());
            requestData.put("nom", client.getNom());
            requestData.put("age", client.getAge());
            requestData.put("poids", client.getPoids());
            requestData.put("objectif", client.getObjectif());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestData, headers);

            RestTemplate restTemplate = new RestTemplate();

            System.out.println("🔗 Making POST request to: " + AI_API_URL);

            // Get response as String first to debug
            ResponseEntity<String> stringResponse = restTemplate.exchange(
                    AI_API_URL, HttpMethod.POST, entity, String.class);

            System.out.println("📥 Raw Response: " + stringResponse.getBody());
            System.out.println("📥 Status Code: " + stringResponse.getStatusCode());

            if (stringResponse.getStatusCode() == HttpStatus.OK && stringResponse.getBody() != null) {
                // Parse JSON manually
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> aiResponse = mapper.readValue(stringResponse.getBody(), Map.class);

                System.out.println("✅ AI Success: " + aiResponse.get("success"));

                if (Boolean.TRUE.equals(aiResponse.get("success"))) {
                    System.out.println("🎉 AI GENERATION SUCCESSFUL!");
                    return convertAIPlanToEntity(client, (Map<String, Object>) aiResponse.get("meal_plan"));
                } else {
                    System.out.println("❌ AI Error: " + aiResponse.get("error"));
                }
            }

        } catch (Exception e) {
            System.out.println("💥 EXCEPTION in generateMealPlan: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("🔄 FALLING BACK TO BASIC PLAN");
        return generateBasicPlan(client);
    }

    private PlanAlimentaire convertAIPlanToEntity(Client client, Map<String, Object> aiPlan) {
        try {
            PlanAlimentaire plan = new PlanAlimentaire();
            plan.setNom("Plan " + client.getObjectif() + " - Généré par IA");
            plan.setDateDebut(LocalDate.now());
            plan.setDateFin(LocalDate.now().plusDays(7));
            plan.setGenereParIA(true);
            plan.setTypePlan(client.getObjectif());
            plan.setClient(client);

            // Set nutrition targets
            if (aiPlan.containsKey("nutrition_targets")) {
                Map<String, Object> nutritionTargets = (Map<String, Object>) aiPlan.get("nutrition_targets");
                plan.setTotalCaloriesCible(((Number) nutritionTargets.get("total_calories")).doubleValue());
                plan.setProteinesCible(((Number) nutritionTargets.get("proteins_g")).doubleValue());
                plan.setGlucidesCible(((Number) nutritionTargets.get("carbs_g")).doubleValue());
                plan.setLipidesCible(((Number) nutritionTargets.get("fats_g")).doubleValue());
            }

            // Create meals WITH ACTUAL FOODS
            List<Repas> repasList = extractRealMealsFromAIResponse(aiPlan, plan);
            plan.setRepas(repasList);

            System.out.println("✅ Converted AI plan with " + repasList.size() + " meals");
            return plan;

        } catch (Exception e) {
            System.out.println("❌ Error converting AI plan: " + e.getMessage());
            throw new RuntimeException("Failed to convert AI plan", e);
        }
    }

    // ADD THE MISSING METHODS:

    private List<Repas> extractRealMealsFromAIResponse(Map<String, Object> aiPlan, PlanAlimentaire plan) {
        List<Repas> repasList = new ArrayList<>();

        try {
            System.out.println("=== DEBUG: Starting AI Response Processing ===");

            // Get first day's plan from AI response
            List<Map<String, Object>> dailyPlans = (List<Map<String, Object>>) aiPlan.get("daily_plan");
            System.out.println("Daily plans count: " + (dailyPlans != null ? dailyPlans.size() : "NULL"));

            if (dailyPlans == null || dailyPlans.isEmpty()) {
                System.out.println("DEBUG: No daily plans found, using basic meals");
                return createBasicMeals(plan);
            }

            Map<String, Object> firstDay = dailyPlans.get(0);
            Map<String, Object> meals = (Map<String, Object>) firstDay.get("meals");
            System.out.println("Meals found: " + (meals != null ? meals.keySet() : "NULL"));

            // Map AI meal types to your French meal types
            Map<String, String> mealTypeMapping = Map.of(
                    "breakfast", "Petit-déjeuner",
                    "lunch", "Déjeuner",
                    "dinner", "Dîner",
                    "snack", "Snack"
            );

            for (Map.Entry<String, String> entry : mealTypeMapping.entrySet()) {
                String aiMealType = entry.getKey();
                String yourMealType = entry.getValue();

                if (meals.containsKey(aiMealType)) {
                    Map<String, Object> mealData = (Map<String, Object>) meals.get(aiMealType);
                    List<Map<String, Object>> foods = (List<Map<String, Object>>) mealData.get("foods");

                    System.out.println("Processing " + aiMealType + " -> " + yourMealType + ", foods count: " + foods.size());

                    Repas repas = createRepasWithRealFoods(yourMealType, foods, plan);
                    if (repas != null) {
                        repasList.add(repas);
                        System.out.println("Successfully created repas with " + repas.getComposants().size() + " composants");
                    }
                } else {
                    System.out.println("Meal type not found: " + aiMealType);
                }
            }

        } catch (Exception e) {
            System.out.println("Error extracting meals from AI: " + e.getMessage());
            e.printStackTrace();
            return createBasicMeals(plan);
        }

        System.out.println("Total repas created: " + repasList.size());
        return repasList;
    }

    private Repas createRepasWithRealFoods(String mealType, List<Map<String, Object>> foods, PlanAlimentaire plan) {
        if (foods == null || foods.isEmpty()) {
            System.out.println("No foods provided for meal: " + mealType);
            return null;
        }

        Repas repas = new Repas();
        repas.setType(mealType);
        repas.setPlanAlimentaire(plan);

        List<ComposantRepas> composants = new ArrayList<>();
        double totalCalories = 0;
        double totalProteins = 0;
        double totalCarbs = 0;
        double totalFats = 0;

        for (Map<String, Object> foodData : foods) {
            ComposantRepas composant = createComposantFromFoodData(foodData, repas);
            if (composant != null) {
                composants.add(composant);

                // Calculate totals
                totalCalories += composant.getCalories();
                totalProteins += composant.getProteines();
                totalCarbs += composant.getGlucides();
                totalFats += composant.getLipides();
            }
        }

        repas.setComposants(composants);
        repas.setCaloriesTotales(totalCalories);
        repas.setProteinesTotales(totalProteins);
        repas.setGlucidesTotales(totalCarbs);
        repas.setLipidesTotales(totalFats);

        System.out.println("Created repas '" + mealType + "' with " + composants.size() + " foods, total calories: " + totalCalories);
        return repas;
    }

    private ComposantRepas createComposantFromFoodData(Map<String, Object> foodData, Repas repas) {
        try {
            String foodName = (String) foodData.get("name");
            double portionSize = ((Number) foodData.get("portion_size")).doubleValue();

            System.out.println("Creating composant for: " + foodName + " (" + portionSize + "g)");

            // Try to find existing food in database first
            Optional<Aliments> existingAliment = alimentsRepository.findByNomContainingIgnoreCase(foodName);

            Aliments aliment;
            if (existingAliment.isPresent()) {
                // Use existing food from database
                aliment = existingAliment.get();
                System.out.println("Found existing aliment: " + aliment.getNom());
            } else {
                // Create new Aliments entry from AI data
                aliment = new Aliments();
                aliment.setNom(foodName);
                aliment.setQuantite(100); // Base quantity for 100g calculations

                // Set nutrition data from AI response
                if (foodData.containsKey("calories_per_100g")) {
                    double caloriesPer100g = ((Number) foodData.get("calories_per_100g")).doubleValue();
                    aliment.setCalories(caloriesPer100g);
                } else {
                    // Estimate if not provided
                    aliment.setCalories(150.0);
                }

                if (foodData.containsKey("proteins")) {
                    double proteinsPer100g = ((Number) foodData.get("proteins")).doubleValue();
                    aliment.setProteines(proteinsPer100g);
                } else {
                    aliment.setProteines(10.0);
                }

                if (foodData.containsKey("carbs")) {
                    double carbsPer100g = ((Number) foodData.get("carbs")).doubleValue();
                    aliment.setGlucides(carbsPer100g);
                } else {
                    aliment.setGlucides(20.0);
                }

                if (foodData.containsKey("fats")) {
                    double fatsPer100g = ((Number) foodData.get("fats")).doubleValue();
                    aliment.setLipides(fatsPer100g);
                } else {
                    aliment.setLipides(5.0);
                }

                // Save the new food to database
                aliment = alimentsRepository.save(aliment);
                System.out.println("Created new aliment: " + aliment.getNom());
            }

            // Create ComposantRepas
            ComposantRepas composant = new ComposantRepas();
            composant.setAliment(aliment);
            composant.setQuantite(portionSize);
            composant.setDescription(foodName + " (Portion: " + portionSize + "g)");
            composant.setModeCuisson("GRILLE");
            composant.setRepas(repas);

            System.out.println("Composant created - Calories: " + composant.getCalories() +
                    ", Proteins: " + composant.getProteines());

            return composant;

        } catch (Exception e) {
            System.out.println("❌ Error creating composant for food: " + foodData + " - " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private List<Repas> createBasicMeals(PlanAlimentaire plan) {
        System.out.println("Creating basic meals as fallback");
        return Arrays.asList(
                createBasicRepas("Petit-déjeuner", plan.getTotalCaloriesCible() * 0.25, plan),
                createBasicRepas("Déjeuner", plan.getTotalCaloriesCible() * 0.35, plan),
                createBasicRepas("Dîner", plan.getTotalCaloriesCible() * 0.30, plan),
                createBasicRepas("Snack", plan.getTotalCaloriesCible() * 0.10, plan)
        );
    }

    private Repas createBasicRepas(String type, double calories, PlanAlimentaire plan) {
        Repas repas = new Repas();
        repas.setType(type);
        repas.setCaloriesTotales(calories);
        repas.setProteinesTotales(calories * 0.3 / 4);
        repas.setGlucidesTotales(calories * 0.5 / 4);
        repas.setLipidesTotales(calories * 0.2 / 9);
        repas.setPlanAlimentaire(plan);
        return repas;
    }

    private String convertMealType(String aiMealType) {
        switch (aiMealType.toLowerCase()) {
            case "breakfast": return "Petit-déjeuner";
            case "lunch": return "Déjeuner";
            case "dinner": return "Dîner";
            case "snack": return "Snack";
            default: return aiMealType;
        }
    }

    private PlanAlimentaire generateBasicPlan(Client client) {
        PlanAlimentaire plan = new PlanAlimentaire();
        plan.setNom("Plan " + client.getObjectif() + " - Basique");
        plan.setDateDebut(LocalDate.now());
        plan.setDateFin(LocalDate.now().plusDays(7));
        plan.setGenereParIA(false);
        plan.setTypePlan(client.getObjectif());
        plan.setClient(client);

        double baseCalories = client.getPoids() * 25;
        switch (client.getObjectif()) {
            case "MAIGRIR": baseCalories *= 0.85; break;
            case "PRISE_MASSE": baseCalories *= 1.15; break;
        }

        plan.setTotalCaloriesCible(baseCalories);
        plan.setProteinesCible(baseCalories * 0.3 / 4);
        plan.setGlucidesCible(baseCalories * 0.5 / 4);
        plan.setLipidesCible(baseCalories * 0.2 / 9);

        List<Repas> repasList = createBasicMeals(plan);
        plan.setRepas(repasList);
        return plan;
    }
}