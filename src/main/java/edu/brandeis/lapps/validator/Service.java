package edu.brandeis.lapps.validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Service {
	
	public String id, name, metadata;
	public JSONObject serviceSpec, metadataObj, response;
	public JSONArray requires, produces;

	/**
	 * Create a Service object that contains the service metadata and that allows
	 * you to run the service on a string
	 * 
	 * @param serviceSpecification 
	 */
	protected Service(JSONObject serviceSpecification)
	{
		this.serviceSpec = serviceSpecification;
		this.id = (String) serviceSpec.get("serviceId");
		this.name = (String) serviceSpec.get("serviceName");
		this.metadata = LappsAPI.getMetadata(this.id);
		this.parseMetadata();
	}
	
	@Override
	public String toString() {
		return String.format("<Service id=%s>", this.id);
	}

	public void prettyPrint() {
		String main = String.format(
				"\n%s\n   %s\n   spec=%s", 
				this, this.name, this.serviceSpec.toJSONString());
		String meta = String.format(
				"\n   requires=%s\n   produces=%s  ", 
				this.requires.toString(), this.produces.toString());
		System.out.println(main + meta);
	}

	public void printMetadata() {
		System.out.println(this.metadata);
	}
	
	private void parseMetadata() {
		JSONParser parser = new JSONParser();
		try {
			this.metadataObj = (JSONObject) parser.parse(this.metadata);
			JSONObject payloadObj = (JSONObject) this.metadataObj.get("payload");
			JSONObject requiresObj = (JSONObject) payloadObj.get("requires");
			JSONObject producesObj = (JSONObject) payloadObj.get("produces");
			this.requires = new JSONArray();
			this.produces = new JSONArray();
			// TODO: should also get the format
			if (requiresObj.containsKey("annotations"))
				this.requires = (JSONArray) requiresObj.get("annotations");
			if (producesObj.containsKey("annotations"))
				this.produces = (JSONArray) producesObj.get("annotations");
		} catch (ParseException ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Execute the service on a JSON string with discriminator and payload.
	 * 
	 * @param input
	 * 
	 * @return
	 * Returns a String but we really run this for the side effect of putting
	 * a JSONObject instance in the Service's response instance variable.
	 * 
	 * @throws IOException 
	 */
	public String execute(String input) throws IOException {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(LappsAPI.SOAP_PROXY_URL + "?id=" + this.id);
		httppost.setHeader("Content-Type","text/plain");

		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("", input));
		//params.add(new BasicNameValuePair("id",serviceid));
		httppost.setEntity(new StringEntity(input));

		// Execute and get the response.
		HttpResponse httpResponse = httpclient.execute(httppost);
		HttpEntity httpEntity = httpResponse.getEntity();

		if (httpEntity != null) {
            InputStream instream = httpEntity.getContent();
            try {
                StringWriter writer = new StringWriter();
                IOUtils.copy(instream, writer, "utf-8");
				JSONParser parser = new JSONParser();
				this.response = (JSONObject) parser.parse(writer.toString());
			} catch (ParseException ex) {
				Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                instream.close();
            }
        }
		return this.response.toString();
	}

}
