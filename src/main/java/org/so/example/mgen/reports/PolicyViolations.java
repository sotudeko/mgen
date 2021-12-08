package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;
import org.so.example.mgen.service.FileIoService;
import org.so.example.mgen.service.PolicyIdsService;
import org.so.example.mgen.util.FilenameInfo;
import org.so.example.mgen.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.ArrayList;
import java.util.List;

public class PolicyViolations implements CsvFileService {
    private static final Logger log = LoggerFactory.getLogger(PolicyViolations.class);

    @Autowired
    PolicyIdsService  policyIdsService;

    @Override
    public void makeCsvFile(FileIoService f, JsonReader reader) {
        log.info("Make Policy Violations Report");

        List<String[]> data = new ArrayList<>();
        data.add(FilenameInfo.policyViolationsFileHeader);

        JsonObject obj = reader.readObject();
        JsonArray results = obj.getJsonArray("applicationViolations");

        for (JsonObject result : results.getValuesAs(JsonObject.class)) {

            JsonObject application = result.getJsonObject("application");
            JsonArray policyViolations = result.getJsonArray("policyViolations");

            String applicationPublicId = application.getString("publicId");

            for (JsonObject policyViolation : policyViolations.getValuesAs(JsonObject.class)) {
                String stage = policyViolation.getString("stageId");
                String openTime = policyViolation.getString("openTime");
                String policyName = policyViolation.getString("policyName");
                int threatLevel = policyViolation.getInt("threatLevel");

                JsonObject component = policyViolation.getJsonObject("component");
                String packageUrl = (component.get("packageUrl") != null) ? String.valueOf(component.get("packageUrl")) : "";

                JsonArray constraintViolations = policyViolation.getJsonArray("constraintViolations");
                for (JsonObject constraintViolation : constraintViolations.getValuesAs(JsonObject.class)) {
                    JsonArray reasons = constraintViolation.getJsonArray("reasons");

                    String reason = "";

                    if (policyName.equalsIgnoreCase("Integrity-Rating")){
                        reason = "Integrity-Rating";
                    }
                    else if (PolicyIdsService.isSecurityPolicy(policyName)){
                        reason = getCVE(reasons);
                    }
                    else if (PolicyIdsService.isLicensePolicy(policyName)){
                        reason = getLicense(reasons);
                    }

                    String[] line = {policyName, reason, applicationPublicId, openTime, packageUrl, stage};
                    data.add(line);
                }
            }
        }

        f.writeCsvFile(FilenameInfo.policyViolationsCsvFile,  data);
    }

    private String getCVE(JsonArray reasons) {
        String cveList = "";
        List<String> cves = new ArrayList<>();

        for (JsonObject reason : reasons.getValuesAs(JsonObject.class)) {
            JsonObject reference = reason.getJsonObject("reference");
            String cve  = reference.getString("value");

            if (!cves.contains(cve)){
                cves.add(cve);
            }
        }

        for (String c : cves){
            cveList = cveList + ":";
        }

        cveList = UtilService.removeLastChar(cveList);

        return cveList;
    }

    private String getLicense(JsonArray reasons) {
        String licenseList = "";
        List<String> licenses = new ArrayList<>();

        for (JsonObject reason : reasons.getValuesAs(JsonObject.class)) {
            String  licenseFound = reason.getString("reason");

            String license = licenseFound.substring(licenseFound.indexOf("(")+1, licenseFound.indexOf(")"));
            license = license.replaceAll("'", "");

            if (!licenses.contains(license)){
                licenses.add(license);
            }
        }

        for (String l : licenses){
            licenseList = licenseList + ":";
        }

        licenseList = UtilService.removeLastChar(licenseList);

        return licenseList;
    }

    @Override
    public void makeCsvFile(FileIoService f, JsonObject reader) {

    }
}
