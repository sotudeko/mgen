package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class AutoReleasedFromQuarantineConfig implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(AutoReleasedFromQuarantineConfig.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making AutoReleasedFromQuarantineConfig report");

        JsonArray results = reader.readArray();

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String id = result.getString("id");
            String name = result.getString("name");
            boolean autoReleaseQuarantineEnabled = result.getBoolean("autoReleaseQuarantineEnabled");

            log.info(id + ":" + name + ":" + autoReleaseQuarantineEnabled);
        }
    }

    @Override
    public void makeCsvFile(JsonObject reader) {

    }
}
