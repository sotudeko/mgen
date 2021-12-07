package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;

import javax.json.JsonObject;
import javax.json.JsonReader;

public class PolicyViolations implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(PolicyViolations.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Make Policy Violations Report");
    }

    @Override
    public void makeCsvFile(JsonObject reader) {

    }
}
