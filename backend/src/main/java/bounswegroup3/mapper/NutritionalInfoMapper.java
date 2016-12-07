package bounswegroup3.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.NutritionalInfo;
import io.dropwizard.jackson.Jackson;

public class NutritionalInfoMapper{
	public NutritionalInfo map(String json) throws Exception {
		Double weight = 0.0;
		Double calories = 0.0;
		Double totalFat = 0.0;
		Double saturatedFat = 0.0;
		Double cholesterol = 0.0;
		Double sodium = 0.0;
		Double totalCarbohydrate = 0.0;
		Double dietaryFiber = 0.0;
		Double sugars = 0.0;
		Double protein = 0.0;
		Double potassium = 0.0; // glorious Kazakhstan, all other countries have inferior potassium
		Double phosphorus = 0.0;
		
		ObjectMapper mapper = Jackson.newObjectMapper();
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> read = mapper.readValue(json, LinkedHashMap.class);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList<LinkedHashMap> foods = (ArrayList)read.get("foods");
		
		for(@SuppressWarnings("rawtypes") LinkedHashMap food : foods){
			if(food.get("serving_weight_grams")!=null){
				weight += new Double(food.get("serving_weight_grams").toString());
			}
			
			if(food.get("nf_calories")!=null){
				calories += new Double(food.get("nf_calories").toString());
			}
			
			if(food.get("nf_total_fat")!=null){
				totalFat += new Double(food.get("nf_total_fat").toString());
			}
			
			if(food.get("nf_saturated_fat")!=null){
				saturatedFat += new Double(food.get("nf_saturated_fat").toString());
			}
			
			if(food.get("nf_cholesterol")!=null){
				cholesterol += new Double(food.get("nf_cholesterol").toString());
			}
			
			if(food.get("nf_sodium")!=null){
				sodium += new Double(food.get("nf_sodium").toString());
			}
			
			if(food.get("nf_total_carbohydrate")!=null){
				totalCarbohydrate += new Double(food.get("nf_total_carbohydrate").toString());
			}
			
			if(food.get("nf_dietary_fiber")!=null){
				dietaryFiber += new Double(food.get("nf_dietary_fiber").toString());
			}
			
			if(food.get("nf_sugars")!=null){
				sugars += new Double(food.get("nf_sugars").toString());
			}
			
			if(food.get("nf_protein")!=null){
				protein += new Double(food.get("nf_protein").toString());
			}
			
			if(food.get("nf_potassium")!=null){
				potassium += new Double(food.get("nf_potassium").toString());
			}
			
			
			if(food.get("nf_p")!=null){
				phosphorus += new Double(food.get("nf_p").toString());
			}
		}
		
		return new NutritionalInfo(weight, calories, totalFat, 
				saturatedFat, cholesterol, sodium, totalCarbohydrate, 
				dietaryFiber, sugars, protein, potassium, phosphorus);
	}
}
