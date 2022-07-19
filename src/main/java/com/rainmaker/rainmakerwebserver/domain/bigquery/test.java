package com.rainmaker.rainmakerwebserver.domain.bigquery;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import com.rainmaker.rainmakerwebserver.util.contant.productiveity_boundery.BigQueryMetaData;

import lombok.RequiredArgsConstructor;

// Sample to query in a table
public class test {



	public static void main(String[] args) throws InterruptedException {
		// TODO(developer): Replace these variables before running the sample.
		String query =
			"SELECT *\n"
				+ " FROM `"
				+ BigQueryMetaData.PROJECT_ID
				+ "."
				+ BigQueryMetaData.DATASET_NAME
				+ "."
				+ BigQueryMetaData.TABLE_NAME
				+ "`";
		query(query);
	}

	public static void query(String query) {
		try {
			// Initialize client that will be used to send requests. This client only needs to be created
			// once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

			QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

			TableResult results = bigquery.query(queryConfig);

			results
				.iterateAll()
				.forEach(row -> row.forEach(val -> System.out.printf("%s,", val.toString())));

			System.out.println("Query performed successfully.");
		} catch (BigQueryException | InterruptedException e) {
			System.out.println("Query not performed \n" + e.toString());
		}
	}
}
