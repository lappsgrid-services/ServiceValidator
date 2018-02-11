package edu.brandeis.lapps.validator;

import java.util.ArrayList;


public class Report {

	private final Service service;
	private final ArrayList<String[]> messages;
	
	protected Report(Service service) {
		this.service = service;
		this.messages = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return String.format("<Report service=%s>", this.service.id);
	}

	public void prettyPrint() {
		System.out.println(this);
		if (this.messages.size() > 0) {
			for (String[] error : this.messages) {
				System.out.println("   " + error[1] + " (" + error[0] + ")");
			}
		}
	}

	public void compact() {
		// TODO: this should compact the error messages, for example, for two
		// errors that have the same string but a different file name; the
		// question is whether this is a good idea, maybe we want to keep all
		// the files for which an error occurred
	}
	
	public void add(String inputFile, String message) {
		//System.out.println("   " + message);
		this.messages.add(new String[]{inputFile, message});
	}

}
