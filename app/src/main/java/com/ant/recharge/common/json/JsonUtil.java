package com.ant.recharge.common.json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;

@SuppressWarnings("deprecation")
public class JsonUtil {

	public static ObjectMapper om = new ObjectMapper();

	public static String encode(Object value) throws Exception {
		try {
			return om.writeValueAsString(value);
		} catch (Exception e) {
			throw e;
		}
	}

	public static <T> T decode(String r, Class<T> valueType) throws Exception {
		try {
			return om.readValue(r, valueType);
		} catch (Exception e) {
			throw e;
		}
	}

	public static <T> T decode(JsonNode j, Class<T> valueType) throws Exception {
		try {
			return om.readValue(j, valueType);
		} catch (Exception e) {
			throw e;
		}
	}

	public static <T> T decode(JsonNode j, TypeReference<T> valueTypeRef)
			throws Exception {
		try {
			return om.readValue(j, valueTypeRef);
		} catch (Exception e) {
			throw e;
		}
	}

	public static <T> T decode(String r, TypeReference<T> valueTypeRef)
			throws Exception {
		try {
			return om.readValue(r, valueTypeRef);
		} catch (Exception e) {
			throw e;
		}

	}

	static {
		try {
			om.configure(
					DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);

			om.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES,
					false);
		} catch (Exception e) {
		}
	}

}
