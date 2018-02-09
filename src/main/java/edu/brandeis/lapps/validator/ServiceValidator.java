package edu.brandeis.lapps.validator;

import edu.brandeis.lapps.validator.tests.ServiceTest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
		parseServices(LappsAPI.getBrandeisServices());
		//for (int i = 0; i < servicesList.size(); i++) {
		for (int i = 1; i < services.size() && i < 3; i++) {
			Service service = new Service((JSONObject) services.get(i));
			System.out.println("\n" + service);
			//service.printMetadata();
			validate(service);
		}
	}

	private static void parseServices(String servicesJSON){
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
		try {
			ArrayList<String> inputs = InputSelector.select(service);
			for(String input : inputs) {
				String lif = service.execute(InputSelector.getTestCase1());
				runTests(service, lif, report);
				report.prettyPrint();
			}
		} catch (IOException ex) {
			Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void runTests(Service service, String lif, Report report) 
			throws InstantiationException, IllegalAccessException {
		
		for (Class theClass : ServiceTest.subclasses()) {
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
