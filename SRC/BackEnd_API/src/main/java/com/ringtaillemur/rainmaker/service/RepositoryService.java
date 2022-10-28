package com.ringtaillemur.rainmaker.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.GitOrganization;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.domain.enumtype.OwnerType;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RepositoryDetailDto;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;
import com.ringtaillemur.rainmaker.util.SlackLogger;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RepositoryService {

	private final RepositoryRepository repositoryRepository;
	private final OAuthUserRepositoryService oAuthUserRepositoryService;
	@Autowired
	public SlackLogger slackLogger;
	public List<Repository> findRepositories(List<Repository> repositoryList) {

		return repositoryRepository.findByIdsIn(repositoryList.stream().map(Repository::getId).toList());
		// List<OAuthUserRepositoryTable> oAuthUserRepositoryTableList = oAuthUserRepositoryService
		// 	.getRepositoryByOAuthUser(oAuthUser);
		// return oAuthUserRepositoryTableList.stream()
		// 	.map(OAuthUserRepositoryTable::getRepository)
		// 	.toList();
	}

	public Optional<Repository> getRepositoryById(Long repositoryId) {
		return repositoryRepository.findById(repositoryId);
	}

	public void saveAllRepositories(List<Repository> repositoryList) {
		repositoryRepository.saveAll(repositoryList);
	}


	public Repository changeRepositoryDetailToRepository(String token, Set<GitOrganization> gitOrganizationSet, RepositoryDetailDto repositoryDetailDto) {
		try {
			URL url = new URL(String.format("https://api.github.com/repos/%s/%s", repositoryDetailDto.getOrganizationName(), repositoryDetailDto.getRepositoryName()));
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "application/vnd.github+json"); // header Content-Type 정보
			conn.setRequestProperty("Authorization", "Bearer " + token); // header Content-Type 정보
			conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
				sb.append(line);
			}

			JSONObject eachRepository = new JSONObject(sb.toString());

			Long ownerId = eachRepository.getJSONObject("owner").getLong("id");

			Repository repository = repositoryRepository.findById(eachRepository.getLong("id")).orElseGet(
				() -> Repository.builder()
					.id(eachRepository.getLong("id"))
					.name(repositoryDetailDto.getRepositoryName())
					.ownerType(OwnerType.ORGANIZATION)
					.ownerOrganizationId(ownerId)
					.build()
			);

			GitOrganization gitOrganization = GitOrganization.builder()
				.id(ownerId)
				.name(repositoryDetailDto.getOrganizationName())
				.build();



			gitOrganizationSet.add(gitOrganization);
			return repository;
		} catch (Exception e) {
			slackLogger.errLog(e,"RepositoryService");
			e.printStackTrace();
			return null;
		}
	}

}
