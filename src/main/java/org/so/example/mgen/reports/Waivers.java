package org.so.example.mgen.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.CsvFileService;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Waivers implements CsvFileService  {
    private static final Logger log = LoggerFactory.getLogger(Waivers.class);

    @Override
    public void makeCsvFile(JsonReader reader) {
        log.info("Making Waivers report");

        JsonObject obj = reader.readObject();

        JsonArray aw = obj.getJsonArray("applicationWaivers");
        this.doWaivers("application", aw);

        JsonArray rw = obj.getJsonArray("repositoryWaivers");
        this.doWaivers("repository", rw);

    }

    public void doWaivers(String waiverType, JsonArray componentWaivers){

        for (JsonObject result : componentWaivers.getValuesAs(JsonObject.class)) {

            JsonObject waivers = result.getJsonObject(waiverType);
            String applicationName = waivers.getString("publicId");

            JsonArray stages = result.getJsonArray("stages");

            for (JsonObject stage : stages.getValuesAs(JsonObject.class)){
                String stageId = stage.getString("stageId");

                JsonArray componentViolations = stage.getJsonArray("componentPolicyViolations");

                for (JsonObject componentViolation : componentViolations.getValuesAs(JsonObject.class)){
                    JsonObject component = componentViolation.getJsonObject("component");
                    String packageUrl = component.getString("packageUrl");

                    JsonArray waivedPolicyViolations = componentViolation.getJsonArray("waivedPolicyViolations");

                    for (JsonObject waivedPolicyViolation : waivedPolicyViolations.getValuesAs(JsonObject.class)){
                        JsonObject policyWaiver = waivedPolicyViolation.getJsonObject("policyWaiver");
                        log.info((policyWaiver.toString()));

                        String policyName = waivedPolicyViolation.getString("policyName");
                        int threatLevel = waivedPolicyViolation.getInt("threatLevel");

//                        if ((policyWaiver.containsKey("comment")) && (policyWaiver.getString("comment") != null)) {
//                            String comment = policyWaiver.getString("comment");
//                        }

//                        if (waivedPolicyViolation.containsKey("createTime")) {
//                            String createTime = policyWaiver.getString("createTime");
//                        }
//
//                        if (waivedPolicyViolation.containsKey("expiryTime")) {
//                            String expiryTime = policyWaiver.getString("expiryTime");
//                        }

                        //log.info(applicationName + ":" + stageId + ":" + packageUrl + ":" + policyName + ":" + threatLevel);
                    }
                }

            }

        }
    }
}
