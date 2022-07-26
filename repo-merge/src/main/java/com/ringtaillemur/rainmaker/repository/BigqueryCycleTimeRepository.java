package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;
import com.ringtaillemur.rainmaker.domain.bigquery.QuerySender;
import com.ringtaillemur.rainmaker.dto.domaindto.CycleTimeDto;
import com.ringtaillemur.rainmaker.util.contant.productiveity_boundery.BigQueryMetaData;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class BigqueryCycleTimeRepository {

	private final QuerySender querySender;

	public List<CycleTimeDto> findCycleTimesByCycleTimeEndBetween(LocalDateTime measurementStartTime,
		LocalDateTime measurementEndTime) throws BigQueryException, InterruptedException {
		String query = String.format("SELECT * FROM `%s.%s.%s`", BigQueryMetaData.PROJECT_ID,
			BigQueryMetaData.DATASET_NAME, BigQueryMetaData.TABLE_NAME);
		TableResult queryResult = querySender.query(query);
		return getCycleTimeDtoList(queryResult);
	}

	private List<CycleTimeDto> getCycleTimeDtoList(TableResult tableResult) {
		ArrayList<CycleTimeDto> cycleTimeDtoList = new ArrayList<>();
		Iterable<FieldValueList> values = tableResult.getValues();
		values.iterator().forEachRemaining(value -> cycleTimeDtoList.add(new CycleTimeDto(value)));
		log.info(cycleTimeDtoList.toString());
		return cycleTimeDtoList;
	}
}
