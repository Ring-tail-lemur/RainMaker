package org.example.functions.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.ZoneId;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlackLogger {
	private String slackLogBotUri = null;
	public SlackLogger() throws IOException,ParseException {
		slackLogBotUri = this.readJson();
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
			String newMessage = nowTime.toString() + "\nHistoryCollector Got Err! \n----------------------ErrLog : "+ e + "\n----------------------\nErrMessage : "+ message;
			newJsonObj.put("text", newMessage);
			OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
			streamWriter.write(newJsonObj.toString());
			streamWriter.flush();
			StringBuilder stringBuilder = new StringBuilder();
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
				String line;
				while((line = bufferedReader.readLine()) != null){
					stringBuilder.append(line).append("\n");
				}
				bufferedReader.close();
			}
		}catch (Exception exception){
			log.error(exception.getMessage());
		}
	}

	private String readJson() throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Reader reader = new java.io.FileReader("./slack-secret.json");
		org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(reader);
		return (String) jsonObject.get("slack_uri");
	}
}
