package com.spring.rest.controller.test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 
 * @author bytesTree
 * @see <a href="http://www.bytestree.com/">BytesTree</a>
 * 
 */
public class TestUtils {
	
	 public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	    private static final String CHARACTER = "a";

	@SuppressWarnings("rawtypes")
	public static List jsonToList(String json, TypeToken token) {
		Gson gson = new Gson();
		return gson.fromJson(json, token.getType());
	}

	public static String objectToJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	public static <T> T jsonToObject(String json, Class<T> classOf) {
		Gson gson = new Gson();
		return gson.fromJson(json, classOf);
	}
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }

        return builder.toString();
    }
}