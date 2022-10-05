package org.example.functions.extractor;

import java.io.IOException;

import org.example.functions.dto.SourceDataDto;
import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.extractor.datainterface.DataSourceAdapterFactory;
import org.example.functions.util.exception.DataSourceAdaptorNotFindException;
import org.example.functions.util.exception.ResponseTypeMissMatchException;
import org.json.JSONArray;

public class SourceDataExtractorImpl implements SourceDataExtractor {

	private static SourceDataExtractorImpl sourceDataExtractor = new SourceDataExtractorImpl();

	private SourceDataExtractorImpl() {
	}

	public static SourceDataExtractorImpl getInstance() {
		return sourceDataExtractor;
	}

	@Override
	public SourceDataDto extractData(DataExtractingConfigDto dataExtractingConfigDto) throws
		DataSourceAdaptorNotFindException, IOException, ResponseTypeMissMatchException {
		JSONArray responseArray = DataSourceAdapterFactory
			.getDataSourceAdapterOf(dataExtractingConfigDto.getAdapterType())
			.send(dataExtractingConfigDto);
		return new SourceDataDto(responseArray, dataExtractingConfigDto);
	}
}
