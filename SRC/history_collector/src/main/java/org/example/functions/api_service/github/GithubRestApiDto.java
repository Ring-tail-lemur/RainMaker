package org.example.functions.api_service.github;

import java.net.URL;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GithubRestApiDto {
	private URL url;
	private JSONObject header;
	private String method;
}
