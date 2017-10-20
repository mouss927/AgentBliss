package com.bliss.model;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class Test {
	
	public Test () {}
	
	public void EnvoyeInfo()
	{
		
		
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

		try {
			String url=java.net.InetAddress.getLocalHost().getHostAddress();
		    
		    JSONObject json = new JSONObject();
		    json.put("idMachine", 1);
		    json.put("idSalle", 1);
		    json.put("nomMachine", "pc1");
		    json.put("UrlMachine", url);
		    json.put("etat", true);
		    json.put("ram", "ram dispo 4go");
		    String value=json.toString();
		    StringEntity entity = new StringEntity(value);
		    HttpPost request = new HttpPost("http://http://localhost:8080/MonitoringToolsBO/check");
		    request.addHeader("content-type", "application/x-www-form-urlencoded");
		    request.setEntity(entity);
		    HttpResponse response = httpClient.execute(request);

		    //handle response here...

		}catch (Exception ex) {

		    //handle exception here

		} finally {
		    //Deprecated
		    //httpClient.getConnectionManager().shutdown(); 
		}
	}

}
