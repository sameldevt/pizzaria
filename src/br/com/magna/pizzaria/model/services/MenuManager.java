package br.com.magna.pizzaria.model.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.magna.pizzaria.model.entities.Flavor;

public class MenuManager {
	private static List<Flavor> flavorList = fillFlavorMenu();
			
	public static List<Flavor> getFlavorList(){
		return flavorList;
	}
	
	public static void printMenu() {
		System.out.println("\nCARDÁPIO");		
		for(Flavor f : flavorList) {
			System.out.println(f + "\n");
		}
	}
	
	private static List<Flavor> fillFlavorMenu() {	
		List<Flavor> tempPizzaList = new ArrayList<>();
		
		try(Scanner scan = new Scanner(new File("util/pizza.txt"))){
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				
				String[] fields = line.split(";");
				
				String name = fields[0];
				String ingredients = fields[1];
				double price = Double.valueOf(fields[2]);
				
				Flavor pizza = new Flavor(name, ingredients, price);
				
				tempPizzaList.add(pizza);
			}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		return tempPizzaList;
	}
	
}
