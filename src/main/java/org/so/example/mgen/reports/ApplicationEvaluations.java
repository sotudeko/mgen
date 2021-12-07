package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;
import org.so.example.mgen.service.FileIoService;
import org.so.example.mgen.util.FilenameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationEvaluations  implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(ApplicationEvaluations.class);

    @Autowired
    private FileIoService fileIoService;

    public void makeCsvFile(JsonReader reader) {
        log.info("Making Application Evaluations report");

        List<String[]> data = new ArrayList<>();
        data.add(FilenameInfo.applicationEvaluationsFileHeader);

        JsonArray results = reader.readArray();

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String stage = result.getString("stage");
            String evaluationDate = result.getString("evaluationDate");
            String reportDataUrl = result.getString("reportDataUrl");
            String applicationName = reportDataUrl.split("/")[3];

            log.info(stage + ":" + applicationName + ":" + evaluationDate);

            String[] line = {applicationName, evaluationDate, stage};
            data.add(line);
        }
    }

    @Override
    public void makeCsvFile(JsonObject reader) {
    }
}
