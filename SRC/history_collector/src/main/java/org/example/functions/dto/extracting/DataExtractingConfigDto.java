package org.example.functions.dto.extracting;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.example.functions.dto.HttpRequestDto;
import org.example.functions.extractor.datainterface.AdapterType;
import org.example.functions.util.FileReader;
import org.example.functions.util.constants.FilePathConstant;
import org.json.JSONObject;

import com.microsoft.azure.functions.HttpRequestMessage;

import lombok.Data;

@Data
public class DataExtractingConfigDto {
	private String dataName;

	private JSONObject dataLoadConfig; //mapping 객체가 들어감
	private AdapterType adapterType;
	private JSONObject dataRequestContext; //source 객체가 들어감
	private Map<String, String> requestParameter;

	private DataSourceInterfaceConfigDtoInterface dataSourceInterfaceConfigDtoInterface;
	private Map<String, List<String>> requestVariableMap;

	public static List<DataExtractingConfigDto> getDataExtractingConfigDtoList(
		HttpRequestMessage<Optional<String>> request, Map<String, List<String>> requestVariableMap) throws
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
				dataLoadConfig, request.getQueryParameters(), key, requestVariableMap);
			dataExtractingConfigDtoList.add(dataExtractingConfigDto);
		}
		return dataExtractingConfigDtoList;
	}

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

	public boolean hasRequestVariable() {
		return dataRequestContext.keySet().contains("variable_list");
	}

	public URL getUrl() {
		return dataSourceInterfaceConfigDtoInterface.getUrl();
	}

	public String getMethod() {
		return dataSourceInterfaceConfigDtoInterface.getMethod();
	}

	public JSONObject getHeader() {
		return dataSourceInterfaceConfigDtoInterface.getHeader();
	}

	public String getBody() {
		return dataSourceInterfaceConfigDtoInterface.getBody();
	}

	public HttpRequestDto getHttpRequestDto() {
		return dataSourceInterfaceConfigDtoInterface.getHttpRequestDto();
	}

	public List<HttpRequestDto> getAllHttpRequestDto() {
		return dataSourceInterfaceConfigDtoInterface.getAllHttpRequestDto();
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
			dataSourceInterfaceConfigDtoInterface = new GraphqlConfigDto(this);
		} else if (adapterType == AdapterType.RESTAPI) {
			dataSourceInterfaceConfigDtoInterface = new RestApiConfigDto(this);
		}
	}
}
