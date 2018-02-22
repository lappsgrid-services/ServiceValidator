package edu.brandeis.lapps.validator;

import edu.brandeis.lapps.validator.tests.ServiceTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ServiceValidator {

	private static JSONArray services; //a json array of lapps services

	public static void main(String[] args) throws IOException {
		getBrandeisServices();
		InputSelector.initiate();
		//InputSelector.printInputs();
		//for (int i = 0; i < servicesList.size(); i++) {
		for (int i = 1; i < services.size() && i < 2; i++) {
			Service service = new Service((JSONObject) services.get(i));
			System.out.println();
			service.prettyPrint();
			validate(service);
		}
	}

	public static void getBrandeisServices() {
		getServices(LappsAPI.getBrandeisServices()); }

	public static void getVassarServices(){
		getServices(LappsAPI.getVassarServices()); }
	
	private static void getServices(String servicesJSON){
		/*** Makes servicesList an array of JSON objects ***/
		JSONParser parser = new JSONParser();
		try {
			Object obj1 = parser.parse(servicesJSON);
			JSONObject obj = (JSONObject)obj1;
			JSONArray serviceArray = (JSONArray) obj.get("elements");
			services = serviceArray;
		} catch(ParseException pe) {
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}
	}

	public static void validate(Service service) {
		Report report = new Report(service);
		ArrayList<String[]> inputs = InputSelector.select(service);
		for(String[] input : inputs) {
			String filename = input[0];
			String content = input[1];
			System.out.println(filename);
			try {
				String lif = service.execute(content);
				runTests(service, filename, report);
			} catch (IOException ex) {
				Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InstantiationException ex) {
				Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		report.prettyPrint();
	}
	
	public static void runTests(Service service, String lif, Report report) 
			throws InstantiationException, IllegalAccessException {
		
		for (Class theClass : ServiceTest.testclasses()) {
			ServiceTest test;
			try {
				test = (ServiceTest) theClass.newInstance();
				test.run(service, lif, report);
			} catch (InstantiationException ex) {
				Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
}
