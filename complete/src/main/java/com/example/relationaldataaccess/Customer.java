package com.example.relationaldataaccess;

public class Customer {
	public int id;
	public String firstName, lastName;
	public int index;

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%d, firstName='%s', lastName='%s', index='%d']",
				id, firstName, lastName, index);
	}
}
