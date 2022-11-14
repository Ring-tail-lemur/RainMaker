package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.Duration;
import java.time.LocalDateTime;

import com.ringtaillemur.rainmaker.domain.view.MttrDetail;
import com.ringtaillemur.rainmaker.domain.view.MttrDetailData;

import lombok.Data;

@Data
public class MttrDetailDto {
	private String url;
	private Long startTime;
	private Long codingTime;
	private Long pickupTime;
	private Long reviewTime;
	private Long deployTime;
	private Long convinceTime;
	private Long issueNumber;
	private Long mttr;

	public MttrDetailDto(MttrDetail mttrDetail) {
		MttrDetailData mttrDetailData = mttrDetail.getData();
		url = mttrDetailData.getIssueUrl();
		startTime = getMinDuration(mttrDetailData.getFindTime(), mttrDetailData.getFirstCommitTime());
		codingTime = getMinDuration(mttrDetailData.getFirstCommitTime(), mttrDetailData.getPrOpenTime());
		pickupTime = getMinDuration(mttrDetailData.getFirstCommitTime(), mttrDetailData.getFirstReviewTime());
		reviewTime = getMinDuration(mttrDetailData.getFirstReviewTime(), mttrDetailData.getPrCloseTime());
		deployTime = getMinDuration(mttrDetailData.getPrCloseTime(), mttrDetailData.getDeploymentTime());
		convinceTime = getMinDuration(mttrDetailData.getDeploymentTime(), mttrDetailData.getEndTime());
		issueNumber = mttrDetail.getData().getIssueNumber();
		mttr = getMinDuration(mttrDetailData.getFindTime(), mttrDetailData.getEndTime());
	}

	private static long getMinDuration(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
		LocalDateTime filterDate = LocalDateTime.of(2, 1, 1, 0, 0, 0);
		if (localDateTime1.isBefore(filterDate) || localDateTime2.isBefore(filterDate)) {
			return -1;
		}
		return Duration.between(localDateTime1, localDateTime2).toMinutes();
	}
}
