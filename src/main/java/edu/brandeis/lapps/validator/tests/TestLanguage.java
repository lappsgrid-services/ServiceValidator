package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.ServiceReport;
import edu.brandeis.lapps.validator.Service;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLanguage extends ServiceTest {
    private static final String LANGUAGE_ERROR =
            "Service does not preserve Language attribute.";

    @Override
    public void run(Service service, String filename, ServiceReport report) {
        announce(TestLanguage.class.getName());
        try {
            JSONParser parser = new JSONParser();
            JSONObject outputPayload = (JSONObject) parser.parse(service.response.get("payload").toString());
            JSONObject text = (JSONObject) parser.parse(outputPayload.get("text").toString());
            if (!text.containsKey("@language")) {
                report.add(filename, LANGUAGE_ERROR);
            }
        } catch (ParseException e){
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}