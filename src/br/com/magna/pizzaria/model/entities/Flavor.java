package br.com.magna.pizzaria.model.entities;

public class Flavor {
	
	private String name;
	private String ingredients;
	private double price;
	
	public Flavor(String name, String ingredients, double price) {
		this.name = name;
		this.ingredients = ingredients;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getIngredients() {
		return ingredients;
	}

	public double getPrice() {
		return price;
	}

	public void giveDiscount() {
		price -= price * 0.1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Sabor: " + name);
		sb.append("\nIngredientes: " + ingredients);
		sb.append("\nPreço: R$" + String.format("%.2f", price));
			
		return sb.toString();
	}
}

