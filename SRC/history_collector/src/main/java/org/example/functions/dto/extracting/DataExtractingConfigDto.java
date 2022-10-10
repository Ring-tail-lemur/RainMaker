package org.example.functions.dto.extracting;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.example.functions.dto.HttpRequestDto;
import org.example.functions.extractor.datainterface.AdapterType;
import org.example.functions.util.FileReader;
import org.example.functions.util.constants.FilePathConstant;
import org.json.JSONObject;

import lombok.Data;

@Data
public class DataExtractingConfigDto {
	private String dataName;

	private JSONObject dataLoadConfig; //mapping 객체가 들어감
	private AdapterType adapterType;
	private JSONObject dataRequestContext; //graphql 객체 또는 restapi 객체가 들어감
	private Map<String, String> requestParameter;

	private DataSourceInterfaceConfigDto dataSourceInterfaceConfigDto;
	private Map<String, List<String>> requestVariableMap;

	public DataExtractingConfigDto(JSONObject dataSourceContext, JSONObject dataLoadConfig,
		Map<String, String> requestParameter, String dataName, Map<String, List<String>> requestVariableMap) throws
		MalformedURLException {
		adapterType = AdapterType.determine(dataSourceContext.getString("interface_type"));
		dataRequestContext = dataSourceContext.getJSONObject(adapterType.getInterfaceType());
		this.dataLoadConfig = dataLoadConfig;
		this.requestParameter = requestParameter;
		this.dataName = dataName;
		this.requestVariableMap = requestVariableMap;
		setDataSourceInterface();
	}

	public Map<String, String> getPaginationInfo() {
		return dataSourceInterfaceConfigDto.getPaginationInfo();
	}

	public static List<DataExtractingConfigDto> getDataExtractingConfigDtoList(
		Map<String, String> requestParameterMap, Map<String, List<String>> requestVariableMap) throws
		MalformedURLException {
		FileReader fileReader = FileReader.getInstance();
		JSONObject configFile = new JSONObject(fileReader.readFile(FilePathConstant.CONFIG_FILE_PATH));
		List<DataExtractingConfigDto> dataExtractingConfigDtoList = new ArrayList<>();

		Iterator<String> keys = configFile.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			JSONObject dataSourceContext = configFile.getJSONObject(key).getJSONObject("source");
			JSONObject dataLoadConfig = configFile.getJSONObject(key).getJSONObject("mapping");
			DataExtractingConfigDto dataExtractingConfigDto = new DataExtractingConfigDto(dataSourceContext,
				dataLoadConfig, requestParameterMap, key, requestVariableMap);
			dataExtractingConfigDtoList.add(dataExtractingConfigDto);
		}
		return dataExtractingConfigDtoList;
	}

	public boolean hasRequestVariable() {
		return dataRequestContext.keySet().contains("variable_list");
	}

	public URL getUrl() {
		return dataSourceInterfaceConfigDto.getUrl();
	}

	public String getMethod() {
		return dataSourceInterfaceConfigDto.getMethod();
	}

	public JSONObject getHeader() {
		return dataSourceInterfaceConfigDto.getHeader();
	}

	public boolean hasPagination() {
		return dataRequestContext.keySet().contains("pagination");
	}

	public String getBody() {
		return dataSourceInterfaceConfigDto.getBody();
	}

	public HttpRequestDto getHttpRequestDto() {
		return dataSourceInterfaceConfigDto.getHttpRequestDto();
	}

	public List<HttpRequestDto> getAllHttpRequestDto() {
		return dataSourceInterfaceConfigDto.getAllHttpRequestDto();
	}

	protected String getUsingRequestVariable() {
		return dataRequestContext.getString("variable_list");
	}

	protected DataExtractingConfigDto(DataExtractingConfigDto dataExtractingConfigDto) {
		adapterType = dataExtractingConfigDto.getAdapterType();
		dataRequestContext = dataExtractingConfigDto.getDataRequestContext();
		requestParameter = dataExtractingConfigDto.getRequestParameter();
		requestVariableMap = dataExtractingConfigDto.getRequestVariableMap();
	}

	private void setDataSourceInterface() throws MalformedURLException {
		if (adapterType == AdapterType.GRAPHQL) {
			dataSourceInterfaceConfigDto = new GraphqlConfigDtoConfigDto(this);
		} else if (adapterType == AdapterType.RESTAPI) {
			dataSourceInterfaceConfigDto = new RestApiConfigDtoConfigDto(this);
		}
	}
}
