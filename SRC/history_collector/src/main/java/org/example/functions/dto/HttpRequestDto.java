package org.example.functions.dto;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.example.functions.dto.extracting.DataSourceInterfaceConfigDto;
import org.example.functions.util.StringFormatter;
import org.json.JSONObject;

import lombok.Data;

@Data
public class HttpRequestDto {
	private URL url;
	private JSONObject header;
	private String method;
	private String body;

	public HttpRequestDto(DataSourceInterfaceConfigDto dataSourceInterfaceConfigDto) {
		url = dataSourceInterfaceConfigDto.getUrl();
		header = dataSourceInterfaceConfigDto.getHeader();
		method = dataSourceInterfaceConfigDto.getMethod();
		body = dataSourceInterfaceConfigDto.getBody();
	}

	public HttpRequestDto(URL url, JSONObject header, String method, String body) {
		this.url = url;
		this.header = header;
		this.method = method;
		this.body = body;
	}

	public HttpRequestDto getPaginatedDto(String endCursor) {
		HttpRequestDto paginatedDto = new HttpRequestDto(url, header, method, body);
		if (endCursor == null) {
			paginatedDto.setFirstPage();
		} else{
			paginatedDto.setNextPage(endCursor);
		}
		return paginatedDto;
	}

	private void setNextPage(String endCursor) {
		StringFormatter stringFormatter = new StringFormatter();
		HashMap<String, String> parameterMap = new HashMap<>();
		parameterMap.put("page", String.format("(after: \\\"%s\\\")", endCursor));
		parameterMap.put("pageElement", String.format(", after: \\\"%s\\\"", endCursor));
		body = stringFormatter.bindParameters(body, parameterMap);
	}

	private void setFirstPage() {
		StringFormatter stringFormatter = new StringFormatter();
		HashMap<String, String> parameterMap = new HashMap<>();
		parameterMap.put("page", "");
		parameterMap.put("pageElement", "");
		body = stringFormatter.bindParameters(body, parameterMap);
	}


	public HttpURLConnection getHttpURLConnection() throws IOException {
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setRequestMethod(method);
		setConnectionHeader(httpURLConnection);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		if (body != null) {
			DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
			dataOutputStream.writeBytes(body);
			dataOutputStream.flush();
			dataOutputStream.close();
		}
		return httpURLConnection;
	}

	private void setConnectionHeader(HttpURLConnection httpURLConnection) {
		httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
		Iterator<String> headerKeys = header.keys();
		while (headerKeys.hasNext()) {
			String headerKey = headerKeys.next();
			httpURLConnection.setRequestProperty(headerKey, String.valueOf(header.get(headerKey)));
		}
	}
}
