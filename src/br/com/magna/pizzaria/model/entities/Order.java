package br.com.magna.pizzaria.model.entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {
	
	private Cart cart;
	private Customer customer;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	public Order(Cart cart, Customer customer) {
		this.cart = cart;
		this.customer = customer;
	}
	
	public void addItem(Pizza item) {
		cart.addItem(item);
	}
	
	public void removeItem(int itemIndex) {
		cart.removeItem(itemIndex);
	}
	
	public void editItem(int itemIndex, Pizza item) {
		cart.editItem(itemIndex, item);
	}

	public void clearCart() {
		cart.clear();
	}
	
	public String getCartItems() {
		return cart.getItemListString();
	}
	
	public boolean isCartEmpty() {
		return cart.isEmpty();
	}

	public Integer getCartItemCount() {
		return cart.getItemCount();
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public double getTotal() {
		return calculateTotal();
	}
	
	public List<Pizza> getItemList(){
		return cart.getItems();
	}
	
	public Pizza getItemById(int itemToEditIndex) {
		return cart.getItemById(itemToEditIndex);
	}
	
	private double calculateTotal() {
		double totalCost = 0;
		
		for(Pizza item : cart.getItems()) {
			totalCost += item.getPizzaPrice();
		}
		
		return totalCost;
	}
	
	private void logReceipt(String receipt) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		String receiptName = "util/receipts/" + LocalDateTime.now().format(dtf) + ".txt";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(receiptName))) {
            writer.write(receipt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
	public String generateReceipt() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n\t\t\t     PIZZARIA GENESIS\n");
		sb.append("\t\t    R Prudente de Morais, 468 - Casa Grande\n");
		sb.append("\t\t\tCEP: 09960-500 - DIADEMA-SP\n");
		sb.append("------------------------------------------------------------------------------\n");
		sb.append(LocalDateTime.now().format(formatter) + "\t\t\t\t\t\t COD:123456789\n");
		sb.append("\t\t\t\tCUPOM FISCAL\n");
		sb.append("QTD UN. VL UNIT(R$)\nST\t\t\t\t\t\t\t\t   VL TOTAL(R$)\n");
		sb.append("------------------------------------------------------------------------------\n");
		
		for(Pizza pizza : cart.getItems()) {
			sb.append(pizza + "\n");
		}
		
		sb.append("[" + cart.getItemCount() + "] ITEM(S)\t\t\t\t\t\t\t      R$" + String.format("%.2f", calculateTotal()) + "\n");
		sb.append("------------------------------------------------------------------------------\n");
		sb.append(customer + "\n");
		sb.append(customer.isBirthday() ? "Aproveite o seu dia!" : "");
		logReceipt(sb.toString());
		return sb.toString();
	}
}
