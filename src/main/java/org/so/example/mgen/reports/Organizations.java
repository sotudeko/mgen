package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Organizations implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(Organizations.class);

    @Override
    public void makeCsvFile(JsonReader reader) {

        JsonObject obj = reader.readObject();
        JsonArray results = obj.getJsonArray("organizations");

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String id = result.getString("id");
            String oname = result.getString("name");
            log.info(id + " -> " + oname);
        }
    }
}
