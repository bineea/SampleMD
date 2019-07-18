package my.sample.common.entity;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonFilter;

import my.sample.common.tools.JsonTools;

@JsonFilter(value = JsonTools.FILTER_NAME)
public abstract class BaseEntity {

	public final String toJson() {
		return toJson(false);
	}
	
	public final String toJson(boolean format) {
		
		try {
			String jsonStr = JsonTools.entityToJson(this);
			return format ? JsonTools.formatJson(jsonStr) : jsonStr;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
