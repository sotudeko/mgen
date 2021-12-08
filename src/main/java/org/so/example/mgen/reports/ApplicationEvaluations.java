package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;
import org.so.example.mgen.service.FileIoService;
import org.so.example.mgen.util.FilenameInfo;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.ArrayList;
import java.util.List;

public class ApplicationEvaluations implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(ApplicationEvaluations.class);

    public void makeCsvFile(JsonReader reader) {
        log.info("Making ApplicationEvaluations report");

        List<String[]> data = new ArrayList<>();
        data.add(FilenameInfo.applicationEvaluationsFileHeader);

        JsonArray results = reader.readArray();

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String stage = result.getString("stage");
            String evaluationDate = result.getString("evaluationDate");
            String reportDataUrl = result.getString("reportDataUrl");
            String applicationName = reportDataUrl.split("/")[3];

            String[] line = {applicationName, evaluationDate, stage};
            data.add(line);
        }

        FileIoService.writeCsvFile(FilenameInfo.applicationEvaluationsCsvFile,  data);
    }

    @Override
    public void makeCsvFile(JsonObject reader) {
    }
}
