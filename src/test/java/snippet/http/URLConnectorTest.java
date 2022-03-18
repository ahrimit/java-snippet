package snippet.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class URLConnectorTest {
	
	private Logger logger = LogManager.getLogger();

	@Test 
	@Ignore 
	public void connGet() throws JsonMappingException, JsonProcessingException {
		String jsonStr = URLConnector.connGet("http://127.0.0.1:5000/member/user1", 5000);
		logger.debug(jsonStr);
		
		ObjectMapper mapper = new ObjectMapper(); 
		Map<String, String> map = mapper.readValue(jsonStr, new TypeReference<HashMap<String,String>>() {});
		logger.debug(map.get("name"));
		assertEquals(map.get("name"), "kate");

	}  
	
	@Test 
	public void connPost() throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "user1");
		paramMap.put("authKey", "authKey");
		
		String jsonStr = URLConnector.connPost("http://127.0.0.1:5000/auth/member", paramMap,  5000);
		ObjectMapper mapper = new ObjectMapper(); 
		Map<String, String> map = mapper.readValue(jsonStr, new TypeReference<HashMap<String,String>>() {});
		
		logger.debug(map.get("name"));
		assertEquals(map.get("name"), "kate");
	} 
	
	
}

