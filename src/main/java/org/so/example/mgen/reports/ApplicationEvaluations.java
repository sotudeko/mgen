package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ApplicationEvaluations implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(ApplicationEvaluations.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making Application Evaluations report");

        JsonArray results = reader.readArray();

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String stage = result.getString("stage");
            String evaluationDate = result.getString("evaluationDate");
            String reportDataUrl = result.getString("reportDataUrl");
            String applicationName = reportDataUrl.split("/")[3];

            log.info(stage + ":" + applicationName + ":" + evaluationDate);
        }

    }

    @Override
    public void makeCsvFile(JsonObject reader) {

    }
}
