package org.example.functions.dto;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HttpRequestDto {
	private String requestType;
	private URL url;
	private JSONObject header;
	private String method;
	private String body;

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
