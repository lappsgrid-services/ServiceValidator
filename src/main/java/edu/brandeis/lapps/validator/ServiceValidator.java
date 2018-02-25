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


/**
 * Main class of the LAPPS Service Validator.
 * 
 * Running this class now does the following:
 * 
 * (1) it pings the Brandeis service manager and gets its list of services, and
 * (2) it runs the available tests on just the first service found.
 * 
 * Output is written as a set of HTML files in the build/report directory, when
 * you run this multiple times prior results will be overwritten.
 */
public class ServiceValidator {

	private static JSONArray services; //a json array of lapps services
	private static String reportDir;
	
	public static void main(String[] args) throws IOException {
		reportDir = "build/report/";
		getBrandeisServices();
		InputSelector.initiate();
		TestSuiteReport tsReport = new TestSuiteReport(reportDir);
		ArrayList<String[]> inputFiles = InputSelector.selectAll();
		tsReport.writeInputFiles(inputFiles);
		//for (int i = 0; i < servicesList.size(); i++) {
		for (int i = 1; i < services.size() && i < 2; i++) {
			Service service = new Service((JSONObject) services.get(i));
			ServiceReport report = new ServiceReport(service, reportDir);
			System.out.println(service.id);
			validate(service, report);
			tsReport.update(service, report);
			System.out.println();
			report.writeHTML();
		}
		tsReport.printObservations();
		tsReport.finish();
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

	public static void validate(Service service, ServiceReport report) {
		ArrayList<String[]> inputs = InputSelector.select(service);
		for(String[] input : inputs) {
			String filename = input[0];
			String content = input[1];
			System.out.println("   " + filename);
			try {
				//
				String response = service.execute(content);
				report.addResponse(filename, response);
				runTests(service, filename, report);
			} catch (IOException ex) {
				Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InstantiationException ex) {
				Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public static void runTests(Service service, String lif, ServiceReport report) 
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
