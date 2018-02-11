package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.Report;
import edu.brandeis.lapps.validator.Service;


public class TestServiceRequirements extends ServiceTest {

	@Override
	public void run(Service service, String filename, Report report) {
		announce(TestServiceRequirements.class.getName());
	}

}
