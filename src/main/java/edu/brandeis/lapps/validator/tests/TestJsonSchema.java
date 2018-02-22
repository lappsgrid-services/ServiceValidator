package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.Report;
import edu.brandeis.lapps.validator.Service;

public class TestJsonSchema extends ServiceTest {

	@Override
	public void run(Service service, String filename, Report report) {
		announce(TestJsonSchema.class.getName());
	}

}
