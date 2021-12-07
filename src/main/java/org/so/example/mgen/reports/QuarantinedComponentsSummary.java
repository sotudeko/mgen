package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;

import javax.json.JsonObject;
import javax.json.JsonReader;

public class QuarantinedComponentsSummary implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(QuarantinedComponentsSummary.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making QuarantinedComponentsSummary report");

        JsonObject dataObj = reader.readObject();
        log.info(dataObj.toString());

        int repositoryCount = dataObj.getInt("repositoryCount");
        int quarantineEnabledRepositoryCount = dataObj.getInt("quarantineEnabledRepositoryCount");
        boolean quarantineEnabled = dataObj.getBoolean("quarantineEnabled");
        int totalComponentCount = dataObj.getInt("totalComponentCount");
        int quarantinedComponentCount = dataObj.getInt("quarantinedComponentCount");

        log.info(repositoryCount + ":" + quarantineEnabledRepositoryCount + ":" + quarantineEnabled + ":" + totalComponentCount + ":" + quarantinedComponentCount);
    }
}
