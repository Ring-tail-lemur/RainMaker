package com.ringtaillemur.rainmaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ringtaillemur.rainmaker.domain.*;
import com.ringtaillemur.rainmaker.domain.enumtype.OwnerType;
import com.ringtaillemur.rainmaker.domain.procedure.ReleaseDetail;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepositoryResponseDto;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import com.ringtaillemur.rainmaker.repository.OAuthUserRepositoryRepository;
import com.ringtaillemur.rainmaker.repository.ReleaseDetailRepository;
import com.ringtaillemur.rainmaker.repository.ReleaseSuccessRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;
import com.ringtaillemur.rainmaker.service.UserService;
import com.ringtaillemur.rainmaker.service.dorametrics.LeadTimeForChangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

	@Autowired
	ReleaseSuccessRepository releaseSuccessRepository;

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
	
	@Autowired
	LeadTimeForChangeService leadTimeForChangeService;

	@Autowired
	ReleaseDetailRepository releaseDetailRepository;
	@Test
	@Transactional
	@Rollback(value = false)
	void test4() {
		List<Long> repositoryIds = new ArrayList<>();
		repositoryIds.add(517528822L);
		repositoryIds.add(544985444L);
		// List<ReleaseDetailRepository.res> res = releaseDetailRepository.varcharToRepoIdProcedure(
		// 	"517528822, 544985444");
		// System.out.println(res.get(0).getValue() + ", " + res.get(1).getValue());
		// List<ReleaseDetailRepository.res> releaseDetails = releaseDetailRepository.releaseDetailProcedure("517528822,544985444,510731046");
		List<ReleaseDetail> releaseDetails = releaseDetailRepository.releaseDetailProcedure("517528822,544985444,510731046");

		// System.out.println(releaseDetails[0]);
		// System.out.println(releaseDetails[1]);

		System.out.println("HI");
		releaseDetails.stream().map(releaseDetail -> {
			// System.out.println(releaseDetail.getRelease_name());
			System.out.println("releaseDetail = " + releaseDetail.getReleaseName());
			return null;
		});

	}

}
