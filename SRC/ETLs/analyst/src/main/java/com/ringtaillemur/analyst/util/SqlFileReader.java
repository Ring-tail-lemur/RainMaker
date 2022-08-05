package com.ringtaillemur.analyst.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class SqlFileReader {

	private final static SqlFileReader sqlFileReader = new SqlFileReader();

	private SqlFileReader(){}

	public static SqlFileReader getSqlFileReader() {
		return sqlFileReader;
	}

	public String getSqlFromFile(String filePath) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		FileReader fileReader = new FileReader(Paths.get("").toAbsolutePath().toString()+"/"+filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String line;
		while((line = bufferedReader.readLine()) != null){
			stringBuilder.append(line).append("\n");
		}
		return stringBuilder.toString();
	}
}
