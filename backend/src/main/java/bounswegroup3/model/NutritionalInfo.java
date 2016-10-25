package bounswegroup3.model;

/**
 * Represents the total nutritions of a meal. It is collected from an external source
 * and is never consumed by the application. However, some API calls require it
 * {"weight":Float, "calories":Float, "totalFat":Float, "saturatedFat":Float,
 *  "cholesterol":Float, "sodium":Float, "totalCarbohydrate":Float, "dietaryFiber":Float,
 *  "sugars":Float, "protein":Float, "potassium":Float, "phosphorus":Float}
 *  The macronutrient(total weight/fat/CH/protein) information is in grams
 *  and the micronutrients are in miligrams.
 */
public class NutritionalInfo {
	private Double weight;
	private Double calories;
	private Double totalFat;
	private Double saturatedFat;
	private Double cholesterol;
	private Double sodium;
	private Double totalCarbohydrate;
	private Double dietaryFiber;
	private Double sugars;
	private Double protein;
	private Double potassium; // glorious Kazakhstan, all other countries have inferior potassium
	private Double phosphorus;
	
	public NutritionalInfo() {
		super();
	}
	
	public NutritionalInfo(Double weight, Double calories, Double totalFat, Double saturatedFat, Double cholesterol,
			Double sodium, Double totalCarbohydrate, Double dietaryFiber, Double sugars, Double protein,
			Double potassium, Double phosphorus) {
		super();
		this.weight = weight;
		this.calories = calories;
		this.totalFat = totalFat;
		this.saturatedFat = saturatedFat;
		this.cholesterol = cholesterol;
		this.sodium = sodium;
		this.totalCarbohydrate = totalCarbohydrate;
		this.dietaryFiber = dietaryFiber;
		this.sugars = sugars;
		this.protein = protein;
		this.potassium = potassium;
		this.phosphorus = phosphorus;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getCalories() {
		return calories;
	}

	public void setCalories(Double calories) {
		this.calories = calories;
	}

	public Double getTotalFat() {
		return totalFat;
	}

	public void setTotalFat(Double totalFat) {
		this.totalFat = totalFat;
	}

	public Double getSaturatedFat() {
		return saturatedFat;
	}

	public void setSaturatedFat(Double saturatedFat) {
		this.saturatedFat = saturatedFat;
	}

	public Double getCholesterol() {
		return cholesterol;
	}

	public void setCholesterol(Double cholesterol) {
		this.cholesterol = cholesterol;
	}

	public Double getSodium() {
		return sodium;
	}

	public void setSodium(Double sodium) {
		this.sodium = sodium;
	}

	public Double getTotalCarbohydrate() {
		return totalCarbohydrate;
	}

	public void setTotalCarbohydrate(Double totalCarbohydrate) {
		this.totalCarbohydrate = totalCarbohydrate;
	}

	public Double getDietaryFiber() {
		return dietaryFiber;
	}

	public void setDietaryFiber(Double dietaryFiber) {
		this.dietaryFiber = dietaryFiber;
	}

	public Double getSugars() {
		return sugars;
	}

	public void setSugars(Double sugars) {
		this.sugars = sugars;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(Double protein) {
		this.protein = protein;
	}

	public Double getPotassium() {
		return potassium;
	}

	public void setPotassium(Double potassium) {
		this.potassium = potassium;
	}

	public Double getPhosphorus() {
		return phosphorus;
	}

	public void setPhosphorus(Double phosphorus) {
		this.phosphorus = phosphorus;
	}
}
