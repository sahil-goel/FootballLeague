package com.sapient.football.league.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

public class Json {
	private final static ObjectMapper mapper = objectMapper();

	private static ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		return objectMapper;
	}
	private final static TypeReference<List<Map<String, Object>>> MAP_TYPE_REFERENCE = new TypeReference<List<Map<String, Object>>>() {
	};
	
	@SuppressWarnings("deprecation")
	public static List<Map<String, Object>> parse(String src) {
		try {
			return mapper.readValue(src, MAP_TYPE_REFERENCE);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static <K,V> Map<K,V> toMap(String src, Class<K> clazz, Class<V> valueClass) {
		TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>() {
		};
		try {
			return parse(src, typeReference);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static <T> T parse(String src, TypeReference<T> typeReference) {
		try {
			return mapper.readValue(src, typeReference);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
}
