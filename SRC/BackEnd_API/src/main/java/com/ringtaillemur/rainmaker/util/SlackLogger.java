package com.ringtaillemur.rainmaker.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ringtaillemur.rainmaker.dto.configdto.DeployProperties;

@Component
public class SlackLogger {
	@Autowired
	DeployProperties deployProperties;
	public void log(String message) throws IOException {
		String slackUrl = deployProperties.slackWebhookUrl;
		LocalTime nowTime = LocalTime.now(ZoneId.of("Asia/Seoul"));
		JSONObject newJsonObj = new JSONObject();
		URL uri = new URL(slackUrl);
		HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setRequestProperty("Content-Type", "application/json;utf-8");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		String newMessage =
			nowTime.toString() +
				"\nBE LOG"+
				"\n----------------------\nMessage : " +
				message;
		newJsonObj.put("text", newMessage);
		OutputStreamWriter streamWriter = new OutputStreamWriter(
			connection.getOutputStream()
		);
		streamWriter.write(newJsonObj.toString());
		streamWriter.flush();
		StringBuilder stringBuilder = new StringBuilder();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), "utf-8")
			);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			bufferedReader.close();
		} else {}
	}

	public void errLog(Exception e,  String message){
		try {
			String slackUrl = deployProperties.slackWebhookUrl;
			LocalTime nowTime = LocalTime.now(ZoneId.of("Asia/Seoul"));
			JSONObject newJsonObj = new JSONObject();
			URL uri = new URL(slackUrl);
			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestProperty("Content-Type", "application/json;utf-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			StackTraceElement[] ste = e.getStackTrace();
			String errStack = "";
			for(int i = 0; i < ste.length; i++){
				errStack = errStack + ste[i].toString();
			}
			String newMessage =
				nowTime.toString() +
					"\nBE Got Err! \n----------------------\nErrLog : " +
					errStack +
					"\n----------------------\nErrMessage : " +
					message;
			newJsonObj.put("text", newMessage);
			OutputStreamWriter streamWriter = new OutputStreamWriter(
				connection.getOutputStream()
			);
			streamWriter.write(newJsonObj.toString());
			streamWriter.flush();
			StringBuilder stringBuilder = new StringBuilder();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "utf-8")
				);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line).append("\n");
				}
				bufferedReader.close();
			} else {}
		} catch (Exception exception) {}
	}


}
