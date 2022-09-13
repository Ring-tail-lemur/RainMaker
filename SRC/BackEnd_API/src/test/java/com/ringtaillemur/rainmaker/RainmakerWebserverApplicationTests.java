package com.ringtaillemur.rainmaker;

import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.UserConfigService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class RainmakerWebserverApplicationTests {

	@Autowired
	UserConfigService userConfigService;
	@Test
	void contextLoads() {
	}

	@Test
	void 과연Repo리스트를뽑아올까요() {
		ArrayList<UserRepositoryDto> p = userConfigService.getUserRepositoryDtoByToken("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");

		for(var i : p) {
			System.out.print("id : " + i.getId());
			System.out.print(" / Organization : " + i.getOrganization());
			System.out.print(" / Repository : " + i.getRepository());
			System.out.println(" / Pushed_at : " + i.getPushed_at());
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
}
