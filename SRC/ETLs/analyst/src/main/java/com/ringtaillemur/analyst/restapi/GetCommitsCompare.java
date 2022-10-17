package com.ringtaillemur.analyst.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
			JSONArray resultJSONArray = new JSONArray();
			int page = 1;
			while (true) {
				URL url = new URL(
					String.format("https://api.github.com/repos/%s/%s/commits?sha=%s&per_page=100&page=%d",
						owner, repo, tagName, page));
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();

				conn.setRequestMethod("GET"); // http 메서드
				conn.setRequestProperty("accept", "application/vnd.github+json"); // header Content-Type 정보
				conn.setRequestProperty("Authorization", "token " + token); // header Content-Type 정보
				conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

				// 서버로부터 데이터 읽어오기
				BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
				StringBuilder sb = new StringBuilder();

				String line = null;
				while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
					sb.append(line);
				}
				JSONArray resultArray = new JSONArray(sb.toString());
				if (resultArray.isEmpty()) {
					break;
				}
				resultJSONArray.putAll(resultArray);
				page += 1;
			}
			return resultJSONArray;
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray();
		}
	}

	public JSONArray getCommitsCompareBy(ReleaseDto targetReleaseDto, ReleaseDto previousReleaseDto,
		String token) throws
		IOException, ParseException {
		try {
			String owner = targetReleaseDto.getOwnerName();
			String repo = targetReleaseDto.getRepositoryName();
			String basehead2 = targetReleaseDto.getTagName();
			String basehead1 = previousReleaseDto.getTagName();
			JSONArray result = new JSONArray();
			int page = 1;
			while (true) {
				URL url = new URL(
					String.format("https://api.github.com/repos/%s/%s/compare/%s...%s?per_page=100&page=%d",
						owner, repo, basehead1, basehead2, page));
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
				JSONObject singleResult = new JSONObject(sb.toString());
				JSONArray resultJOSNArray = singleResult.getJSONArray("commits");
				if (resultJOSNArray.isEmpty()) {
					break;
				}
				result.putAll(resultJOSNArray);
				page += 1;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray();
		}
	}
}
