package edu.brandeis.lapps.validator.tests;

import edu.brandeis.lapps.validator.ServiceReport;
import edu.brandeis.lapps.validator.Service;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestFormat extends ServiceTest {

	private static final String FORMAT_ERROR_STRING =
		"Service accepts %s which is disallowed by the service input requirements";

	@Override
	public void run(Service service, String filename, ServiceReport report) {

		String inputDiscriminator, outputDiscriminator, outputPayload;
		String error = null;
		JSONObject lif = null;
		
		announce(TestFormat.class.getName());
		inputDiscriminator = service.input.get("discriminator").toString();
		outputDiscriminator = service.response.get("discriminator").toString();
		outputPayload = service.response.get("payload").toString();

		if (outputDiscriminator.endsWith("error")) {
			error = outputPayload;
		} else {
			try {
				lif = (JSONObject) new JSONParser().parse(outputPayload);
			} catch (ParseException ex) {
				Logger.getLogger(TestFormat.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		//dribble("Input discriminator = " + inputDiscriminator);
		//dribble("Output discriminator = " + outputDiscriminator);
		
		if (service.requiredFormat.contains(inputDiscriminator)) {
			//dribble("Required format contains input discriminator");
			// If format includes the input discriminator, then we should either
			// not get an error or an error that is not related to the input format
			if (error != null && this.errorIsNotFormatError(inputDiscriminator, error))
				report.add(filename, String.format(FORMAT_ERROR_STRING, inputDiscriminator));
		} else {
			//dribble("Required format does not contain input discriminator");
			// We should get an error, but this error has to be of the right kind
			if (error == null) {
				//dribble("No error found");
				report.add(filename, String.format(FORMAT_ERROR_STRING, inputDiscriminator)); }
			else if (this.errorIsNotFormatError(inputDiscriminator, error)) {
				//dribble("Error is not format error");
				report.add(filename, String.format(FORMAT_ERROR_STRING, inputDiscriminator)); }
		}
	}

	private boolean errorIsFormatError(String inputDiscriminator, String error) {
		// NOTE: this requires we have an idea of what the error would be
		// TODO: maybe add to this that error contains the input discriminator
		return error.startsWith("Unsupported discriminator type")
				|| error.startsWith("Invalid discriminator type");
	}

	private boolean errorIsNotFormatError(String inputDiscriminator, String error) {
		return ! errorIsFormatError(inputDiscriminator, error);
	}
}
