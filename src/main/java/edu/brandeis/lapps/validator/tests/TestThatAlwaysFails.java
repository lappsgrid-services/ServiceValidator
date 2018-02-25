package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.ServiceReport;
import edu.brandeis.lapps.validator.Service;
import static edu.brandeis.lapps.validator.tests.ServiceTest.announce;

/**
 * Test that no service can pass.
 *
 * This is here for debugging, if it is added to the test classes on ServiceTest
 * then every service will fail this test on any input.
 */
public class TestThatAlwaysFails extends ServiceTest {

	private static final String ERROR = "Service failed test that is impossible to pass";

	@Override
	public void run(Service service, String filename, ServiceReport report) {
		announce(TestThatAlwaysFails.class.getName());
		report.add(filename, ERROR);
	}
}
