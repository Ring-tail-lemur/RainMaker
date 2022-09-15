package com.ringtaillemur.rainmaker;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.UserConfigService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RainmakerWebserverApplicationTests {

	@Autowired
	UserConfigService userConfigService;
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
}
