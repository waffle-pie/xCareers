package org.example.setting;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface SettingCollection<T> {
	T init(ObjectMapper objectMapper) throws IOException;
}
