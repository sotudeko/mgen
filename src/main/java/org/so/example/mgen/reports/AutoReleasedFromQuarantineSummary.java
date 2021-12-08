package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;
import org.so.example.mgen.util.FilenameInfo;

import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.ArrayList;
import java.util.List;

public class AutoReleasedFromQuarantineSummary implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(AutoReleasedFromQuarantineSummary.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making AutoReleasedFromQuarantineSummary report");

        List<String[]> data = new ArrayList<>();
        data.add(FilenameInfo.autoReleasedFromQuarantineSummaryFileHeader);

        JsonObject dataObj = reader.readObject();

        int autoReleaseQuarantineCountMTD = dataObj.getInt("autoReleaseQuarantineCountMTD");
        int autoReleaseQuarantineCountYTD = dataObj.getInt("autoReleaseQuarantineCountYTD");

        String[] line = {String.valueOf(autoReleaseQuarantineCountMTD), String.valueOf(autoReleaseQuarantineCountYTD)};
        data.add(line);

        //FileIoService.writeCsvFile(FilenameInfo.autoReleasedFromQuarantineSummaryCsvFile,  data);
    }

    @Override
    public void makeCsvFile(JsonObject reader) {

    }

}
