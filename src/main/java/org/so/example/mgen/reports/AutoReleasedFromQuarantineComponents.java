package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class AutoReleasedFromQuarantineComponents implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(AutoReleasedFromQuarantineComponents.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
    }

    @Override
    public void makeCsvFile(JsonObject dataObject) {
        log.info("Making AutoReleasedFromQuarantineComponents report");

        JsonArray results = dataObject.getJsonArray("results");

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String displayName = result.getString("displayName");
            String repository = result.getString("repository");
            String quarantineDate = result.getString("quarantineDate");
            String dateCleared = result.getString("dateCleared");

            log.info(displayName + ":" + repository + ":" + quarantineDate);
        }
    }
}
