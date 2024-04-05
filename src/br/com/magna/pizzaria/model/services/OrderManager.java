package br.com.magna.pizzaria.model.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.com.magna.pizzaria.model.entities.Cart;
import br.com.magna.pizzaria.model.entities.Customer;
import br.com.magna.pizzaria.model.entities.Flavor;
import br.com.magna.pizzaria.model.entities.Order;
import br.com.magna.pizzaria.model.entities.Pizza;
import br.com.magna.pizzaria.model.enums.PizzaType;

public class OrderManager {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static Scanner scan = new Scanner(System.in);
	
	
	private static List<PizzaType> pizzaTypes = Arrays.asList(PizzaType.values());
	private static List<Flavor> flavorList = MenuManager.getFlavorList();;
	
	private static Order order;
	
	private static void waitAndClear(int waitTime) {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < 50; i++) System.out.println();
	}
	
	private static void clear() {
		for(int i = 0; i < 50; i++) System.out.println();
	}
	
	public static void start() {
		int pos = 0;
		
		System.out.println("SEJA BEM-VINDO À PIZZARIA GENESIS");
	
		while(true) {
			switch(pos) {
			case 0:
				pos = mainMenu();
				break;
			case 1:				
				pos = registerClient();
				break;
			case 2:
				pos = printMenu();
				break;
			case 3:
				pos = selectItem();
				break;
			case 4:
				pos = viewCart();
				break;	
			case 5:
				pos = removeItemFromCart();
				break;
			case 6:
				pos = editItemFromCart();
				break;
			case 7:
				pos = finishOrder();
				break;
			default:
				System.out.println("Opção inválida!");
			}
		}
	}
	
	private static int printMenu() {
		MenuManager.printMenu();
		return 0;
	}

	private static int mainMenu() {
		System.out.println("\nMENU PRINCIPAL");
		System.out.println("1 - Identificar cliente");
		System.out.println("2 - Ver cardápio");
		System.out.println("3 - Escolher pizza");
		System.out.print("Escolha uma opção: ");
		
		try {
			return scan.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Opção inválida!");
			return 0;
		}
	}
	
	private static int registerClient() {
		if(order != null) {
			System.out.println("CLIENTE JÁ IDENTIFICADO!");
			waitAndClear(1000);
			return 0;
		};
		
		scan.nextLine();
		System.out.println("\nIDENTIFICAR CLIENTE");
		System.out.print("Nome: ");
		String clientName = scan.nextLine();
		System.out.print("Endereço: ");
		String clientAddress = scan.nextLine();
		
		LocalDate birthDate;
		while(true) {
			System.out.print("Data de nascimento (dd/mm/yyyy): ");
			String clientBirthDate = scan.nextLine();
			
			try {
				birthDate = LocalDate.parse(clientBirthDate, formatter);	
				break;
			}catch(DateTimeParseException e ) {
				System.out.println("Formato de data inválido! Por favor, use dd/mm/yyyy");
				waitAndClear(1000);
				continue;
			}
		}
		Customer customer = new Customer(clientName, clientAddress, birthDate);
		
		
		String registerMessage = CustomerManager.registerCustomer(customer);
		
		System.out.println(registerMessage);
		order = new Order(new Cart(), customer);
		
		System.out.println("CLIENTE IDENTIFICADO COM SUCESSO!");
		waitAndClear(1000);
		return 0;
	}

	private static int selectItem() {
		if(order == null) {
			System.out.println("\nCLIENTE NÃO IDENTIFICADO");
			System.out.println("Você precisa se identificar para fazer um pedido!\n");
			waitAndClear(1500);
			return 0;
		};
		
		int typeIndex = selectType();
		
		List<Flavor> flavors = selectFlavors(typeIndex);	
		
		PizzaType pizzaType = pizzaTypes.get(typeIndex - 1);
		
		Pizza pizza = new Pizza(pizzaType, flavors);
		
		order.addItem(pizza);				
	
		return 4;
	}
	
	private static int selectType() {
		int typeIndex;
		
		while(true) {
			System.out.println("\nESCOLHER PIZZA");
			System.out.println("1 - Grande, 8 pedaços");
			System.out.println("2 - Grande. 8 pedaços. dois sabores");
			System.out.println("3 - Broto, 4 pedaços");
			System.out.println("4 - Broto, 4 pedaços, dois sabores");
			
			try {
				typeIndex = scan.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("Opção inválida!");
				waitAndClear(500);
				continue;
			}
			
			if(typeIndex < 1 || typeIndex > 4) {
				System.out.println("Opção inválida!");
				waitAndClear(500);
				scan.nextLine();
				continue;
			}
			break;
		}
		
		return typeIndex;
	}
	
	private static void printPizzaList() {
		int itemPos = 1;
		for(Flavor flavor : flavorList) {
			System.out.println("[" + itemPos + "]" + flavor);
			System.out.println();
			itemPos++;
		}
	}
	
	private static List<Flavor> selectFlavors(int type) {
		List<Flavor> list = new ArrayList<>();
		int flavorIndex;
		
		while(true) {
			System.out.println("\nESCOLHER SABOR");
			printPizzaList();
			
			System.out.print("Escolha algum sabor: ");
			
			try {
				flavorIndex = scan.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("Opção inválida!");
				waitAndClear(500);
				continue;
			}
			
			if(flavorIndex < 1 || flavorIndex > flavorList.size()) {
				System.out.println("Opção inválida!");
				waitAndClear(500);
				scan.nextLine();
				continue;
			}
			break;
		}
		
		list.add(flavorList.get(flavorIndex - 1));
			
		if(type == 2 || type == 4) {
			while(true) {
				System.out.print("Escolha outro sabor: ");
				
				try {
					flavorIndex = scan.nextInt();
				}catch(InputMismatchException e) {
					System.out.println("Opção inválida!");
					waitAndClear(500);
					continue;
				}
				
				if(flavorIndex < 1 || flavorIndex > flavorList.size()) {
					System.out.println("Opção inválida!");
					scan.nextLine();
					continue;
				}
				break;
			}
			
			list.add(flavorList.get(flavorIndex - 1));
		}
		
		System.out.println("ITEM ADICIONADO COM SUCESSO!");
		waitAndClear(1000);
		return list;
	}
	
	private static int viewCart() {
		System.out.println("\nCARRINHO");
		System.out.println(order.getCartItems());
		System.out.println("TOTAL R$" + String.format("%.2f", order.getTotal()) + "\n");
	
		System.out.println("1 - Concluir pedido");
		System.out.println("2 - Adicionar mais itens");
		System.out.println("3 - Remover item");
		System.out.println("4 - Editar item");
		
		int option = scan.nextInt();
		
		switch(option) {
		case 1:
			return 7;
		case 2:
			return 3;
		case 3:
			return 5;
		case 4:
			return 6;
		default:
			System.out.println("Opção inválida!");
			waitAndClear(500);
			break;
		}
		
		return 4;
	}
	
	private static int removeItemFromCart() {
		clear();
		if(order.isCartEmpty()) {
			System.out.println("Não é possível remover itens pois seu carrinho está vazio!");
			waitAndClear(1000);
			return 4;
		}
		
		String itemToRemove;
		
		System.out.println("\nREMOVER");
		
		while(true) {
			System.out.println(order.getCartItems());
			System.out.println("Qual item deseja remover?");
			System.out.println("(Digite x para cancelar)");
			System.out.print("> ");
			scan.nextLine();
			itemToRemove = scan.nextLine();
			
			if(itemToRemove.charAt(0) == 'x') {
				return 4;
			}
			
			if(Integer.valueOf(itemToRemove) > order.getCartItemCount()) {
				System.out.println("Opção inválida!");
				waitAndClear(500);
				continue;
			}
			
			break;
		}

		order.removeItem(Integer.valueOf(itemToRemove) - 1);
		System.out.println("Item removido com sucesso!");
		waitAndClear(1000);
		return 4;
	}
	
	private static int editItemFromCart() {
		clear();
		if(order.isCartEmpty()) {
			System.out.println("Não é possível editar itens pois seu carrinho está vazio!");
			waitAndClear(1000);
			return 4;
		}
		
		System.out.println("\nEDITAR");
		String itemToEdit;
		
		while(true) {
			System.out.println(order.getCartItems());
			System.out.println("Qual item deseja editar? ");	
			System.out.println("(Digite x para cancelar)");
			System.out.print("> ");
			scan.nextLine();
			itemToEdit = scan.nextLine();
			
			if(itemToEdit.charAt(0) == 'x') {
				return 4;
			}
			
			if(Integer.valueOf(itemToEdit) < 0 ||
					Integer.valueOf(itemToEdit) > order.getCartItemCount()) {
				System.out.println("Opção inválida!");
				scan.nextLine();
				waitAndClear(500);
				continue;
			}
			
			break;
		}
		
		System.out.println("Qual característica deseja editar?");
		System.out.println("1 - Tipo da pizza");
		System.out.println("2 - Sabor(es)");
		System.out.println("3 - Cancelar");
		System.out.print("> ");
		int param = scan.nextInt();
		
		switch(param) {
		case 1: {
			int editedPizzaTypeIndex;
			while(true) {
				System.out.println("\nESCOLHER PIZZA");
				System.out.println("1 - Grande, 8 pedaços");
				System.out.println("2 - Grande. 8 pedaços. dois sabores");
				System.out.println("3 - Broto, 4 pedaços");
				System.out.println("4 - Broto, 4 pedaços, dois sabores");
				
				try {
					editedPizzaTypeIndex = scan.nextInt();
				}catch(InputMismatchException e) {
					System.out.println("Opção inválida!");
					waitAndClear(500);
					continue;
				}
				
				if(editedPizzaTypeIndex < 1 || editedPizzaTypeIndex > 4) {
					System.out.println("Opção inválida!");
					waitAndClear(500);
					scan.nextLine();
					continue;
				}
				break;
			}
			
			PizzaType type = pizzaTypes.get(editedPizzaTypeIndex - 1);
			
			Pizza pizza = order.getItemById(Integer.valueOf(itemToEdit) - 1);
			pizza.selectPizzaType(type);
		
			order.editItem(Integer.valueOf(itemToEdit) - 1, pizza); 
			return 4;
		}
		case 2: {
			int pizzaFlavor;
			while(true) {
				System.out.println(order.getItemById(Integer.valueOf(itemToEdit) - 1));
				System.out.print("Qual sabor deseja alterar? ");
				try {
					pizzaFlavor = scan.nextInt();
				}catch(InputMismatchException e ) {
					System.out.println("Opção inválida!");
					waitAndClear(500);
					continue;
				}
				
				if(pizzaFlavor > 2 || pizzaFlavor < 0) {
					System.out.println("Opção inválida!");
					waitAndClear(500);
					continue;
				}
				
				break;
			}
			
			int editedFlavorIndex;
			while(true) {
				printPizzaList();
				System.out.println("\nESCOLHER SABOR");
				System.out.print("Escolha algum sabor: ");
				
				try {
					editedFlavorIndex = scan.nextInt();
				}catch(InputMismatchException e) {
					System.out.println("Opção inválida!");
					waitAndClear(500);
					continue;
				}
				
				if(editedFlavorIndex < 1 || editedFlavorIndex > flavorList.size() - 1) {
					System.out.println("Opção inválida!");
					waitAndClear(500);
					scan.nextLine();
					continue;
				}
				break;
			}
			
			Pizza pizza = order.getItemById(Integer.valueOf(itemToEdit) - 1);
			Flavor newFlavor = flavorList.get(editedFlavorIndex - 1);
			
			pizza.editFlavor(newFlavor, pizzaFlavor - 1);
			
			order.editItem(Integer.valueOf(itemToEdit) - 1, pizza);
				
			return 4;
		}
			
		case 3: return 4;
		default:
			System.out.println("Opção inválida!");
			waitAndClear(500);
			return 4;
		}		
	}
	
	private static int finishOrder() {
		clear();
		if(order.isCartEmpty()) {
			System.out.println("Não é possível concluir seu pedido pois seu carrinho está vazio!");
			waitAndClear(500);
			return 4;
		}
		
		System.out.println(order.generateReceipt());
		System.out.println("Agradecemos pelo pedido!");
		scan.nextLine();
		
		order.clearCart();
		return 0;
	}

	public static List<Flavor> getFlavorList(){
		return flavorList;
	}
}
