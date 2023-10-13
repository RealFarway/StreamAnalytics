package com.streamanalytics.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.streamanalytics.api.WowzaAPIHandler;
import com.streamanalytics.config.WowzaConfig;
import com.streamanalytics.models.CurrentStats;
import com.streamanalytics.parser.WowzaDataParser;
import com.streamanalytics.storage.DataStore;

public class DataCollectionModule {
	private final WowzaAPIHandler apiHandler;
	private final WowzaDataParser dataParser;
	private final DataStore dataStore;

	public DataCollectionModule(WowzaConfig config) {
		this.apiHandler = new WowzaAPIHandler(config);
		this.dataParser = new WowzaDataParser();
		this.dataStore = new DataStore();
	}

	public void collectDataForApplication(String appName) throws Exception {
		try {
			JSONObject response = apiHandler.getCurrentStatsForApplication(appName);
			CurrentStats stats = dataParser.parseCurrentStats(response);
			dataStore.storeCurrentStats(appName, stats);
		} catch (JSONException e) {
			e.printStackTrace();
			// TODO: Log the error.
		}
	}
}