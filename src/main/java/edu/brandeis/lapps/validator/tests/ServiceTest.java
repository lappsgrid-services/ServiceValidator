package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.ServiceReport;
import edu.brandeis.lapps.validator.Service;


public class ServiceTest {

	public void run(Service service, String filename, ServiceReport report) {
		throw new UnsupportedOperationException("The run method has not been implemented.");
	}

	/**
	 * Printing message that test is running.
	 * 
	 * @param className
	 * The name of the test that is running.
	 */
	public static void announce(String className) {
		//System.out.println("   " + className);
	}

	/**
	 * Collecting subclasses of TestService that are used for testing .
	 * 
	 * Manually maintains a list of subclasses needed for the validator tests. 
	 * 
	 * @return 
	 * An array of classes. 
	 */
	public static Class[] testclasses() {
		return new Class[] {
			TestFormat.class,
			TestThatAlwaysFails.class,
			TestJsonSchema.class,
			TestServiceRequirements.class,
			TestLanguage.class
		};
	}
	
	protected void dribble(Object message) {
		System.out.print(">>> ");
		System.out.println(message);
	}
}
