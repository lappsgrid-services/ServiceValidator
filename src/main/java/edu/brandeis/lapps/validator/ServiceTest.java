package edu.brandeis.lapps.validator;

import edu.brandeis.lapps.validator.tests.DummyTest;
import edu.brandeis.lapps.validator.tests.TestJsonSchema;
import edu.brandeis.lapps.validator.tests.TestServiceRequirements;


public class ServiceTest {

	public static void run(Service service, String lif, Report report) {
		throw new UnsupportedOperationException("The run method has not been implemented.");
	}

	public static void announce(String className) {
		System.out.println("   " + className);
	}
	
	public static Class[] subclasses() {
		return new Class[] {
			DummyTest.class,
			TestJsonSchema.class,
			TestServiceRequirements.class
		};
	}
}
