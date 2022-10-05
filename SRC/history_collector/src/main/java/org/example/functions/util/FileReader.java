package org.example.functions.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class FileReader {

	public static final FileReader fileReader = new FileReader();

	private FileReader() {
	}

	public static FileReader getInstance() {
		return fileReader;
	}

	/**
	 * 인자로 받은 filePath에 있는 파일을 읽어와서 String으로 반환한다.
	 * @param filePath 읽어올 파일의 Path
	 * @return String 형식의 파일
	 */
	public String readFile(String filePath) {
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
