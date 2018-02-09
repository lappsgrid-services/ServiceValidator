/*
 * 
 */

package edu.brandeis.lapps.validator;

import java.util.ArrayList;


public class Report {

	private final Service service;
	private final ArrayList<String> errors;
	
	protected Report(Service service) {
		this.errors = new ArrayList<>();
		this.service = service;
	}
	
	@Override
	public String toString() {
		return String.format("<Report service=%s>", this.service.id);
	}

	public void prettyPrint() {
		System.out.println(this);
		if (this.errors.size() > 0) 
			System.out.println(this.errorsAsString());
	}

	private String errorsAsString() {
		return "   " + String.join("\n   ", this.errors);
	}

	public void add(String message) {
		this.errors.add(message);
	}

}
