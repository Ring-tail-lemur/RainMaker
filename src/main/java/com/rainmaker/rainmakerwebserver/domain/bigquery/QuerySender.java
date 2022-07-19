package com.rainmaker.rainmakerwebserver.domain.bigquery;

import org.springframework.stereotype.Component;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

@Component
public class QuerySender {
	public TableResult query(String query) throws BigQueryException, InterruptedException {
		BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
		return bigquery.query(queryConfig);
	}
}
