package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;
import org.so.example.mgen.util.FilenameInfo;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.ArrayList;
import java.util.List;

public class AutoReleasedFromQuarantineConfig implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(AutoReleasedFromQuarantineConfig.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making AutoReleasedFromQuarantineConfig report");

        List<String[]> data = new ArrayList<>();
        data.add(FilenameInfo.autoReleasedFromQuarantineConfigFileHeader);

        JsonArray results = reader.readArray();

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String id = result.getString("id");
            String name = result.getString("name");
            boolean autoReleaseQuarantineEnabled = result.getBoolean("autoReleaseQuarantineEnabled");

            String[] line = {id, name, String.valueOf(autoReleaseQuarantineEnabled)};
            data.add(line);
        }

        //FileIoService.writeCsvFile(FilenameInfo.autoReleasedFromQuarantineConfigCsvFile,  data);
    }

    @Override
    public void makeCsvFile(JsonObject reader) {

    }
}
