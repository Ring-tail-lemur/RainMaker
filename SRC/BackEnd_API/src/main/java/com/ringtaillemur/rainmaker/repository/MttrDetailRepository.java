package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.view.MttrDetail;
import com.ringtaillemur.rainmaker.domain.view.MttrDetailData;

public interface MttrDetailRepository extends JpaRepository<MttrDetail, MttrDetailData> {

	List<MttrDetail> findByDataRepositoryIdInAndDataEndTimeBetween(List<Long> repositoryIds, LocalDateTime startTime,
		LocalDateTime endTime);
}
