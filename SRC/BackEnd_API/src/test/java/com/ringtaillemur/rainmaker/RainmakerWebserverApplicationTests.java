package com.ringtaillemur.rainmaker;

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
}
