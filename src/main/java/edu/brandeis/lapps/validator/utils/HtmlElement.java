package edu.brandeis.lapps.validator.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class HtmlElement {
	
	String name;
	String content;
	Properties attrs;
	HtmlElement parent;
	List<HtmlElement> dtrs;

	public HtmlElement(HtmlElement parent, String name, String content) {
		this.name = name;
		this.content = content;
		this.attrs = new Properties();
		this.parent = parent;
		if (this.parent != null) {
			this.parent.addChild(this);
		}
		this.dtrs = new ArrayList<>();
	}

	public void addChild(HtmlElement dtr) {
		this.dtrs.add(dtr);
	}

	public void addAttribute(String attribute, String value) {
		this.attrs.put(attribute, value);
	}

	public void write(StringBuilder sb, int level) {
		String indent = String.join("", Collections.nCopies(level * 4, " "));
		StringBuilder attrString = new StringBuilder();
		for (Object key : this.attrs.keySet()) {
			attrString.append(String.format(" %s=\"%s\"", key, this.attrs.get(key)));
		}
		sb.append(String.format("%s<%s%s>", indent, this.name, attrString.toString()));
		if (this.content == null) {
			sb.append("\n");
			for (HtmlElement dtr : this.dtrs) {
				dtr.write(sb, level + 1);
			}
			sb.append(String.format("%s</%s>\n", indent, this.name));
		} else {
			sb.append(String.format("%s", this.content));
			sb.append(String.format("</%s>\n", this.name));
		}
	}
	
}
