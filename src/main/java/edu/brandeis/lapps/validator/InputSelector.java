package edu.brandeis.lapps.validator;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InputSelector {

	// Keep all inputs in memory so we won't need to read them from disk for
	// every service.
	private static Map<String, String> INPUTS;

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
	 * The Service for which input strings are selected. The current version 
	 * ignores the service and returns all tests.
	 *
	 * @return an ArrayList with strings to be fed to the service as input.
	 */
	public static ArrayList<String[]> select(Service service) {
		ArrayList<String[]> inputs = new ArrayList<>();
		for (String filename : INPUTS.keySet())
			inputs.add(new String[]{filename, INPUTS.get(filename)});
		return inputs;
	}

	public static void initiate() {
		File[] files = getInputFiles();
		INPUTS = new HashMap<>();
		for (File file : files) {
			try {
				INPUTS.put(file.getName(), readFile(file.toString()));
			} catch (IOException ex) {
				Logger.getLogger(InputSelector.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static void printInputs() {
		for (String filename : INPUTS.keySet())
			System.out.println(INPUTS.get(filename).length() + "\t" + filename);
	}

	private static File[] getInputFiles() {
		File folder = new File("src/main/resources/input");
		File[] files = folder.listFiles(new Filter());
		return files;
	}

    private static String readFile(String filename) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filename));
	        return new String(bytes, Charset.forName("UTF-8"));
    }
 
}


class Filter implements FileFilter {

	@Override
    public boolean accept(File path) {
        String filename = path.getName();
		char[] chars = filename.toCharArray();
		char lastChar = chars[chars.length - 1];
		return filename.startsWith("karen-flies") && lastChar != '~';
    }
}