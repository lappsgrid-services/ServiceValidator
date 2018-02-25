package edu.brandeis.lapps.validator;

import edu.brandeis.lapps.validator.utils.HtmlDocument;
import edu.brandeis.lapps.validator.utils.HtmlElement;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ServiceReport {

	final Service service;
	final ArrayList<String[]> messages;
	final Map<String, String> responses;
	final String reportDir;
	
	protected ServiceReport(Service service, String reportDir) {
		this.service = service;
		this.messages = new ArrayList<>();
		this.responses = new HashMap<>();
		this.reportDir = reportDir;
	}
	
	@Override
	public String toString() {
		return String.format("<Report service=%s>", this.service.id);
	}

	public void prettyPrint() {
		System.out.println(this);
		if (this.messages.size() > 0) {
			for (String[] error : this.messages)
				System.out.println("   " + error[1] + " (" + error[0] + ")"); }
	}
	
	public void add(String inputFile, String message) {
		this.messages.add(new String[]{inputFile, message});
	}
	
	public void writeHTML() {
		// create the directory structure
		String serviceReportDir = this.reportDir + this.service.id + "/";
		String outputDir = serviceReportDir + "output/";
		new File(serviceReportDir).mkdirs();
		new File(outputDir).mkdirs();
		// write the report as well as the service output for all inputs
		ReportWriter writer = new ReportWriter(this);
		writer.write(serviceReportDir + "index.html");
		for (String filename : responses.keySet()) {
			String response = responses.get(filename);
			ResponseWriter rw = new ResponseWriter(service, filename, response);
			rw.write(outputDir + filename + ".html"); }
	}

	void addResponse(String filename, String response) {
		this.responses.put(filename, response);
	}

}


class ReportWriter extends HtmlDocument {
	
	private final Service service;
	private final ArrayList<String[]> messages;
	private final String reportDir;

	HtmlElement header;		// the header div in the body, needed for the css
	HtmlElement content;	// the content div in the body, needed for the css

	ReportWriter(ServiceReport report) {
		super();
		this.service = report.service;
		this.messages = report.messages;
		this.reportDir = report.reportDir;
		addStyleSheet("../main.css");
		this.header = new HtmlElement(this.body, "div", null);
		this.header.addAttribute("class", "header");
		addSimpleElement(this.header, "h2", "<br/>LAPPS Service Validator Result");
		this.content = new HtmlElement(this.body, "div", null);
		this.content.addAttribute("class", "content");
		addServiceInfo();
		addMessages();
	}
	
	/** Add the service information as a table */
	private void addServiceInfo() {
		addSimpleElement(this.content, "h2", "Service Information");
		HtmlElement table = new HtmlElement(this.content, "table", null);
		addRow(table, "Service ID", "<b>" + this.service.id + "</b>");
		addRow(table, "Service name", this.service.name);
		addRow(table, "Required&nbsp;format", join(this.service.requiredFormat));
		addRow(table, "Required&nbsp;annotations", join(this.service.requiredAnnotations));
		addRow(table, "Produced&nbsp;format", join(this.service.producedFormat));
		addRow(table, "Produced&nbsp;annotations", join(this.service.producedAnnotations));
	}

	/** Add the list of warning messages as a listing */
	private void addMessages() {
		HashMap<String, ArrayList<String>> map = reorganizeMessages();
		HtmlElement ul, li;
		addSimpleElement(this.content, "h2", "Warnings");
		addSimpleElement(this.content, "p", 
				"Warnings are printed with the output that failed the test");
		
		ul = new HtmlElement(this.content, "ul", null);
		for (String message : map.keySet()) {
			String hrefs = joinFilenames(map.get(message));
			li = new HtmlElement(ul, "li", null);
			addSimpleElement(li, "span", message);
			addSimpleElement(li, "blockquote", hrefs); }
	}

	/**
	 * Take the flat list of messages, which are <filename, message> pairs and return
	 * it as a dictionary of messages.
	 * 
	 * @return the messages indexed on the message string with a list of file names
	 * as the value
	 */
	private HashMap<String, ArrayList<String>> reorganizeMessages() {
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		for (String[] message : this.messages) {
			String fname = message[0];
			String text = message[1];
			if (! map.containsKey(text))
				map.put(text, new ArrayList());
			map.get(text).add(fname); }
		return map;
	}
	
	private String joinFilenames(ArrayList<String> filenames) {
		ArrayList<String> tmp = new ArrayList<>();
		for (String filename : filenames)
			tmp.add(href(filename));
		return "<blockquote>" + String.join(" | ", tmp) + "</blockquote>";
	}

	/** 
	 * Helper function to take a filename and create a link to it.
	 * 
	 * @param filename The file we want to link to.
	 * @return A string like "<a href='output/filename.html'>filename</a>"
	 */
	private String href(String filename) {
		return String.format("<a href='output/%s.html'>%s</a>", filename, filename);
	}
	
}


class ResponseWriter extends HtmlDocument {

	Service service;
	String filename;	// the input that gave rise to the response
	String response;

	HtmlElement header;
	HtmlElement content;
	
	ResponseWriter(Service service, String filename, String response) {
		
		this.service = service;
		this.filename = filename;
		this.response = response;
		
		addStyleSheet("../../main.css");
		this.header = new HtmlElement(this.body, "div", null);
		this.header.addAttribute("class", "header");
		addSimpleElement(this.header, "h2", "<br/>LAPPS Service Validator Result");
		this.content = new HtmlElement(this.body, "div", null);
		this.content.addAttribute("class", "content");
		addSimpleElement(this.content, "h2", "Service: " + this.service.id);
		addSimpleElement(this.content, "h3", "Response generated on input " + href(filename));
		addSimpleElement(this.content, "pre", "\n" + jsonPrettyPrint(this.response));
		addSimpleElement(this.content, "p", null);
	}
	
	private String href(String filename) {
		return String.format("<a href='../../input/%s.html'>%s</a>", filename, filename);
	}
	
}