package com.ringtaillemur.analyst.restapi;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.microsoft.azure.functions.ExecutionContext;
import org.json.JSONObject;

public class LogModule {
  private static String slackLogBotUri = null;

  private LogModule(ExecutionContext context) throws IOException {
    System.out.println("hihi");
    Logger logger = context.getLogger();
    logger.log(Level.parse("ORG"), "NOW LOGMODULE");
    slackLogBotUri = this.readJson();
  }
  private LogModule() throws IOException {
    System.out.println("hihi");

    slackLogBotUri = this.readJson();
  }
  private static class LazyHolder{
    public static final LogModule INSTANCE;

    static {
      try {
        INSTANCE = new LogModule();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
  public static LogModule getLogModule(ExecutionContext context){
    return LazyHolder.INSTANCE;
  }
  public static LogModule getLogModule(){
    return LazyHolder.INSTANCE;
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
        "\nETL Got Err! \n----------------------\nErrLog : " +
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
    System.out.println(slackSecretJSONObject);
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

  public static void main(String[] args) throws IOException{
    LogModule logModule = new LogModule();
    Exception e = new IOException();
    logModule.sendLog(e, "싱글톤TEST");
  }
}
