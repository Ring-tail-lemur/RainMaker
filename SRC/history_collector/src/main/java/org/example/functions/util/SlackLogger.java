package org.example.functions.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.ZoneId;

import org.json.JSONObject;

public class SlackLogger {
	private final FileReader fileReader = FileReader.getInstance();
	private String slackLogBotUri = null;
	public SlackLogger() throws IOException {
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
		}
	}

	private String readJson() throws IOException{
		String slackSecret = fileReader.readFile("/slack-secret.json");
		JSONObject slackSecretJSONObject = new JSONObject(slackSecret);
		return slackSecretJSONObject.getString("slack_uri");
	}
}
