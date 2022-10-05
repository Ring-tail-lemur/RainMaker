package org.example.functions.extractor.datainterface;

import java.io.IOException;

import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.util.exception.ResponseTypeMissMatchException;
import org.json.JSONArray;

public interface DataSourceAdapter {
	boolean isAdapted(AdapterType interfaceType);
	JSONArray send(DataExtractingConfigDto dataExtractingConfigDto) throws IOException, ResponseTypeMissMatchException;
}
