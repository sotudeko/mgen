package org.so.example.mgen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.reports.ApplicationEvaluations;
import org.so.example.mgen.reports.Organizations;
import org.so.example.mgen.reports.PolicyViolations;
import org.so.example.mgen.reports.Waivers;
import org.so.example.mgen.service.NexusIQApiReaderService;
import org.so.example.mgen.service.NexusIQApiDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.json.JsonArray;
import javax.json.JsonObject;


@SpringBootApplication
public class MgenApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(MgenApplication.class);

	@Autowired
	private NexusIQApiReaderService nexusIQApiService;

	@Autowired
	private NexusIQApiDataService nexusIQDataService;

	public static void main(String[] args) {
		SpringApplication.run(MgenApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		nexusIQApiService.makeReport(new Organizations(), "/organizations");
		nexusIQApiService.makeReport(new ApplicationEvaluations(), "/reports/applications");
		nexusIQApiService.makeReport(new Waivers(), "/reports/components/waivers");

		JsonObject dataObj = nexusIQDataService.getData("/policies");
		String policyIds = getPolicyIds(dataObj);
		String policyViolationsEndpoint = "/policyViolations?" + policyIds;
		nexusIQApiService.makeReport(new PolicyViolations(), policyViolationsEndpoint);
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

		policyIds = this.removeLastChar(policyIds);
		return policyIds;
	}

	private String removeLastChar(String s) {
		return (s == null || s.length() == 0)
				? null
				: (s.substring(0, s.length() - 1));
	}
}
