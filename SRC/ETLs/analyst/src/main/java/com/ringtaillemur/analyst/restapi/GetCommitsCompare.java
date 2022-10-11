package com.ringtaillemur.analyst.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.ringtaillemur.analyst.dto.ReleaseDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GetCommitsCompare {

	public JSONArray getFirstReleaseCommitsBy(ReleaseDto targetReleaseDto, String token) throws
		IOException,
		ParseException {

		try {
			String owner = targetReleaseDto.getOwnerName();
			String repo = targetReleaseDto.getRepositoryName();
			String tagName = URLEncoder.encode(targetReleaseDto.getTagName(), "UTF-8");
			URL url = new URL(String.format("https://api.github.com/repos/%s/%s/commits?sha=%s", owner, repo,
				tagName));
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("GET"); // http 메서드
			conn.setRequestProperty("accept", "application/vnd.github+json"); // header Content-Type 정보
			conn.setRequestProperty("Authorization", "token " + token); // header Content-Type 정보
			conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

			// 서버로부터 데이터 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
				sb.append(line);
			}
			return new JSONArray(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LogModule logModule = new LogModule();
			logModule.sendLog(e, "RepositoryRepository // getFirstReleaseCommitsBy");
			return new JSONArray();
		}
	}

	public JSONObject getCommitsCompareBy(ReleaseDto targetReleaseDto, ReleaseDto previousReleaseDto, String token) throws
		IOException, ParseException {
		try {
			String owner = targetReleaseDto.getOwnerName();
			String repo = targetReleaseDto.getRepositoryName();
			String basehead2 = targetReleaseDto.getTagName();
			String basehead1 = previousReleaseDto.getTagName();
			URL url = new URL(
				String.format("https://api.github.com/repos/%s/%s/compare/%s...%s", owner, repo, basehead1, basehead2));
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("GET"); // http 메서드
			conn.setRequestProperty("accept", "application/vnd.github+json"); // header Content-Type 정보
			conn.setRequestProperty("Authorization", "token " + token); // header Content-Type 정보
			conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

			// 서버로부터 데이터 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
				sb.append(line);
			}
			return new JSONObject(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LogModule logModule = new LogModule();
			logModule.sendLog(e, "RepositoryRepository // getCommitsCompareBy");
			return new JSONObject();
		}
	}
}
