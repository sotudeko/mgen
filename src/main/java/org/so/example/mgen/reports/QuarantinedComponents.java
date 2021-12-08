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

public class QuarantinedComponents implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(QuarantinedComponents.class);

    @Override
    public void makeCsvFile(FileIoService f, JsonReader reader) {

    }

    @Override
    public void makeCsvFile(FileIoService f, JsonObject dataObject) {
        log.info("Making QuarantinedComponents report");

        List<String[]> data = new ArrayList<>();
        data.add(FilenameInfo.quarantinedComponentsFileHeader);

        JsonArray results = dataObject.getJsonArray("results");

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String displayName = result.getString("displayName");
            String repository = result.getString("repository");
            String quarantineDate = result.getString("quarantineDate");

            String[] line = {displayName, repository, quarantineDate};
            data.add(line);
        }

        f.writeCsvFile(FilenameInfo.quarantinedComponentsCsvFile,  data);

    }
}
