package com.streamanalytics.parser;

import org.json.JSONObject;
import org.json.JSONException;

import com.streamanalytics.models.CurrentStats;

// Standard data parser for model binding
public class WowzaDataParser {

	public CurrentStats parseCurrentStats(JSONObject response) {
	    try {
	        // Extract properties from the JSON response
	        String serverName = response.getString("serverName");
	        long uptime = response.getLong("uptime");
	        double bytesInRate = response.getDouble("bytesInRate");
	        double bytesOutRate = response.getDouble("bytesOutRate");
	        long totalConnections = response.getLong("totalConnections");

	        // JSONObject connectionCounts = response.getJSONObject("connectionCount");
	        // long webmCount = connectionCounts.getLong("WEBM");
	        // TODO: finish this

	        return new CurrentStats(serverName, uptime, bytesInRate, bytesOutRate, totalConnections);
	    } catch (JSONException e) {
	        e.printStackTrace();
	        // TODO: Log the error.
	        return null;
	    }
	}
}