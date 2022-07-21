package com.rainmaker.rainmakerwebserver.domain.bigquery;

import org.springframework.stereotype.Component;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class QuerySender {
	public TableResult query(String query) throws BigQueryException, InterruptedException {
		log.info(query);
		BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
		return bigquery.query(queryConfig);
	}
}
