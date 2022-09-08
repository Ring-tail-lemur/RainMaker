package org.example.functions.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.example.functions.util.constants.FilePathConstant;
import org.json.JSONObject;

public class ConfigReader {

	TypeConverter typeConverter = TypeConverter.getTypeConverter();

	public JSONObject getJsonObjectConfig() {
		return typeConverter.convertStringToJSONObject(getStringConfig());
	}

	public String getStringConfig() {
		return readFile(FilePathConstant.CONFIG_FILE_PATH);
	}

	private String readFile(String filePath) {
		InputStream input = this.getClass().getResourceAsStream(filePath);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while (true) {
			try {
				if ((length = input.read(buffer)) == -1)
					break;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			result.write(buffer, 0, length);
		}
		try {
			return result.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
