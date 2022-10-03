package org.example.functions.transformer;

import java.util.Map;

import org.example.functions.dto.LoadingDataDto;
import org.example.functions.dto.SourceDataDto;
import org.example.functions.util.StringFormatter;

public class SourceDataTransformerImpl implements SourceDataTransformer {

	private static final SourceDataTransformerImpl sourceDataTransformer = new SourceDataTransformerImpl();
	private final StringFormatter queryStringFormatter = new StringFormatter();

	private SourceDataTransformerImpl() {
	}

	public static SourceDataTransformerImpl getInstance() {
		return sourceDataTransformer;
	}

	@Override
	public LoadingDataDto transformData(SourceDataDto sourceDataDto) {
		String bulkInsertQuery = generateBulkInsertQuery(sourceDataDto);
		return new LoadingDataDto(bulkInsertQuery);
	}

	/**
	 * @param tableName 생성하려는 쿼리의 테이블 이름
	 * @param sourceData 가공/변환되지 않은 원천데이터
	 * @return 타겟 테이블에 해당하는 Bulk Insert Query (String)
	 */
	private String generateBulkInsertQuery(SourceDataDto sourceData) {
		Map<String, String> parameters = getParametersMap(sourceData);
		if (sourceData.isEmpty())
			return "";
		String queryTemplate = "INSERT INTO {{tableName}} ({{columns}}) SELECT * FROM (VALUES {{queryValues}}) tempName ({{columns}}) EXCEPT SELECT {{columns}} from {{tableName}}";
		return queryStringFormatter.bindParameters(queryTemplate, parameters).replace("''", "").replace("\n", "\\n");
	}

	/**
	 * @param tableName 생성하려는 쿼리의 테이블 이름
	 * @param sourceData 가공/변환되지 않은 원천데이터
	 * @return Bulk Query에 동적으로 삽입될 문자열 Map
	 */
	private Map<String, String> getParametersMap(SourceDataDto sourceData) {
		return Map.of(
			"columns", String.join(", ", sourceData.getColumnList()),
			"queryValues", String.join(", ", sourceData.getSqlFormatEntityList()),
			"tableName", sourceData.getPureDataName());
	}
}
