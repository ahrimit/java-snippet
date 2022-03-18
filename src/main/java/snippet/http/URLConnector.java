package snippet.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
public class URLConnector {
	
	private static Logger logger = LogManager.getLogger();
	
	public static String connGet(String pUrl, int timeout) {
		StringBuilder sb = new StringBuilder();
		HttpURLConnection urlConn = null;
		try {
			URL url = new URL (pUrl);
			urlConn = (HttpURLConnection)url.openConnection();

			int responseCode = urlConn.getResponseCode();	
			logger.debug("resultCode : " + responseCode);
			
			BufferedReader br; 
			if(responseCode==200) { // 정상 호출
	    		br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
             } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(urlConn.getErrorStream(), "UTF-8"));
            }
	      
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			
		} catch(IOException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(urlConn != null) {
				urlConn.disconnect();
			}
		}

		return sb.toString();
	}
	
	public static String connPost(String pUrl, String params, int timeout) {
		StringBuilder sb = new StringBuilder();
		
		HttpURLConnection urlConn = null;
		try {
			URL url = new URL (pUrl);
			urlConn = (HttpURLConnection)url.openConnection();
			urlConn.setDoInput (true);
			urlConn.setDoOutput (true);
			urlConn.setUseCaches (false);
			urlConn.setDefaultUseCaches(false);
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("content-type","application/json;utf-8");
			urlConn.setRequestProperty("Accept", "application/json");
			urlConn.setConnectTimeout(timeout);
			urlConn.setReadTimeout(timeout);
			
			DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream());

			if(params != null) {
				wr.writeBytes(params);
			}
			wr.flush();
			wr.close();

			int responseCode = urlConn.getResponseCode();	
			logger.debug("resultCode : " + responseCode);
			
			BufferedReader br; 
			if(responseCode==200) { // 정상 호출
	    		br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
             } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(urlConn.getErrorStream(), "UTF-8"));
            }
			
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			
		} catch(IOException ex) {
			ex.printStackTrace();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(urlConn != null) {
				urlConn.disconnect();
			}
		}
		
		return sb.toString();
	}
	
	public static String connPost(String pUrl, Map<String, String> paramMap, int timeout) {
		StringBuilder params = new StringBuilder();
		paramMap.forEach((key, value)->{params.append(key + "=" + value);});
		
		return connPost(pUrl, params.toString(), timeout);
	}

}