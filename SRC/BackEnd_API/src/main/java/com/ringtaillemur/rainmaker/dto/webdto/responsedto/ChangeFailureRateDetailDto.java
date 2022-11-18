package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.ringtaillemur.rainmaker.domain.procedure.ChangeFailureRateDetail;
import com.ringtaillemur.rainmaker.domain.procedure.ReleaseDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ChangeFailureRateDetailDto {
	private LocalDate releaseDate; // '2022-08-04',
	private String repositoryName; // 'RainMaker',
	private String releaseName; // 'v1.0.0',
	private int commitSize; // '4',
	private int codeChangeSize; // '284',
	private String publishedAt; // '2022-08-04, 14시 32분',
	private int margin; // '100'
	private boolean isSuccess;

	public ChangeFailureRateDetailDto(ChangeFailureRateDetail changeFailureRateDetail, int diffDays) {
		releaseDate = changeFailureRateDetail.getPublishedAt().toLocalDate();
		repositoryName = changeFailureRateDetail.getRepositoryName();
		releaseName = changeFailureRateDetail.getReleaseName();
		commitSize = changeFailureRateDetail.getCommitSize();
		codeChangeSize = changeFailureRateDetail.getCodeChangeSize();
		publishedAt = changeFailureRateDetail.getPublishedAt().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
		isSuccess = changeFailureRateDetail.isSuccess();
		margin = diffDays * 50;
	}
}
