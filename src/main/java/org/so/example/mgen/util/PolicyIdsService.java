package org.so.example.mgen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.service.NexusIQApiDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;


@Service
public class PolicyIdsService {
    private static final Logger log = LoggerFactory.getLogger(PolicyIdsService.class);

    @Autowired
	private NexusIQApiDataService nexusIQDataService;

	@Autowired
	private UtilService utilService;
    

    public String getPolicyIdsEndpoint() throws IOException{
        JsonObject dataObj = nexusIQDataService.getData("/policies");
        String policyIds = getPolicyIds(dataObj);
        String policyViolationsEndpoint = "/policyViolations?" + policyIds;
        return policyViolationsEndpoint;
    }

    private String getPolicyIds(JsonObject obj) {
		log.info("Getting policy ids");

		JsonArray results = obj.getJsonArray("policies");

		String policyIds = "";

		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
			String id = result.getString("id");
			String pname = result.getString("name");

			policyIds = policyIds.concat("p=" + id + "&");
		}

		policyIds = utilService.removeLastChar(policyIds);
		return policyIds;
	}
    
}
