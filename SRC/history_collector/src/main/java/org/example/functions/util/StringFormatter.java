package org.example.functions.util;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;

import lombok.Setter;

@Setter
public class StringFormatter {

	private String prefix;
	private String suffix;

	public StringFormatter(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public StringFormatter() {
		this.prefix = "{{";
		this.suffix = "}}";
	}

	public String bindParameters(String string, Map<String, String> parameterMap) {
		StringSubstitutor strSubstitutor = new StringSubstitutor(parameterMap, prefix, suffix);
		return strSubstitutor.replace(string);
	}

	public String removeStartingSubstitutor(String string) {
		return string.substring(string.indexOf(']') + 1);
	}
}
