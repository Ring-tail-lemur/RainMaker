package com.ringtaillemur.analyst.analysislogic.dorametric;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ringtaillemur.analyst.dto.ReleaseDto;
import com.ringtaillemur.analyst.query.OlapQuery;
import com.ringtaillemur.analyst.query.QueryRunner;
import com.ringtaillemur.analyst.repository.RepositoryRepository;
import com.ringtaillemur.analyst.restapi.GetCommitsCompare;

public class UpdateCommitsReleaseId {
	private static final UpdateCommitsReleaseId updateCommitsReleaseId = new UpdateCommitsReleaseId();
	private final QueryRunner queryRunner = QueryRunner.getQueryRunner();
	private final GetCommitsCompare getCommitsCompare = new GetCommitsCompare();
	private final RepositoryRepository repositoryRepository = new RepositoryRepository();

	private UpdateCommitsReleaseId() {
	}

	public static UpdateCommitsReleaseId getUpdateCommitsReleaseId() {
		return updateCommitsReleaseId;
	}

	public void calculateUpdateCommitsReleaseId() throws IOException {
		List<ReleaseDto> releaseDtoList = queryRunner.runSelectReleaseQuery(
			OlapQuery.PUBLISHED_AND_NOT_CALCULATE_LEAD_TIME_FOR_CHANGE_RELEASE);
		List<List<ReleaseDto>> dividedReleaseDtoList = divideReleaseDtoList(releaseDtoList);
		for (List<ReleaseDto> releaseDtos : dividedReleaseDtoList) {
			ReleaseDto lastCalculatedReleaseDto = queryRunner.runSelectCalculatedReleaseTop1Query(
				OlapQuery.PUBLISHED_AND_CALCULATED_LEAD_TIME_FOR_CHANGE_RELEASE);
			releaseDtos.add(0, lastCalculatedReleaseDto);
			String joinMergeQuery = String.join(", ", makeJoinMergeQueryList(releaseDtos));
			queryRunner.runUpdateInsertQuery(String.format(OlapQuery.UPDATE_COMMITS_RELEASE_ID, joinMergeQuery));
		}
	}

	public List<List<ReleaseDto>> divideReleaseDtoList(List<ReleaseDto> releaseDtoList) {
		List<List<ReleaseDto>> dividedReleaseDtoList = new ArrayList<>();
		releaseDtoList.forEach(releaseDto -> addReleaseDtoListToGroup(dividedReleaseDtoList, releaseDto));
		return dividedReleaseDtoList;
	}

	private void addReleaseDtoListToGroup(List<List<ReleaseDto>> dividedReleaseDtoList,
		ReleaseDto releaseDto) {
		Optional<List<ReleaseDto>> optionalRepositoryGroup = dividedReleaseDtoList.stream()
			.filter(
				repositoryGroup -> repositoryGroup.get(0).getRepositoryName().equals(releaseDto.getRepositoryName()))
			.findAny();
		if (optionalRepositoryGroup.isPresent()) {
			List<ReleaseDto> releaseDtoList = optionalRepositoryGroup.get();
			releaseDtoList.add(releaseDto);
		} else {
			ArrayList<ReleaseDto> releaseDtoList = new ArrayList<>();
			releaseDtoList.add(releaseDto);
			dividedReleaseDtoList.add(releaseDtoList);
		}
	}

	public List<String> makeJoinMergeQueryList(List<ReleaseDto> releaseDtoList) throws IOException {

		String mergeQueryForm = "('%s', %s)";
		List<String> joinMergeQueryList = new ArrayList<>();
		for (int i = 1; i < releaseDtoList.size(); i++) {
			ReleaseDto targetReleaseDto = releaseDtoList.get(i);
			String token = repositoryRepository.getOneTokenByRepositoryId(targetReleaseDto.getRepositoryId());
			ReleaseDto previousReleaseDto = releaseDtoList.get(i - 1);
			List<Object> commits;
			if (previousReleaseDto.getReleaseId() == null) {
				commits = getCommitsCompare.getFirstReleaseCommitsBy(
					targetReleaseDto,
					token).toList();
			} else {
				JSONObject commitsCompareResponseJson = getCommitsCompare.getCommitsCompareBy(targetReleaseDto,
					previousReleaseDto,
					token);
				commits = ((JSONArray)commitsCompareResponseJson.get("commits")).toList();
			}
			Map<String, String> shaToReleaseId = commits
				.stream()
				.collect(Collectors.toMap(commit -> (String)((Map<?, ?>)commit).get("sha"),
					commit -> targetReleaseDto.getReleaseId()));
			for (Map.Entry<String, String> stringStringEntry : shaToReleaseId.entrySet()) {
				String mergeQueryPiece = String.format(mergeQueryForm, stringStringEntry.getKey(),
					stringStringEntry.getValue());
				joinMergeQueryList.add(mergeQueryPiece);
			}
		}
		return joinMergeQueryList;
	}
}
