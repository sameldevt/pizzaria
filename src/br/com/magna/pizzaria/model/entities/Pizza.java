package br.com.magna.pizzaria.model.entities;

import java.util.List;

import br.com.magna.pizzaria.model.enums.PizzaType;

public class Pizza{

	private PizzaType pizzaType;
	private List<Flavor> flavors;
	
	public Pizza(PizzaType pizzaType, List<Flavor> flavors) {
		this.pizzaType = pizzaType;
		this.flavors = flavors;
	}	
	
	public void selectPizzaType(PizzaType pizzaType) {
		this.pizzaType = pizzaType;
		
		try {
			if(this.pizzaType == PizzaType.GRANDE || this.pizzaType == PizzaType.BROTO) {
				flavors.remove(1);
			}
		}catch(NullPointerException e) {			
		}
	}
	
	public void editFlavor(Flavor flavor, int flavorIndex) {
		flavors.remove(flavorIndex);
		flavors.add(flavor);		
	}
	
	public void selectFlavor(Flavor flavor) {
		if(flavors.size() == 2) {
			System.out.println("Você não pode adicionar mais sabores!");
			return;
		}
		
		flavors.add(flavor);
	}
	
	public List<Flavor> getFlavors() {
		return this.flavors;
	}
	
	public Flavor getFlavorById(int flavorIndex) {
		return flavors.get(flavorIndex);
	}
	
	public PizzaType getPizzaType() {
		return pizzaType;
	}
	
	public double getPizzaPrice() {
		return calculatePizzaPrice();
	}
	
	private double calculatePizzaPrice() {
		if(pizzaType == PizzaType.GRANDE_DOIS_SABORES) {
			return flavors.stream().mapToDouble(Flavor::getPrice).max().orElse(0.0);
		}
		else if(pizzaType == PizzaType.BROTO_DOIS_SABORES) {
			return (flavors.stream().mapToDouble(Flavor::getPrice).max().orElse(0.0)) / 2;
		}
		else if(pizzaType == PizzaType.GRANDE) {
			return flavors.get(0).getPrice();
		}
		else if(pizzaType == PizzaType.BROTO) {
			return flavors.get(0).getPrice() / 2;
		}
		
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("[" + pizzaType + "][VALOR FINAL: R$" + String.format("%.2f", calculatePizzaPrice()) + "]\n");
		
		if(flavors.size() > 1 && flavors.get(0).toString().equals(flavors.get(1).toString())) {
			sb.append(flavors.getFirst() + "\n");
			return sb.toString();
		}	
		
		int itemIndex = 1;
		for(Flavor flavor : flavors) {
			sb.append("[" + itemIndex + "]" + flavor + "\n");
			itemIndex++;
		}
		
		return sb.toString();
	}
}
