package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.util.List;

import com.ringtaillemur.rainmaker.domain.procedure.ReleaseDetail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeploymentFrequencyDetailDto {
	List<ReleaseDetail> releaseDetail;
}
