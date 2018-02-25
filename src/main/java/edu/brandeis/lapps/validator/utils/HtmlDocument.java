package edu.brandeis.lapps.validator.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.json.simple.JSONArray;


public class HtmlDocument {

	public HtmlElement html;
	public HtmlElement head;
	public HtmlElement body;

	public HtmlDocument() {
		this.html = new HtmlElement(null, "html", null);
		this.head = new HtmlElement(this.html, "head", null);
		this.body = new HtmlElement(this.html, "body", null);
	}

	public final void addStyleSheet(String stylesheet) {
		HtmlElement link = new HtmlElement(this.head, "link", null);
		link.addAttribute("rel", "stylesheet");
		link.addAttribute("href", stylesheet);
	}

	/**
	 * Add a simple HTML tag to the end of the parent's list of children
	 *
	 * @param parent parent of the new HtmlElement
	 * @param tagname tag name  of the new HtmlElement
	 * @param content text content of the new HtmlElement (can be null)
	 */
	public final void addSimpleElement(HtmlElement parent, String tagname, String content) {
		HtmlElement e = new HtmlElement(parent, tagname, content);
	}

	/**
	 * Add a table row with two columns to a table
	 *
	 * @param table The table to which the row is added.
	 * @param leftColumn The text content of the left column
	 * @param rightColumn The text content of the right column
	 */
	public final void addRow(HtmlElement table, String leftColumn, String rightColumn) {
		HtmlElement tr = new HtmlElement(table, "tr", null);
		HtmlElement td1 = new HtmlElement(tr, "td", leftColumn);
		td1.addAttribute("class", "first-column");
		HtmlElement td2 = new HtmlElement(tr, "td", rightColumn);
	}

	/**
	 * Utility method to join elements of a JSONArray with line breaks
	 *
	 * @param jArray The JSONArray that will be split
	 * @return  The elements of the array joined as a String
	 */
	public String join(JSONArray jArray) {
		return String.join("<br/>", jArray);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.html.write(sb, 0);
		return sb.toString();
	}

	public void write() {
		System.out.println(this);
	}

	public void write(String filename) {
		try {
			Files.write(Paths.get(filename), this.toString().getBytes());
		} catch (IOException ex) {
			Logger.getLogger(HtmlDocument.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public final String jsonPrettyPrint(String input) {
		// Using the JavaScript engine, as suggested in
		// https://stackoverflow.com/questions/4105795/pretty-print-json-in-java
		// But we will probably want to use Jackson for this.
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine scriptEngine = manager.getEngineByName("JavaScript");
			scriptEngine.put("jsonString", input);
			scriptEngine.eval("result = JSON.stringify(JSON.parse(jsonString), null, 4)");
			String prettyPrintedJson = (String) scriptEngine.get("result");
			return prettyPrintedJson;
		} catch (ScriptException ex) {
			Logger.getLogger(HtmlDocument.class.getName()).log(Level.SEVERE, null, ex);
		}
		return input;
	}
}
