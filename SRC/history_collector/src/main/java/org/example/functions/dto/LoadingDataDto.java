package org.example.functions.dto;

import lombok.Data;

@Data
public class LoadingDataDto {
	private String query;
	private String persistenceUnitName = "azure-mssql-unit";

	public LoadingDataDto(String query) {
		this.query = query;
	}
}
