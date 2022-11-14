package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.ringtaillemur.rainmaker.domain.procedure.ReleaseDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DeploymentFrequencyDetailDto {
	private LocalDate releaseDate; // '2022-08-04',
	private String repositoryName; // 'RainMaker',
	private String releaseName; // 'v1.0.0',
	private int commitSize; // '4',
	private int codeChangeSize; // '284',
	private String publishedAt; // '2022-08-04, 14시 32분',
	private int margin; // '100'

	public DeploymentFrequencyDetailDto(ReleaseDetail releaseDetail, int diffDays) {
		releaseDate = releaseDetail.getPublishedAt().toLocalDate();
		repositoryName = releaseDetail.getRepositoryName();
		releaseName = releaseDetail.getReleaseName();
		commitSize = releaseDetail.getCommitSize();
		codeChangeSize = releaseDetail.getCodeChangeSize();
		publishedAt = releaseDetail.getPublishedAt().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
		margin = diffDays * 50;
	}
}
