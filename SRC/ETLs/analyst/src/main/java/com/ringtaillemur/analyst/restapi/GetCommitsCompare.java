package com.ringtaillemur.analyst.restapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ringtaillemur.analyst.dto.ReleaseDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GetCommitsCompare {

	public JSONArray getFirstReleaseCommitsBy(ReleaseDto targetReleaseDto, String token) {

		try {
			String owner = targetReleaseDto.getOwner_name();
			String repo = targetReleaseDto.getRepository_name();
			String tagName = URLEncoder.encode(targetReleaseDto.getTag_name(), "UTF-8");
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
			return new JSONArray();
		}
	}

	public JSONObject getCommitsCompareBy(ReleaseDto targetReleaseDto, ReleaseDto previousReleaseDto, String token) {
		try {
			String owner = targetReleaseDto.getOwner_name();
			String repo = targetReleaseDto.getRepository_name();
			String basehead1 = targetReleaseDto.getTag_name();
			String basehead2 = previousReleaseDto.getTag_name();
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
			return new JSONObject();
		}
	}
}
