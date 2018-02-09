package edu.brandeis.lapps.validator;

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
		} catch (NoSuchMethodException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException ex) {
			Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void runTests(Service service, String lif, Report report)
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class[] args = new Class[3];
		args[0] = Service.class;
		args[1] = String.class;
		args[2] = Report.class;
		for (Class c : ServiceTest.subclasses()) {
			Method m = c.getMethod("run", args);
			m.invoke(null, service, lif, report);
		}
	}
	
}
