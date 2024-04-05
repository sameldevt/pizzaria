package br.com.magna.pizzaria.model.services;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.magna.pizzaria.model.entities.Customer;
import br.com.magna.pizzaria.model.entities.Flavor;

public class CustomerManager {
	
	private static LocalDate now = LocalDate.now();	
	private static List<Customer> customers = new ArrayList<>();
	
	public static String registerCustomer(Customer customer) {
		if(customers.contains(customer)) {
			return "Usuário já identificado!";
		}
		
		customers.add(customer);
	
		return surprise(customer.getBirthDate());
	}
	
	public static boolean exists(Customer customer) {
		return customers.contains(customer);
	}
	
	private static String surprise(LocalDate date) {
		if(date.getDayOfMonth() == now.getDayOfMonth() && date.getMonthValue() == now.getMonthValue()) {
			StringBuilder sb = new StringBuilder();
			for(Flavor f : MenuManager.getFlavorList()) {
				f.giveDiscount();
			}

			try(Scanner scan = new Scanner(new File("util/happybirthday.txt"))){
				while(scan.hasNextLine()) {
					String line = scan.nextLine();
					
					sb.append(line + "\n");
				}
				
				sb.append("\nParabéns! Você recebeu 10% de desconto em todos os sabores!");
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
			
			return sb.toString();
		}
		else if(now.getYear() - date.getYear() > 100) {
			StringBuilder sb = new StringBuilder();

			try(Scanner scan = new Scanner(new File("util/skull.txt"))){
				while(scan.hasNextLine()) {
					String line = scan.nextLine();
					
					sb.append(line + "\n");
				}	
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
			
			return sb.toString();
		}
		
		return "";
	}
}
