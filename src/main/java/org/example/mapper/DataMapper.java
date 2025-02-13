package org.example.mapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataMapper {
	private final ObjectMapper objectMapper = new ObjectMapper();

	public void writeToJson(Object object, String filePath) throws IOException {
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), object);
	}

	public <T> T readFromJson(Class<T> valueType, String filePath) throws IOException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
			if (inputStream == null) {
				throw new IOException("파일을 찾을 수 없습니다: " + filePath);
			}
			return objectMapper.readValue(inputStream, valueType);
		}
	}
}
