package com.streamanalytics;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.streamanalytics.config.WowzaConfig;
import com.streamanalytics.service.DataCollectionModule;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.vhost.IVHost;

public class StreamAnalyticsCollector extends ModuleBase {

	private ScheduledExecutorService scheduler;
	private int noStreamCounter = 0; // Counter for when there are no active streams

	public void onAppStart(IApplicationInstance appInstance) throws Exception {
		String fullname = appInstance.getApplication().getName() + "/" + appInstance.getName();
		getLogger().info("StreamAnalyticsCollector Module Loaded in Application: " + fullname);

		IVHost vhost = appInstance.getVHost();

		// Fetch custom properties for this application from Wowza configurations
		String baseUrl = appInstance.getProperties().getPropertyStr("baseUrl", "http://localhost:8087");
		String serverName = appInstance.getProperties().getPropertyStr("serverName", "defaultServerName");
		// Get the AnalyticsLogInterval custom property, or use default (3)
		final int AnalyticsLogInterval = appInstance.getProperties().getPropertyInt("analyticsLogInterval", 3);

		String vhostName = vhost.getName();

		// Setup Wowza configuration using fetched properties
		WowzaConfig config = new WowzaConfig(baseUrl, serverName, vhostName);

		getLogger().info("StreamAnalyticsCollector config set: baseUrl=" + baseUrl + " | serverName=" + serverName
				+ " | vhostName=" + vhostName + " | appName=" + appInstance.getApplication().getName());

		// Create a DataCollectionModule instance
		DataCollectionModule dataCollectionModule = new DataCollectionModule(config);

		// Initialize the ScheduledExecutorService
		scheduler = Executors.newSingleThreadScheduledExecutor();

		// Had to do a workaround to stop the scheduler, basically it checks if the
		// application has active streams
		// if it does, then it keeps the scheduler rolling, otherwise shuts it down.

		// Schedule the recurring task
		scheduler.scheduleWithFixedDelay(() -> {
			try {
				if (!hasActiveStreams(appInstance)) {
					noStreamCounter++;
					// If no streams for a certain number of checks, then shutdown
					if (noStreamCounter >= 5) {
						scheduler.shutdown();
					}
				} else {
					noStreamCounter = 0; // Reset counter if there are active streams
				}

				// Collect the data (and saves it)
				dataCollectionModule.collectDataForApplication(appInstance.getApplication().getName());
			} catch (Exception e) {
				getLogger().error("StreamAnalyticsCollector: Error while collecting data: " + e.getMessage());
			}
		}, 0, AnalyticsLogInterval, TimeUnit.SECONDS);
	}

	private boolean hasActiveStreams(IApplicationInstance appInstance) {
		// Check if there are any active streams for the application
		return appInstance.getStreams().getPublishStreamNames().size() > 0;
	}

	public void onAppStop(IApplicationInstance appInstance) {
		// Checks if there is still a scheduler active to shut down
		if (scheduler != null) {
			scheduler.shutdown(); // Shutdown the scheduler
			try {
				if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
					scheduler.shutdownNow(); // Force shutdown
				}
			} catch (InterruptedException e) {
				scheduler.shutdownNow();
			}
		}
	}
}