package org.example.functions.extractor;

import java.io.IOException;

import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.dto.SourceDataDto;
import org.example.functions.util.exception.DataSourceAdaptorNotFindException;
import org.example.functions.util.exception.ResponseTypeMissMatchException;

public interface SourceDataExtractor {
	SourceDataDto extractData(DataExtractingConfigDto dataExtractingConfigDto) throws
		DataSourceAdaptorNotFindException,
		IOException,
		ResponseTypeMissMatchException;
}
