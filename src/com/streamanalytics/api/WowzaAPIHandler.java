package com.streamanalytics.api;

import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import com.streamanalytics.config.WowzaConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WowzaAPIHandler {

    private final WowzaConfig config;

    public WowzaAPIHandler(WowzaConfig config) {
        this.config = config;
    }

    public JSONObject getCurrentStatsForApplication(String appName) throws Exception {
    	// Construct the API endpoint URL
        String endpoint = String.format("%s/v2/servers/%s/vhosts/%s/applications/%s/monitoring/current",
                config.getBaseUrl(), config.getServerName(), config.getVhostName(), appName);
        URL url = new URL(endpoint);
        
        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json; charset=utf-8");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            
            // Check the HTTP response code
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // Read the response
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String output;
            StringBuilder response = new StringBuilder();

            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            // Convert the response string to JSONObject
            return new JSONObject(response.toString());

        } finally {
            connection.disconnect();
        } 
    }
}