package com.streamanalytics.config;

public class WowzaConfig {
    
    private final String baseUrl;
    private final String serverName;
    private final String vhostName;
    
    private String appName;
    private String instanceName;
    private String streamName;

//    private String apiUsername; // for API authentication if required
//    private String apiPassword; // for API authentication if required

   
    // Constructor with the mandatory fields
    public WowzaConfig(String baseUrl, String serverName, String vhostName) {
        this.baseUrl = baseUrl;
        this.serverName = serverName;
        this.vhostName = vhostName;
    }

    // Getters

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public String getVhostName() {
        return vhostName;
    }

    public String getAppName() {
        return appName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public String getStreamName() {
        return streamName;
    }

    // Setters

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    @Override
    public String toString() {
        return "WowzaConfig{" +
                "baseUrl='" + baseUrl + '\'' +
                ", serverName='" + serverName + '\'' +
                ", vhostName='" + vhostName + '\'' +
                ", appName='" + appName + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", streamName='" + streamName + '\'' +
                '}';
    }
}