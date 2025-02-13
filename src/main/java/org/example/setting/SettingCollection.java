package org.example.setting;

import java.io.IOException;

import org.example.mapper.DataMapper;

public interface SettingCollection<T> {
	T init(DataMapper mapper) throws IOException;
}
