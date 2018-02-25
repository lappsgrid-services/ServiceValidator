package edu.brandeis.lapps.validator;

import edu.brandeis.lapps.validator.utils.HtmlDocument;
import edu.brandeis.lapps.validator.utils.HtmlElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestSuiteReport {

	String reportDir;
	String inputDir;
	ArrayList<String[]> observations;
	
	public TestSuiteReport(String reportDir) {
		this.reportDir = reportDir;
		this.inputDir = this.reportDir + "input/";
		this.observations = new ArrayList();
		new File(reportDir).mkdirs();
		new File(inputDir).mkdirs();
		try {
			File source = new File("src/main/resources/main.css");
			File target = new File(this.reportDir + "/main.css");
			Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			Logger.getLogger(TestSuiteReport.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	void update(Service service, ServiceReport report) {
		String size = String.format("%d", report.messages.size());
		observations.add(new String[]{service.id, size});
	}

	void finish() {
		IndexWriter writer = new IndexWriter(this.observations);
		writer.write(this.reportDir + "index.html");
	}

	void writeInputFiles(ArrayList<String[]> files) {
		for (String[] file : files) {
			String filename = file[0];
			String content = file[1];
			InputWriter writer = new InputWriter(filename, content);
			writer.write(this.inputDir + filename + ".html"); }
	}

	void printObservations() {
		for (String[] observation : this.observations) {
			System.out.print(observation[1] + " ");
			System.out.print(observation[0]);
			System.out.println(); }
	}
	
}


class IndexWriter extends HtmlDocument {
	
	HtmlElement header;
	HtmlElement content;
	
	IndexWriter(ArrayList<String[]> observations) {
		
		super();
		addStyleSheet("main.css");
		addSimpleElement(this.head, "style", 
				"pre { padding: 5px; border: thin dotted grey; background-color: #eee; }" );
		this.header = new HtmlElement(this.body, "div", null);
		this.header.addAttribute("class", "header");
		addSimpleElement(this.header, "h2", "<br/>LAPPS Service Validator Report");
		this.content = new HtmlElement(this.body, "div", null);
		this.content.addAttribute("class", "content");
		addSimpleElement(this.content, "h3", "Services validated with for each the number of warnings");
		HtmlElement table = new HtmlElement(this.content, "table", null);
		addRow(table, "Service", "Warnings");
		for (String[] observation : observations)
			addRow(table, href(observation[0]), observation[1]);
		addSimpleElement(this.content, "p", null);
	}
	
	private String href(String filename) {
		return String.format("<a href='%s/index.html'>%s</a>", filename, filename);
	}
	
}


class InputWriter extends HtmlDocument {
	
	HtmlElement header;
	HtmlElement content;
	
	InputWriter(String filename, String content) {
		
		super();
		addStyleSheet("../main.css");
		this.header = new HtmlElement(this.body, "div", null);
		this.header.addAttribute("class", "header");
		addSimpleElement(this.header, "h2", "<br/>LAPPS Service Validator Input");
		this.content = new HtmlElement(this.body, "div", null);
		this.content.addAttribute("class", "content");
		addSimpleElement(this.content, "h2", "Service input: " + filename);
		addSimpleElement(this.content, "pre", "\n" + jsonPrettyPrint(content));
		addSimpleElement(this.content, "p", null);
	}
}
