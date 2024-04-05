package br.com.magna.pizzaria.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Cart {

	private List<Pizza> items = new ArrayList<>();
	
	public void clear() {
		items.clear();
	}
	
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	public void addItem(Pizza item) {
		items.add(item);
	}
	
	public void removeItem(int itemIndex) {
		items.remove(itemIndex);
	}
	
	public void editItem(int itemIndex, Pizza item) {
		removeItem(itemIndex);
		addItem(item);
	}
	
	public int getItemCount(){
		return items.size();
	}
	
	public Pizza getItemById(int itemId) {
		return items.get(itemId);
	}
	
	public List<Pizza> getItems(){
		return items;
	}
	
	public String getItemListString() {
		StringBuilder sb = new StringBuilder();
		
		int i = 1;
		for(Pizza item : items) {
			sb.append("[" + i + "]" + item.toString() + "\n");
			i++;
		}
		
		return sb.toString();
	}
}


