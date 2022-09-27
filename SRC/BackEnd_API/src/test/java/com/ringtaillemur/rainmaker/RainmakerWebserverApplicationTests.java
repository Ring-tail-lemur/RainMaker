package com.ringtaillemur.rainmaker;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import com.ringtaillemur.rainmaker.service.UserConfigService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class RainmakerWebserverApplicationTests {

	@Autowired
	UserConfigService userConfigService;
	@Autowired
	LeadTimeForChangeRepository leadTimeForChangeRepository;
	@Test
	void contextLoads() {
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
		Repository info = userConfigService.getRepositoryInfoByGithubApi("Ring-tail-lemur", "private_fake", "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");
		System.out.println(info);
	}

	@Test
	void 웹훅트리거링가능(){
		String s = userConfigService.triggerHistoryCollector("Ring-tail-lemur", "private_fake", "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");
		System.out.println(s);
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
}
