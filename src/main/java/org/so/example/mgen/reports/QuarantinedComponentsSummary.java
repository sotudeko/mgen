package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;
import org.so.example.mgen.util.FilenameInfo;

import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.ArrayList;
import java.util.List;

public class QuarantinedComponentsSummary implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(QuarantinedComponentsSummary.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making QuarantinedComponentsSummary report");

        List<String[]> data = new ArrayList<>();
        data.add(FilenameInfo.quarantinedComponentsFileHeader);

        JsonObject dataObj = reader.readObject();

        int repositoryCount = dataObj.getInt("repositoryCount");
        int quarantineEnabledRepositoryCount = dataObj.getInt("quarantineEnabledRepositoryCount");
        boolean quarantineEnabled = dataObj.getBoolean("quarantineEnabled");
        int totalComponentCount = dataObj.getInt("totalComponentCount");
        int quarantinedComponentCount = dataObj.getInt("quarantinedComponentCount");

        String[] line = {
                String.valueOf(repositoryCount),
                String.valueOf(quarantineEnabledRepositoryCount),
                String.valueOf(quarantineEnabled),
                String.valueOf(totalComponentCount),
                String.valueOf(quarantinedComponentCount)
        };

        data.add(line);

        //FileIoService.writeCsvFile(FilenameInfo.quarantinedComponentsCsvFile,  data);
    }

    @Override
    public void makeCsvFile(JsonObject reader) {

    }
}
