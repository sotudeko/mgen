package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;

import javax.json.JsonObject;
import javax.json.JsonReader;

public class AutoReleasedFromQuarantineSummary implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(AutoReleasedFromQuarantineSummary.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making AutoReleasedFromQuarantineSummary report");

        JsonObject dataObj = reader.readObject();
        log.info(dataObj.toString());

        int autoReleaseQuarantineCountMTD = dataObj.getInt("autoReleaseQuarantineCountMTD");
        int autoReleaseQuarantineCountYTD = dataObj.getInt("autoReleaseQuarantineCountYTD");

        log.info(autoReleaseQuarantineCountMTD + ":" + autoReleaseQuarantineCountYTD);
    }

    @Override
    public void makeCsvFile(JsonObject reader) {

    }

}
