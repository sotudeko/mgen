package org.so.example.mgen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
public class PolicyIdsService {
    private static final Logger log = LoggerFactory.getLogger(PolicyIdsService.class);

    @Autowired
	private NexusIQApiDataService nexusIQDataService;

	@Autowired
	private UtilService utilService;

	private static List<String> securityPolicies = Arrays.asList("Security-Critical",
																 "Security-High",
																 "Security-Medium",
																 "Security-Malicious",
																 "Security-Namespace Conflict",
																 "Integrity-Rating");

	private static List<String> licensePolicies = Arrays.asList("License-Banned",
																"License-None",
																"License-Copyleft");

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

			//if (isSecurityPolicy(pname) || isLicensePolicy(pname)){
				policyIds = policyIds.concat("p=" + id + "&");
			//}
		}

		policyIds = utilService.removeLastChar(policyIds);
		return policyIds;
	}

	public static boolean isSecurityPolicy(String policyName){
		final boolean contains = securityPolicies.contains(policyName);
		return contains;
	}

	public static boolean isLicensePolicy(String policyName){
		final boolean contains = licensePolicies.contains(policyName);
		return contains;
	}
}
