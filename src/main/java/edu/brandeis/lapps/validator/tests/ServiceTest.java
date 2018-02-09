package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.Report;
import edu.brandeis.lapps.validator.Service;


public class ServiceTest {

	public void run(Service service, String lif, Report report) {
		throw new UnsupportedOperationException("The run method has not been implemented.");
	}

	public static void announce(String className) {
		System.out.println("   " + className);
	}
	
	public static Class[] subclasses() {
		return new Class[] {
			DummyTest.class,
			//TestFormat.class,
			TestJsonSchema.class,
			TestServiceRequirements.class
		};
	}

	public void test(Service service, String lif, Report report) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
