package org.example.functions.dto.extracting;

import java.net.URL;
import java.util.List;

import org.example.functions.dto.HttpRequestDto;
import org.json.JSONObject;

public interface DataSourceInterfaceConfigDtoInterface {

	String getBody();
	JSONObject getHeader();
	URL getUrl();

	String getMethod();

	HttpRequestDto getHttpRequestDto();

	List<HttpRequestDto> getAllHttpRequestDto();
}
