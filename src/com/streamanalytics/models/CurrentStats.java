package com.streamanalytics.models;

// Model class for the API response
public class CurrentStats {
    private final String serverName;
    private final long uptime;
    private final double bytesInRate;
    private final double bytesOutRate;
    private final long totalConnections;
    // TODO: private final Map<String, Long> connectionCount;

    // Constructor
    public CurrentStats(String serverName, long uptime, double bytesInRate, double bytesOutRate, 
                        long totalConnections) {
        this.serverName = serverName;
        this.uptime = uptime;
        this.bytesInRate = bytesInRate;
        this.bytesOutRate = bytesOutRate;
        this.totalConnections = totalConnections;
    }

    // Getters
    public String getServerName() {
        return serverName;
    }

    public long getUptime() {
        return uptime;
    }

    public double getBytesInRate() {
        return bytesInRate;
    }

    public double getBytesOutRate() {
        return bytesOutRate;
    }

    public long getTotalConnections() {
        return totalConnections;
    }
    
    @Override
    public String toString() {
        return "ServerName: " + serverName + 
               ", Uptime: " + uptime + 
               ", BytesInRate: " + bytesInRate + 
               ", BytesOutRate: " + bytesOutRate + 
               ", TotalConnections: " + totalConnections;
    }
    
    public String toFormattedString() {
        return String.format("Server: %s, Uptime: %d, Bytes In Rate: %.2f, Bytes Out Rate: %.2f, Total Connections: %d",
                             serverName, uptime, bytesInRate, bytesOutRate, totalConnections);
    }
    
}