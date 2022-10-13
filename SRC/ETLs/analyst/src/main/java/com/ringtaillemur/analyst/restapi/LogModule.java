package com.ringtaillemur.analyst.restapi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
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

  public void sendLog(Exception e, String message) throws IOException {
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
      String newMessage =
        nowTime.toString() +
        "\nETL Got Err! \n----------------------ErrLog : " +
        e +
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

  private String readJson() {
    String slackSecret = readFile("./slack-secret.json");
    JSONObject slackSecretJSONObject = new JSONObject(slackSecret);
    System.out.println(slackSecretJSONObject);
    return slackSecretJSONObject.getString("slack_uri");
  }

  private String readFile(String filePath) {
    InputStream input = this.getClass().getResourceAsStream(filePath);
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    while (true) {
      try {
        if ((length = input.read(buffer)) == -1) break;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      result.write(buffer, 0, length);
    }
    try {
      return result.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
