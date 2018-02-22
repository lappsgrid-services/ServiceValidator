/*
 * Probably all depricated now since all code has been copied to other classes.
 */

package edu.brandeis.lapps.validator;

import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.apache.http.client.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.commons.io.IOUtils;


public class ServiceValidatorKelley {
	private static JSONArray servicesList; //a json array of lapps services
	private static String testLIF =
		//"{\"discriminator\":\"http://vocab.lappsgrid.org/ns/media/text\",\"payload\":\"Lif test for metadata validator.\"}";
	    //"This is a text test.";
		"{\"discriminator\": \"http://vocab.lappsgrid.org/ns/media/jsonld\",\"payload\": {\"@context\": \"http://vocab.lappsgrid.org/context-1.0.0.jsonld\",\"metadata\": {},\"text\": {\"@value\": \"Lif test for metadata validator.\"},\"views\": [{\"metadata\": {\"contains\": {\"http://vocab.lappsgrid.org/Token\": {\"producer\": \"edu.brandeis.cs.uima.dkpro.OpenNlpTokenizer:0.0.1-SNAPSHOT\",\"type\": \"tokenizer:dkpro_opennlp\"}}},\"annotations\": [{\"id\": \"13\",\"start\": 0,\"end\": 32,\"@type\": \"http://vocab.lappsgrid.org/Sentence\",\"features\": {\"sentence\": \"Lif test for metadata validator.\"}},{\"id\": \"17\",\"start\": 0,\"end\": 3,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \"Lif\"}},{\"id\": \"26\",\"start\": 4,\"end\": 8,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \"test\"}},{\"id\": \"35\",\"start\": 9,\"end\": 12,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \"for\"}},{\"id\": \"44\", \"start\": 13,\"end\": 21,\"@type\": \"http://vocab.lappsgrid.org/Token\", \"features\": {\"word\": \"metadata\"}}, {\"id\": \"53\",\"start\": 22,\"end\": 31,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \"validator\"}},{\"id\": \"62\",\"start\": 31,\"end\": 32,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \".\"}}]} ]}}";

	private static void parseServiceList(String servicesJSON){
		/*** Makes servicesList an array of JSON objects ***/
		JSONParser parser = new JSONParser();
		try{
			Object obj1 = parser.parse(servicesJSON);
			JSONObject obj = (JSONObject)obj1;
			JSONArray serviceArray = (JSONArray) obj.get("elements");
			servicesList = serviceArray;
		}catch(ParseException pe){
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}
	}
	
	private static String getServices(){
	/** Gets list of available services from Service Manager **/
		String result;
		ProcessBuilder pb = new ProcessBuilder(
				"curl",
				"http://api.lappsgrid.org/services/brandeis"
				);
        result = apiCall(pb);
		return result;
	}
	
	private static String getMetadata(String serviceId){
		String result;
		System.out.println("serviceid = " + serviceId);
		ProcessBuilder pb = new ProcessBuilder(
				"curl",
				"http://api.lappsgrid.org/metadata?id=" + serviceId
				);
        result = apiCall(pb);
		return result;
	}
	
	private static String getRequires(String metadata){
		String result = "";
		JSONParser parser = new JSONParser();
		JSONObject md;
		try {
			md = (JSONObject) parser.parse(metadata);
			JSONObject payload = (JSONObject) md.get("payload");
			JSONObject requires = (JSONObject) payload.get("requires");
			if (requires.containsKey("annotations")){
				result =  requires.get("annotations").toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static String getProduces(String metadata){
		String result = "";
		JSONParser parser = new JSONParser();
		JSONObject md;
		try {
			md = (JSONObject) parser.parse(metadata);
			JSONObject payload = (JSONObject) md.get("payload");
			JSONObject produces = (JSONObject) payload.get("produces");
			result = produces.get("annotations").toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	private static String checkService(String serviceid, String lif) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://api.lappsgrid.org/soap-proxy?id=" + serviceid);
        httppost.setHeader("Content-Type","text/plain");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("", lif));
        //params.add(new BasicNameValuePair("id",serviceid));
        httppost.setEntity(new StringEntity(lif));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                StringWriter writer = new StringWriter();
                IOUtils.copy(instream, writer);
                String theString = writer.toString();
                return theString;
            } finally {
                instream.close();
            }
        }
        return "";
	}

	private static String checkResult(String response) {
        JSONParser parser = new JSONParser();
        JSONObject md;
        try {
            md = (JSONObject) parser.parse(response);
            System.out.println(response);
            String discriminator = md.get("discriminator").toString();
            Boolean err = (discriminator.equals("http://vocab.lappsgrid.org/ns/error"));
            if (err){
                return "failure";
            } else {
                return "success";
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "ERROR";
    }

	private static String apiCall(ProcessBuilder pb){
	    String result = "";
        Process p;
        try {
            p= pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while (( line = reader.readLine()) != null){
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            result = builder.toString();
        }
        catch (IOException e){
            System.out.print("ERROR");
            e.printStackTrace();
        }
        return result;
	}
	
	public static void main(String[] args) throws IOException {
		String serviceManJSON = getServices();
		parseServiceList(serviceManJSON);
		//for (int i = 0; i<servicesList.size()-1;i++){
		for (int i = 0; i<3; i++){ //change to i<servicesList.size()-1 to test all
			JSONObject service = (JSONObject) servicesList.get(i);
			System.out.println("PROCESSING... " + service.get("serviceDescription"));
			String serviceMetadata = getMetadata((String)service.get("serviceId"));
			System.out.println(checkResult(checkService((String)service.get("serviceId"), testLIF)));

			System.out.println("Service requires:");
			System.out.println(getRequires(serviceMetadata));
			System.out.println("Service produces:");
			System.out.println(getProduces(serviceMetadata));
			System.out.println("\n\n");
		}
	}
}
