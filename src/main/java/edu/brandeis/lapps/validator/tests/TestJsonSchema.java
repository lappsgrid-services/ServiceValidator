package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.ServiceReport;
import edu.brandeis.lapps.validator.Service;

public class TestJsonSchema extends ServiceTest {

	@Override
	public void run(Service service, String filename, ServiceReport report) {
		announce(TestJsonSchema.class.getName());
	}

}
