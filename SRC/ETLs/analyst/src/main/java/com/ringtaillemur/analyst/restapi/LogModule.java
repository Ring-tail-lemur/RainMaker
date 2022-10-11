package com.ringtaillemur.analyst.restapi;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LogModule {
	private String slackLogBotUri = null;
	public LogModule() throws IOException, ParseException {
		slackLogBotUri = this.readJson();
	}
	public void sendLog(Exception e,  String message) throws IOException {
		try {
			LocalTime nowTime = LocalTime.now(ZoneId.of("Asia/Seoul"));
			System.out.println(this.slackLogBotUri);
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

	private String readJson() throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Reader reader = new FileReader("./slack-secret.json");
		System.out.println("Hihi");
		org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(reader);
		System.out.println(jsonObject.toString());
		return (String) jsonObject.get("slack_uri");
	}
	public static void main(String[] args) throws IOException, ParseException {
		LogModule logModule = new LogModule();
		logModule.sendLog(new Exception(), "ㅅㅂㅅㅂ");
	}
}