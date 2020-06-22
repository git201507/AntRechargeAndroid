package com.ant.recharge.common.json;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 *
 */
public class JacksonConverter implements Converter  {
	private static final String MIME_TYPE = "application/json; charset=UTF-8";

	private final ObjectMapper objectMapper;

	public JacksonConverter() {
		this(new ObjectMapper());

	}

	public JacksonConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public Object fromBody(TypedInput body, Type type)
			throws ConversionException {
		try {
			JavaType javaType = objectMapper.getTypeFactory().constructType(
					type);
			return objectMapper.readValue(body.in(), javaType);
		} catch (JsonParseException e) {
			throw new ConversionException(e);
		} catch (JsonMappingException e) {
			throw new ConversionException(e);
		} catch (IOException e) {
			throw new ConversionException(e);
		}
	}

	@Override
	public TypedOutput toBody(Object object) {
		try {
			String json = objectMapper.writeValueAsString(object);
			return new TypedByteArray(MIME_TYPE, json.getBytes("UTF-8"));
		} catch (JsonProcessingException e) {
			throw new AssertionError(e);
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}
}
