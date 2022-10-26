package com.ringtaillemur.rainmaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.OAuthUserRepositoryTable;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.domain.enumtype.OwnerType;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepositoryResponseDto;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import com.ringtaillemur.rainmaker.repository.OAuthUserRepositoryRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;
import com.ringtaillemur.rainmaker.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ringtaillemur.rainmaker.domain.GitUser;
import com.ringtaillemur.rainmaker.repository.GitUserRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RainmakerWebserverApplicationTest {

	@Autowired
	GitUserRepository gitUserRepository;
	
	@Autowired
	RepositoryRepository repositoryRepository;
	
	@Autowired
	OAuthUserRepositoryRepository oAuthUserRepositoryRepository;
	
	@Autowired
	OAuthRepository oAuthRepository;
	
	@Autowired
	UserService userService;

	@Test
	@Transactional
	void test() {
		
		List<Repository> repositories = new ArrayList<>();
		Repository 테스트1 = Repository.builder()
				.id(1L)
				.name("테스트1")
				.ownerOrganizationId(1L)
				.ownerType(OwnerType.ORGANIZATION)
				.build();
		
		Repository 테스트2 = Repository.builder()
				.id(2L)
				.name("테스트2")
				.ownerOrganizationId(1L)
				.ownerType(OwnerType.ORGANIZATION)
				.build();
		repositories.add(테스트1);
		repositories.add(테스트2);
		
		OAuthUser byId = oAuthRepository.findById(81180977L).get();
		
		OAuthUserRepositoryTable build = OAuthUserRepositoryTable.builder()
				.repository(테스트1)
				.oAuthUser(byId)
				.build();
		
		OAuthUserRepositoryTable build2 = OAuthUserRepositoryTable.builder()
				.repository(테스트2)
				.oAuthUser(byId)
				.build();
		
		
		List<OAuthUserRepositoryTable> oAuthUserRepositoryTableList = new ArrayList<>();
		oAuthUserRepositoryTableList.add(build);
		oAuthUserRepositoryTableList.add(build2);
		
//		oAuthUserRepositoryRepository.saveAll(oAuthUserRepositoryTableList);
		byId.setOAuthUserRepositoryTables(oAuthUserRepositoryTableList);
//		repositoryRepository.saveAll(repositories);
		
	}
	
	
	@Test
	@Transactional
	void test2() {
		RegisterRepositoryResponseDto responseDto = new RegisterRepositoryResponseDto();
		List<String> a = new ArrayList<>(List.of("81180977,ring-tail-lemur,test-for-fake-project", "514491457,SWM13-S3,SpringStudy"));
		responseDto.setRepoIds(a);
		userService.registerRepository(responseDto, 81180977L);
	}
	
}
