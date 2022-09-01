package org.example.functions.api_service.github;

import java.io.IOException;
import java.util.Map;

public class GithubRestApiSender {

	public void sendGithubRestApi(Map<String, String> requestMap, String token) throws IOException {
		// URL url = new URL(getRequestUrl(requestMap));
		// HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		//
		// httpURLConnection.setRequestMethod(requestMap.get("method").toUpperCase());
		// httpURLConnection.setRequestProperty("accept", "application/vnd.github+json");
		// httpURLConnection.setRequestProperty("Authorization", "token " + token);
		// httpURLConnection.setDoOutput(true);
		//
		// BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		// StringBuilder stringBuilder = new StringBuilder();
		// String line;
		//
		// while ((line = bufferedReader.readLine()) != null) {
		// 	stringBuilder.append(line);
		// }
		// String resultString = stringBuilder.toString();
		// if (resultString.charAt(0) == '{') {
		// 	return Arrays.asList(configMapper.parseStringToMap(resultString));
		// }
		// return configMapper.parseStringToList(resultString)
		// 	.stream()
		// 	.map(stringMap -> configMapper.parseStringToMap(stringMap))
		// 	.collect(Collectors.toList());
		// return
	}
}
