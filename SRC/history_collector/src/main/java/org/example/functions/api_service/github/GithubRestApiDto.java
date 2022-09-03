package org.example.functions.api_service.github;

import java.net.URL;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GithubRestApiDto {
	private String requestType;
	private URL url;
	private JSONObject header;
	private String method;
}
