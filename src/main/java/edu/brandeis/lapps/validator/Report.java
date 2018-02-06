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
	
}
