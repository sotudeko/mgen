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

public class Organizations implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(Organizations.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making Organizations report");

        List<String[]> data = new ArrayList<>();
        data.add(FilenameInfo.organizationsFileHeader);

        JsonObject obj = reader.readObject();
        JsonArray results = obj.getJsonArray("organizations");

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String id = result.getString("id");
            String oname = result.getString("name");

            String[] line = {id, oname};
            data.add(line);
        }

        FileIoService.writeCsvFile(FilenameInfo.organizationsCsvFile,  data);
    }

    @Override
    public void makeCsvFile(JsonObject reader) {
    }
}
