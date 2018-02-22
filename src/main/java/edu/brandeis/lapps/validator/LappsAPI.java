/**
 * Interface to the LAPPS API at http://api.lappsgrid.org.
 */

package edu.brandeis.lapps.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LappsAPI {

	public static final String API_URL = "http://api.lappsgrid.org";
	public static final String SERVICES_URL = API_URL + "/services";
	public static final String SOAP_PROXY_URL = API_URL + "/soap-proxy";
	
	public static String getBrandeisServices() {
		return getServices("brandeis");
	}

	public static String getVassarServices() {
		return getServices("vassar");
	}

	/**
	 * Get list of available services from the Brandeis or Vassar Service Manager
	 */
	private static String getServices(String server){
		String url = String.format("%s/%s", SERVICES_URL, server);
		ProcessBuilder pb = new ProcessBuilder("curl", url);
		return apiCall(pb);
	}
	
	public static String getMetadata(String serviceID) {
		String url = "http://api.lappsgrid.org/metadata?id=" + serviceID;
		ProcessBuilder pb = new ProcessBuilder("curl", url);
        return apiCall(pb);
	}

	static String apiCall(ProcessBuilder pb){
	    String result = "";
        Process p;
        try {
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            result = builder.toString();
        }
        catch (IOException e) {
            System.out.print("ERROR");
            e.printStackTrace();
        }
        return result;
	}
}
