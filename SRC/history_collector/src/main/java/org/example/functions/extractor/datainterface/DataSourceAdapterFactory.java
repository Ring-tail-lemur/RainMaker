package org.example.functions.extractor.datainterface;

import java.util.Arrays;
import java.util.List;

import org.example.functions.util.exception.DataSourceAdaptorNotFindException;

public class DataSourceAdapterFactory {

	private static List<DataSourceAdapter> adapterList = Arrays.asList(GraphqlAdapter.getInstance(), RestApiAdapter.getInstance());
	public DataSourceAdapterFactory() {
		registerAdapters();
	}

	private void registerAdapters() {
	}

	public static DataSourceAdapter getDataSourceAdapterOf(AdapterType interfaceType) throws
		DataSourceAdaptorNotFindException {
		return adapterList.stream()
			.filter(dataSourceAdapter -> dataSourceAdapter.isAdapted(interfaceType))
			.findAny()
			.orElseThrow(() -> new DataSourceAdaptorNotFindException(
				String.format("\"%s\"라는 데이터소스 어뎁터가 존제하지 않습니다.", interfaceType.getInterfaceType())));
	}
}
