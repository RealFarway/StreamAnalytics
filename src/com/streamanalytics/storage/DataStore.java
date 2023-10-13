package com.streamanalytics.storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.streamanalytics.models.CurrentStats;

public class DataStore {
	private final Map<String, CurrentStats> currentStatsMap = new HashMap<>();

    public void storeCurrentStats(String appName, CurrentStats stats) {
        currentStatsMap.put(appName, stats);
        saveDataToFile(appName);
        //saveDataToDB();
    }

    // Creates a file to store the data, a file is created every day for a better readability
    private void saveDataToFile(String appName) {
        // Determine the path to the current directory
        String currentDir = System.getProperty("user.dir");
        Path basePath = Paths.get(currentDir).getParent();

        // Construct path (Default is: "Wowza Streaming Engine X.X.X\applications\%AppName%\analytics")
        Path logDir = basePath.resolve("applications").resolve(appName).resolve("analytics");

        // Create directories if they don't exist
        if (!Files.exists(logDir)) {
            try {
                Files.createDirectories(logDir);
            } catch (IOException e) {
                e.printStackTrace(); // TODO: Handle the exception
                return;
            }
        }

        // Construct a filename based on the current date
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fileName = dateStr + ".txt";
        Path logFile = logDir.resolve(fileName);

        // Write to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile.toFile(), true))) {
            for (Map.Entry<String, CurrentStats> entry : currentStatsMap.entrySet()) {
                String applicationName = entry.getKey();
                CurrentStats stats = entry.getValue();
                
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                writer.write("[" + timestamp + "] App: " + applicationName + ", " + stats.toFormattedString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();  // TODO: Handle the exception
        }
    }
    
    private void saveDataToDB() {
    	// TODO: finish this method
    	// try-catch
    	// Establish a connection
    	// Create a SQL insert statement
    	// Set parameters and execute
    }
}