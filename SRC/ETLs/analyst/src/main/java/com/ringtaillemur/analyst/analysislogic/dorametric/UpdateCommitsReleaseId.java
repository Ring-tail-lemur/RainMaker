package com.ringtaillemur.analyst.analysislogic.dorametric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ringtaillemur.analyst.dto.ReleaseDto;
import com.ringtaillemur.analyst.query.OlapQuery;
import com.ringtaillemur.analyst.query.QueryRunner;
import com.ringtaillemur.analyst.restapi.GetCommitsCompare;

public class UpdateCommitsReleaseId {
	private static final UpdateCommitsReleaseId updateCommitsReleaseId = new UpdateCommitsReleaseId();
	private final QueryRunner queryRunner = QueryRunner.getQueryRunner();
	private final GetCommitsCompare getCommitsCompare = new GetCommitsCompare();

	private UpdateCommitsReleaseId() {
	}

	public static UpdateCommitsReleaseId getUpdateCommitsReleaseId() {
		return updateCommitsReleaseId;
	}

	public void calculateUpdateCommitsReleaseId() throws Exception {
		List<ReleaseDto> releaseDtoList = queryRunner.runSelectReleaseQuery(
			OlapQuery.PUBLISHED_AND_NOT_CALCULATE_LEAD_TIME_FOR_CHANGE_RELEASE);
		ReleaseDto lastCalculatedReleaseDto = queryRunner.runSelectCalculatedReleaseTop1Query(
			OlapQuery.PUBLISHED_AND_CALCULATED_LEAD_TIME_FOR_CHANGE_RELEASE);

		releaseDtoList.add(0, lastCalculatedReleaseDto);

		String joinMergeQuery = String.join(", ", makeJoinMergeQueryList(releaseDtoList));
		queryRunner.runUpdateInsertQuery(String.format(OlapQuery.UPDATE_COMMITS_RELEASE_ID, joinMergeQuery));
	}

	public List<String> makeJoinMergeQueryList(List<ReleaseDto> releaseDtoList) {
		String mergeQueryForm = "('%s', %s)";
		List<String> joinMergeQueryList = new ArrayList<>();
		for (int i = 1; i < releaseDtoList.size(); i++) {
			ReleaseDto targetReleaseDto = releaseDtoList.get(i);
			ReleaseDto previousReleaseDto = releaseDtoList.get(i - 1);
			List<Object> commits;
			if (previousReleaseDto.getRelease_id() == null) {
				commits = getCommitsCompare.getFirstReleaseCommitsBy(
					targetReleaseDto,
					"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").toList();
			} else {

				JSONObject commitsCompareResponseJson = getCommitsCompare.getCommitsCompareBy(targetReleaseDto,
					previousReleaseDto,
					"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");
				commits = ((JSONArray)commitsCompareResponseJson.get("commits")).toList();
			}
			Map<String, String> shaToReleaseId = commits
				.stream()
				.collect(Collectors.toMap(commit -> (String)((Map)commit).get("sha"),
					commit -> targetReleaseDto.getRelease_id()));
			for (Map.Entry<String, String> stringStringEntry : shaToReleaseId.entrySet()) {
				String mergeQueryPiece = String.format(mergeQueryForm, stringStringEntry.getKey(),
					stringStringEntry.getValue());
				joinMergeQueryList.add(mergeQueryPiece);
			}
		}
		return joinMergeQueryList;
	}
}
