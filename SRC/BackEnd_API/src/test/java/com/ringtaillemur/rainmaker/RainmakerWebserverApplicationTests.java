package com.ringtaillemur.rainmaker;

import com.ringtaillemur.rainmaker.service.UserConfigService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RainmakerWebserverApplicationTests {

	@Autowired
	UserConfigService userConfigService;
	@Test
	void contextLoads() {
	}

	@Test
	void 과연Organ리스트를뽑아올까요() {
		JSONArray p = userConfigService.getOrganizationListByGithubApi("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");
		for(var i : p){
			System.out.println( ((JSONObject)i).getString("login") );
		}


	}
}
