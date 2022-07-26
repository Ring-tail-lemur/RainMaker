package org.example.functions.dto.extracting;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.example.functions.dto.HttpRequestDto;
import org.json.JSONObject;

public interface DataSourceInterfaceConfigDto {

	String getBody();
	JSONObject getHeader();
	URL getUrl();
	String getMethod();
	HttpRequestDto getHttpRequestDto();
	List<HttpRequestDto> getAllHttpRequestDto();
	Map<String, String> getPaginationInfo();
}
