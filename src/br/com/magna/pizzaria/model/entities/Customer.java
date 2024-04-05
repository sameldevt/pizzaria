package br.com.magna.pizzaria.model.entities;

import java.time.LocalDate;

public class Customer {
	
	private String name;
	private String address;
	private LocalDate birthDate;
	
	public Customer(String name, String address, LocalDate birthDate) {
		this.name = name;
		this.address = address;
		this.birthDate = birthDate;
	}
	
	public boolean isBirthday() {
		if(birthDate.getDayOfMonth() == LocalDate.now().getDayOfMonth() && 
				birthDate.getMonthValue() == LocalDate.now().getMonthValue()) {
			return true;
		}
		
		return false;
	}
	
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	@Override
	public String toString() {
		return "Nome: " + name + "\n" + "Endereço: " + address; 
	}
}
