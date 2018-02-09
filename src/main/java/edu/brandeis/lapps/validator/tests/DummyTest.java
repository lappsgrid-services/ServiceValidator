package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.Report;
import edu.brandeis.lapps.validator.Service;

public class DummyTest extends ServiceTest {

	public void run(Service service, String lif, Report report) {

		String discriminator;
		Boolean err;
		
		announce(DummyTest.class.getName());
		discriminator = service.response.get("discriminator").toString();
		err = (discriminator.equals("http://vocab.lappsgrid.org/ns/error"));
		if (err){
			report.add("failure");
			report.add("and we do not know why this is");
		} else {
			report.add("success");
		}
    }
	
	public void test(Service service, String lif, Report report) {

		String discriminator;
		Boolean err;
		
		announce(DummyTest.class.getName());
		discriminator = service.response.get("discriminator").toString();
		err = (discriminator.equals("http://vocab.lappsgrid.org/ns/error"));
		if (err){
			report.add("failure");
			report.add("and we do not know why this is");
		} else {
			report.add("success");
		}
	}
	
}
