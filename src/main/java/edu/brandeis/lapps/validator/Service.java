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

/**
 * Class to maintain the state of a LAPPS Service.
 */
public class Service {
	
	public String id, name, metadata;
	public JSONObject serviceSpec, metadataObj;
	/** the input string that the service last executed, stored as a JSONObject */
	public JSONObject input;
	/** the output string generated by the service, stored as a JSONObject */
	public JSONObject response;
	public JSONObject requires, produces;
	public JSONArray requiredFormat, requiredAnnotations;
	public JSONArray producedFormat, producedAnnotations;
	
	/**
	 * Create a Service object that contains the service metadata and that allows
	 * you to run the service on a string
	 * 
	 * @param serviceSpecification
	 * taken from the service manager
	 */
	protected Service(JSONObject serviceSpecification)
	{
		this.serviceSpec = serviceSpecification;
		this.id = (String) serviceSpec.get("serviceId");
		this.name = (String) serviceSpec.get("serviceName");
		this.metadata = LappsAPI.getMetadata(this.id);
		this.parseMetadata();
	}
	
	private JSONArray getRequiredFormat() {
		return this.normalize((JSONArray)this.requires.get("format")); }

	private JSONArray getRequiredAnnotations() {
		return this.normalize((JSONArray)this.requires.get("annotations")); }

	private JSONArray getProducedFormat() {
		return this.normalize((JSONArray)this.produces.get("format")); }

	private JSONArray getProducedAnnotations() {
		return this.normalize((JSONArray)this.produces.get("annotations")); }

	/**
	 * Normalize to arrays by making sure null is mapped to an empty array.
	 * 
	 * Using get() on a JSONObject can give you null and then even after you
	 * cast it to JSONObject it is still null, so use this to makes sure you
	 * get an empty array instead.
	 * 
	 * @param val
	 * @return 
	 */
	private JSONArray normalize(JSONArray val) {
		if (val == null)
			return new JSONArray();
		else
			return val;
	}

	@Override
	public String toString() {
		return String.format("<Service id=%s>", this.id); }

	public void prettyPrint() {
		System.out.println(this);
		for (Object f : this.requiredFormat)
			System.out.println("   required-format = " + f);
		for (Object a : this.requiredAnnotations)
			System.out.println("   required-annots = " + a);
		for (Object f : this.producedFormat)
			System.out.println("   produced-format = " + f);
		for (Object a : this.producedAnnotations)
			System.out.println("   produced-annots = " + a);

	}

	public void printMetadata() {
		System.out.println(this.metadata); }
	
	private void parseMetadata() {
		JSONParser parser = new JSONParser();
		try {
			this.metadataObj = (JSONObject) parser.parse(this.metadata);
			JSONObject payloadObj = (JSONObject) this.metadataObj.get("payload");
			this.requires = (JSONObject) payloadObj.get("requires");
			this.produces = (JSONObject) payloadObj.get("produces");
			this.requiredFormat = this.getRequiredFormat();
			this.producedFormat = this.getProducedFormat();
			this.requiredAnnotations = this.getRequiredAnnotations();
			this.producedAnnotations = this.getProducedAnnotations();
		} catch (ParseException ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Execute the service on a JSON string with discriminator and payload.
	 * 
	 * @param inputString
	 * 
	 * @return
	 * Returns a String but we really run this for the side effect of putting
	 * a JSONObject instance in the Service's response instance variable.
	 * 
	 * @throws IOException 
	 */
	public String execute(String inputString) throws IOException {

		HttpClient httpclient;
		HttpPost httppost;
		HttpResponse httpResponse;
		HttpEntity httpEntity;
		List<NameValuePair> params;
		InputStream instream;

		httpclient = HttpClients.createDefault();
		httppost = new HttpPost(LappsAPI.SOAP_PROXY_URL + "?id=" + this.id);
		httppost.setHeader("Content-Type","text/plain");

		// Request parameters and other properties.
		// NOTE: params is not used
		params = new ArrayList<>(2);
		params.add(new BasicNameValuePair("", inputString));
		//params.add(new BasicNameValuePair("id",serviceid));
		httppost.setEntity(new StringEntity(inputString));

		// Execute and get the response.
		httpResponse = httpclient.execute(httppost);
		httpEntity = httpResponse.getEntity();

		if (httpEntity != null) {
			instream = httpEntity.getContent();
            try {
                StringWriter writer = new StringWriter();
                IOUtils.copy(instream, writer, "utf-8");
				JSONParser parser = new JSONParser();
				this.input = (JSONObject) parser.parse(inputString);
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
