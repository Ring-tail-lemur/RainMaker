package org.example.functions.util;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.json.JSONObject;

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

	public String bindParameters(String graphqlBody, Map<String, String> requestParameters) {
		StringSubstitutor strSubstitutor = new StringSubstitutor(requestParameters, prefix, suffix);
		return strSubstitutor.replace(graphqlBody);
	}

	public String bindParameters(String rawUrl, JSONObject pathParameters) {
		Map<String, String> pathParameterMap = (Map)pathParameters.toMap();
		StringSubstitutor strSubstitutor = new StringSubstitutor(pathParameterMap, prefix, suffix);
		return strSubstitutor.replace(rawUrl);
	}

	public String removeStartingSubstitutor(String string) {
		return string.substring(string.indexOf(']') + 1);
	}
}
