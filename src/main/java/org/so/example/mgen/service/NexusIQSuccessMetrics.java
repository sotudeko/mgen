package org.so.example.mgen.service;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Map;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.MgenApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.so.example.mgen.model.PayloadItem;


@Service
public class NexusIQSuccessMetrics {
	private static final Logger log = LoggerFactory.getLogger(NexusIQSuccessMetrics.class);

	@Autowired
	private FileIoService fileIoService;
	
	@Value("${iq.sm.period}")
	private String iqSmPeriod;

	@Value("${iq.url}")
	private String iqUrl;

	@Value("${iq.user}")
	private String iqUser;

	@Value("${iq.passwd}")
	private String iqPwd;

	@Value("${iq.api.payload.timeperiod.first}")
	private String iqApiFirstTimePeriod;
	
	@Value("${iq.api.payload.timeperiod.last}")
	private String iqApiLastTimePeriod;
	
	@Value("${iq.api.payload.application.name}")
	private String iqApiApplicationName;
	
	@Value("${iq.api.payload.organisation.name}")
	private String iqApiOrganisationName;

	private String iqSmEndpoint = "api/v2/reports/metrics";


	public void createSmDatafile(String iqSmPeriod) throws ClientProtocolException, IOException, JSONException, org.json.simple.parser.ParseException {
		log.info("Creating successmetrics.csv file");
		
		StringEntity apiPayload = getPayload(iqSmPeriod);
				
		String metricsUrl = iqUrl + "/" + iqSmEndpoint;
    	HttpPost request = new HttpPost(metricsUrl);

		String auth = iqUser + ":" + iqPwd;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth, StandardCharsets.ISO_8859_1);
		
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		request.addHeader("Accept", "text/csv");
		request.addHeader("Content-Type", "application/json");
        request.setEntity(apiPayload);

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);

		int statusCode = response.getStatusLine().getStatusCode();
		
		if (statusCode != 200) {
			throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	    }
	        
	    log.info("Created successmetrics.csv file");
	    
	    InputStream content = response.getEntity().getContent();
	    fileIoService.writeSuccessMetricsFile(content);
	    
	    return;
	}
	
	private StringEntity getPayload(String iqSmPeriod) throws IOException, JSONException, org.json.simple.parser.ParseException {
		log.info("Making api payload");

		PayloadItem firstTimePeriod = new PayloadItem(iqApiFirstTimePeriod, false);
		PayloadItem lastTimePeriod = new PayloadItem(iqApiLastTimePeriod, false);
		PayloadItem organisationName = new PayloadItem(iqApiOrganisationName, false);
		PayloadItem applicationName = new PayloadItem(iqApiApplicationName, false);

		if (!firstTimePeriod.isExists()) {
			throw new RuntimeException("No start period specified (iq.api.payload.timeperiod.first)");
		}

		JSONObject ajson = new JSONObject();
		ajson.put("timePeriod", iqSmPeriod.toUpperCase());
		ajson.put("firstTimePeriod", firstTimePeriod.getItem());
		
		if (lastTimePeriod.isExists()) {
			ajson.put("lastTimePeriod", lastTimePeriod.getItem());
		}
		
		// organisation takes precedence
		if (organisationName.isExists()){
			ajson.put("organizationIds", getId("organizations", organisationName.getItem()));
		}
		else if (applicationName.isExists()) {
			ajson.put("applicationIds", getId("applications", applicationName.getItem()));
		}
		
		log.info("Api Payload: " + ajson.toString());

		StringEntity params = new StringEntity(ajson.toString());

		return params;
	}
	
	private String[] getId(String endpoint, String aoName) throws ClientProtocolException, IOException, org.json.simple.parser.ParseException {
		String[] s = new String[1];

		String apiEndpoint = "/api/v2/" + endpoint;
		
		String content = getIqData(apiEndpoint);
		
		JSONObject jsonObject = new JSONObject(content);
	    
	    JSONArray jsonArray = jsonObject.getJSONArray(endpoint);
	    
	    for (int i = 0; i < jsonArray.length(); i++) {
	        JSONObject jObject = jsonArray.getJSONObject(i);
	        
	        String oName = jObject.getString("name");
	        String oId = jObject.getString("id");
	        
	        if (oName.equals(aoName)) {
	        	StringBuilder ep = new StringBuilder(endpoint);
	        	log.info("Reporting for " + ep.deleteCharAt(ep.length()-1) + ": " + aoName + " [" + oId + "]");
	        	s[0] =  oId;
	        	break;
	        }
	    }

		return s;
	}
	
	private String getIqData(String endpoint) throws ClientProtocolException, IOException {
						
		String url = iqUrl + "/" + endpoint;
    	HttpGet request = new HttpGet(url);

		String auth = iqUser + ":" + iqPwd;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth, StandardCharsets.ISO_8859_1);
		
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		request.addHeader("Content-Type", "application/json");

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);

		int statusCode = response.getStatusLine().getStatusCode();
		
		if (statusCode != 200) {
			throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	    }
	        	    
	    String jsonString = EntityUtils.toString(response.getEntity());   
	    return jsonString;
	}

}
