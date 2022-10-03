package org.example.functions.util.constants;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.functions.dto.SourceDataDto;
import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.extractor.SourceDataExtractorImpl;
import org.example.functions.util.FileReader;
import org.example.functions.util.exception.DataSourceAdaptorNotFindException;
import org.example.functions.util.exception.ResponseTypeMissMatchException;
import org.json.JSONObject;

import com.microsoft.azure.functions.HttpRequestMessage;

public class RequestVariable {

	public static Map<String, List<String>> getRequestVariableMap(HttpRequestMessage<Optional<String>> request) throws
		IOException,
		DataSourceAdaptorNotFindException,
		ResponseTypeMissMatchException {
		Map<String, List<String>> requestVariableMap = new HashMap<>();

		FileReader fileReader = FileReader.getInstance();

		JSONObject requestVariableConfig = new JSONObject(
			fileReader.readFile(FilePathConstant.REQUEST_VARIABLE_PATH));
		Iterator<String> requestVariables = requestVariableConfig.keys();
		while (requestVariables.hasNext()) {
			String requestVariable = requestVariables.next();
			JSONObject targetVariableConfig = requestVariableConfig.getJSONObject(requestVariable);
			DataExtractingConfigDto dataExtractingConfigDto = new DataExtractingConfigDto(
				targetVariableConfig.getJSONObject("source"), targetVariableConfig.getJSONObject("mapping"),
				request.getQueryParameters(), requestVariable, new HashMap<>());
			SourceDataDto sourceDataDto = SourceDataExtractorImpl.getInstance().extractData(dataExtractingConfigDto);
			List<String> entityList = sourceDataDto.getEntityList()
				.stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
			requestVariableMap.put(requestVariable, entityList);
		}
		return requestVariableMap;
	}

}
