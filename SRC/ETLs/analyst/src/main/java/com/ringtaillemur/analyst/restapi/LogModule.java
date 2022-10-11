package com.ringtaillemur.analyst.restapi;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.json.JSONObject;

public class LogModule {
	private String slackLogBotUri = "https://hooks.slack.com/services/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";
	LogModule logModule(){
		return this;
	}
	public void sendLog(Exception e,  String message) throws IOException {
		try {
			LocalTime nowTime = LocalTime.now(ZoneId.of("Asia/Seoul"));
			JSONObject newJsonObj = new JSONObject();
			URL uri = new URL(slackLogBotUri);
			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestProperty("Content-Type", "application/json;utf-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			String newMessage = nowTime.toString() + "\nETL Got Err! \n----------------------ErrLog : "+ e + "\n----------------------\nErrMessage : "+ message;
			newJsonObj.put("text", newMessage);
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write(newJsonObj.toString());
			streamWriter.flush();
			StringBuilder stringBuilder = new StringBuilder();
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "utf-8"));
				String line;
				while((line = bufferedReader.readLine()) != null){
					stringBuilder.append(line).append("\n");
				}
				bufferedReader.close();
				System.out.println("" + stringBuilder.toString());
			}else{
				System.out.println(connection.getResponseMessage());
			}
		}catch (Exception exception){
			System.err.println(exception.toString());
		}
	}
	// public static void main(String[] args) throws IOException {
	// 	LogModule logModule = new LogModule();
	// 	logModule.sendLog(new Exception(), "dㅁㄴㄹㅇㄴㅁㄹd");
	// }
}