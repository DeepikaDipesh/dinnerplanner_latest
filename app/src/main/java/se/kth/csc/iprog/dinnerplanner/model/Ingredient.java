package se.kth.csc.iprog.dinnerplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient{
	
	String name;
	String unit; // can be empty, for example in case of eggs (there is not unit)
	double amount;
	int id;
	double quantity;


	public Ingredient(String name, double v, String unit, double amount) {
		this.name=name;
		this.quantity=v;
		this.unit=unit;
		this.amount=amount;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
}
