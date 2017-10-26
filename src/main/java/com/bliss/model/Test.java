package com.bliss.model;


import java.util.Date;

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
			boolean statut = true;
			float ram = Traitement.ram();
			float disque = Traitement.disque();
			String message ="OK";
			statut = Traitement.error(ram, "ram");
			Date d = new Date();
			long time = d.getTime()/1000;
			if(statut) {
				statut = Traitement.error(disque, "disque");
				if(!statut) {
					message="DISQUE KO";
				}
			}
			else {
				message = "RAM KO";
			}
			
		    JSONObject json = new JSONObject();
		    //sjson.put("idMachine", 1);
		    json.put("idSalle", 1);
		    json.put("nomMachine", "pc1");
		    json.put("urlMachine", url);
		    json.put("etat", statut);
		    json.put("ram", ram);
		    json.put("message",message );
		    json.put("dateDernierRecut", time);
		    json.put("disque", disque);
		    String value=json.toString();
		    StringEntity entity = new StringEntity(value);
		    HttpPost request = new HttpPost("http://localhost:8080/MonitoringToolsBO/check");
		    request.addHeader("content-type", "application/json");
		    request.setEntity(entity);
		    HttpResponse response = httpClient.execute(request);

		    //handle response here...

		}catch (Exception ex) {
ex.printStackTrace();
		    //handle exception here

		} finally {
		    //Deprecated
		    //httpClient.getConnectionManager().shutdown(); 
		}
	}

}
