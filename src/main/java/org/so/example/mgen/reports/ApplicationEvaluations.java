package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.MapToCsv;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ApplicationEvaluations implements MapToCsv {
    private static final Logger log = LoggerFactory.getLogger(ApplicationEvaluations.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making Application Evaluations report");

        JsonArray results = reader.readArray();

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {

            String stage = result.getString("stage");
            String applicationId = result.getString("applicationId");
            String evaluationDate = result.getString("evaluationDate");

            log.info(stage + ":" + applicationId + ":" + evaluationDate);
        }

    }
}
