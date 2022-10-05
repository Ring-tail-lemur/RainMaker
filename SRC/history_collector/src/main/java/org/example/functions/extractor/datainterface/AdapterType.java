package org.example.functions.extractor.datainterface;

import java.util.Arrays;

public enum AdapterType {
	GRAPHQL("graphql"), RESTAPI("restapi");

	private final String interfaceType;

	AdapterType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public static AdapterType determine(String interfaceType) {
		return Arrays.stream(AdapterType.values())
			.filter(adapterType -> adapterType.getInterfaceType().equals(interfaceType))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(
				String.format("\"%s\"에 해당하는 adapter Type은 존재하지 않습니다.", interfaceType)));
	}
}
