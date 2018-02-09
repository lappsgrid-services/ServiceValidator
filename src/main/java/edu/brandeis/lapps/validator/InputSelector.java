package edu.brandeis.lapps.validator;

import java.util.ArrayList;


public class InputSelector {

	public static String getTestCase1() {
		return "{\"discriminator\":\"http://vocab.lappsgrid.org/ns/media/text\",\"payload\":\"Lif test for metadata validator.\"}";
	}
	
	public static String getTestCase2() {
		return "{\"discriminator\": \"http://vocab.lappsgrid.org/ns/media/jsonld\",\"payload\": {\"@context\": \"http://vocab.lappsgrid.org/context-1.0.0.jsonld\",\"metadata\": {},\"text\": {\"@value\": \"Lif test for metadata validator.\"},\"views\": [{\"metadata\": {\"contains\": {\"http://vocab.lappsgrid.org/Token\": {\"producer\": \"edu.brandeis.cs.uima.dkpro.OpenNlpTokenizer:0.0.1-SNAPSHOT\",\"type\": \"tokenizer:dkpro_opennlp\"}}},\"annotations\": [{\"id\": \"13\",\"start\": 0,\"end\": 32,\"@type\": \"http://vocab.lappsgrid.org/Sentence\",\"features\": {\"sentence\": \"Lif test for metadata validator.\"}},{\"id\": \"17\",\"start\": 0,\"end\": 3,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \"Lif\"}},{\"id\": \"26\",\"start\": 4,\"end\": 8,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \"test\"}},{\"id\": \"35\",\"start\": 9,\"end\": 12,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \"for\"}},{\"id\": \"44\", \"start\": 13,\"end\": 21,\"@type\": \"http://vocab.lappsgrid.org/Token\", \"features\": {\"word\": \"metadata\"}}, {\"id\": \"53\",\"start\": 22,\"end\": 31,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \"validator\"}},{\"id\": \"62\",\"start\": 31,\"end\": 32,\"@type\": \"http://vocab.lappsgrid.org/Token\",\"features\": {\"word\": \".\"}}]} ]}}";
	}

	/**
	 * Select the inputs that the service should run on.
	 *
	 * @param service
	 * The Service for which input strings are selected.
	 *
	 * @return an ArrayList with string to be fed to the service
	 */
	public static ArrayList<String> select(Service service) {
		ArrayList<String> inputs = new ArrayList();
		// TODO: just a placeholder for now
		inputs.add(getTestCase2());
		return inputs;
	}
}
