package org.example.functions.util;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TypeConverterTest {

	TypeConverter typeConverter = TypeConverter.getTypeConverter();

	@Test
	void canConvertJSONObject() {
		Assertions.assertThat(typeConverter.canConvertJSONObject("{\"hello\":\"world\"}")).isTrue();
		Assertions.assertThat(typeConverter.canConvertJSONObject("[{\"hello\":\"world\"}]")).isFalse();
	}

	@Test
	void canConvertJSONArray() {
		Assertions.assertThat(typeConverter.canConvertJSONArray("{\"hello\":\"world\"}")).isFalse();
		Assertions.assertThat(typeConverter.canConvertJSONArray("[{\"hello\":\"world\"}]")).isTrue();
	}
}
