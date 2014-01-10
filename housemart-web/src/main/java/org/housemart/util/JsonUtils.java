package org.housemart.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {

	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static <T> T readValue(String content, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(content, valueType);
	}
	
	public static String writeValue(Object obj) throws JsonGenerationException, JsonMappingException, IOException{
		
		return mapper.writeValueAsString(obj);
		
	}
}
