package com.ringtaillemur.rainmaker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.ringtaillemur.rainmaker.domain.GitOrganization;
import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.OAuthUserRepositoryTable;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import com.ringtaillemur.rainmaker.dto.historycollectordto.HistoryCollector;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RepositoryInfoDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.repository.GitOrganizationRepository;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import com.ringtaillemur.rainmaker.repository.OAuthUserRepositoryRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;
import com.ringtaillemur.rainmaker.service.UserConfigService;

@SpringBootTest
@Rollback(value = false)
class RainmakerWebserverApplicationTests {

	@Autowired
	UserConfigService userConfigService;
	@Autowired
	LeadTimeForChangeRepository leadTimeForChangeRepository;
	@Autowired
	OAuthUserRepositoryRepository oAuthUserRepositoryRepository;
	@Autowired
	OAuthRepository oAuthRepository;
	@Autowired
	RepositoryRepository repositoryRepository;
	@Autowired
	GitOrganizationRepository gitOrganizationRepository;
	@Test
	void OrganizationInsert() {
		GitOrganization gitOrganization = GitOrganization.builder()
			.id(123123124L)
			.name("ring-tail-remer")
			.build();
		gitOrganizationRepository.save(gitOrganization);
	}
	@Test
	void contextLoads() {
		oAuthRepository.save(new OAuthUser(123L, "helwe", "asedf.sd", "asdfas", OauthUserLevel.FIRST_AUTH_USER));
	}

	@Test
	void 과연Repo리스트를뽑아올까요() {
		List<UserRepositoryDto> p = userConfigService.getUserRepositoryDtoByToken("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");

		for(var i : p) {
			System.out.print("id : " + i.getId());
			System.out.print(" / Organization : " + i.getOrganization());
			System.out.print(" / Repository : " + i.getRepository());
			System.out.println(" / Pushed_at : " + i.getPushedAt());
		}
	}

	@Test
	void 과연웹훅을등록할수있을까요() {
		String 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 = userConfigService.setUserWebhookByRepoName("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", "Ring-tail-lemur", "test-for-fake-project");

		System.out.println(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);
	}

	@Test
	void 리포지토리정보가져오기() {
		// Repository info = userConfigService.getRepositoryInfoByGithubApi("Ring-tail-lemur", "private_fake", "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");
		// System.out.println(info);
	}
	@Test
	void 웹훅트리거링가능(){

		List<HistoryCollector> h = new ArrayList<>();
		h.add(new HistoryCollector("Ring-tail-lemur", "private_fake", "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"));
		userConfigService.triggerHistoryCollector(h);
	}

	@Test
	void 여러개의리포지토리로지표뽑아와보기() {
		LocalDate startTime = LocalDate.parse("2022-08-01");
		LocalDate endTime = LocalDate.parse("2022-09-28");
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();
		List<Long> repositories = new ArrayList<>(Arrays.asList(517528822L, 510731046L));
		List<LeadTimeForChange> byRepositoryIdInAndDeploymentTimeBetween = leadTimeForChangeRepository.findByRepositoryIdInAndDeploymentTimeBetween(repositories, startDateTime, endDateTime);
	}

	@Test
	@Transactional(rollbackFor = Exception.class)
	void 유저아이디기준으로모든엔티티삭제() {

		OAuthUser oAuthUser = oAuthRepository.findById(81180977L).get();
		List<OAuthUserRepositoryTable> oAuthUserRepositories = oAuthUserRepositoryRepository.findByoAuthUser(oAuthUser);
		List<RepositoryInfoDto> collect = oAuthUserRepositories.stream()
				.map(OAuthUserRepositoryTable::getRepository)
				.map(Repository::getRepositoryInfoDto)
				.collect(Collectors.toList());

		return;
	}

	@Test
	void WebClient테스트() {

		List<HistoryCollector> historyCollectorList = new ArrayList<>();
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("jonghyun").repoName("test1").token("1234").build());
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("jonghyun").repoName("test2").token("1234").build());
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("jonghyun").repoName("test3").token("1234").build());
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("jonghyun").repoName("test4").token("1234").build());
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("inhyeok").repoName("test5").token("1234").build());

//		List<String> historyCollectorStringList = historyCollectorList.stream().map(HistoryCollector::toString).collect(Collectors.toList());
//		String format = String.format("/api/HttpExample?%s", historyCollectorStringList);


		WebClient webClient = WebClient.builder()
				.baseUrl("https://webhook.site/05d4882c-f7cb-4ca9-97f2-36ca09c851f9")
				.build();
		webClient.post().accept(MediaType.APPLICATION_JSON)
				.bodyValue(historyCollectorList)
				.exchange()
				.flux()
				.subscribe( (result) -> {
					changeAuthority(result);
				});

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("==========END==========");
		}
	}

	private void changeAuthority(ClientResponse result) {
		HttpStatus httpStatus = result.statusCode();
		System.out.println(httpStatus);
		if (result.statusCode().is2xxSuccessful()) {
			Optional<OAuthUser> id = oAuthRepository.findById(81180977L);
			OAuthUser oAuthUser = id.orElseThrow();
			oAuthUser.setUserLevel(OauthUserLevel.FIRST_AUTH_USER);
			oAuthRepository.save(oAuthUser);
		}
	}

	@Test
	void 파라미터테스트() {
		List<HistoryCollector> historyCollectorList = new ArrayList<>();
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("jonghyun").repoName("test1").token("1234").build());
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("jonghyun").repoName("test2").token("1234").build());
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("jonghyun").repoName("test3").token("1234").build());
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("jonghyun").repoName("test4").token("1234").build());
		historyCollectorList.add(HistoryCollector.builder()
				.ownerName("inhyeok").repoName("test5").token("1234").build());

		List<String> historyCollectorStringList = historyCollectorList.stream().map(HistoryCollector::toString).collect(Collectors.toList());
		String format = String.format("/api/HttpExample?%s", historyCollectorStringList);
		System.out.println(format);
	}

}
