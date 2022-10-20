package org.example.functions.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.ZoneId;

import org.json.JSONObject;

public class SlackLogger {
	private static String slackLogBotUri = null;
	private SlackLogger() throws IOException {
		slackLogBotUri = this.readJson();
	}

	private static class LazyHolder{
		public static final SlackLogger INSTANCE;

		static {
			try {
				INSTANCE = new SlackLogger();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static SlackLogger getSlackLogger(){
		return LazyHolder.INSTANCE;
	}
	public void sendLogNotErr(String message){
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
			String newMessage =
					nowTime.toString() +
							"\nHistoryCollector Message!" +
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
		} catch (Exception exception) {}
	}
	public void sendLog(Exception e, String message) throws IOException {
		try {
			LocalTime nowTime = LocalTime.now(ZoneId.of("Asia/Seoul"));
			System.out.println(slackLogBotUri);
			JSONObject newJsonObj = new JSONObject();
			URL uri = new URL(slackLogBotUri);
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
							"\nHistoryCollector Got Err! \n----------------------\nErrLog : " +
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

	private String readJson() throws IOException {
		String slackSecret = readFile("static/slack-secret.json");
		JSONObject slackSecretJSONObject = new JSONObject(slackSecret);
		return slackSecretJSONObject.getString("slack_uri");
	}

	private String readFile(String filePath) throws IOException {
		System.out.println("now Reading File!");
		InputStream input = ClassLoader.getSystemResourceAsStream(filePath);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while (true) {
			try {
				if ((length = input.read(buffer)) == -1) break;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
			result.write(buffer, 0, length);
		}
		try {
			return result.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args){
		SlackLogger slackLogger = SlackLogger.getSlackLogger();
		slackLogger.sendLogNotErr("...");
	}
}
