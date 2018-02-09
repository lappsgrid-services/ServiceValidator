package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.Report;
import edu.brandeis.lapps.validator.Service;
import edu.brandeis.lapps.validator.ServiceTest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DummyTest extends ServiceTest {

	public static void run(Service service, String lif, Report report) {
    
		announce(DummyTest.class.getName());

		// TODO: probably do this outside of the tests to avoid repeating this
		JSONParser parser = new JSONParser();
        JSONObject md;

		try {
            md = (JSONObject) parser.parse(lif);	// do this outside as well
            //System.out.println(response);
            String discriminator = md.get("discriminator").toString();
            Boolean err = (discriminator.equals("http://vocab.lappsgrid.org/ns/error"));
            if (err){
                report.add("failure");
                report.add("and we do not why this is");
            } else {
                report.add("success");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
}
