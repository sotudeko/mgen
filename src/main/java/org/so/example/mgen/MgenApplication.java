package org.so.example.mgen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.reports.ApplicationEvaluations;
import org.so.example.mgen.reports.Organizations;
import org.so.example.mgen.reports.PolicyViolations;
import org.so.example.mgen.reports.Waivers;
import org.so.example.mgen.service.NexusIQApiService;
import org.so.example.mgen.service.NexusIQDataService;
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
	private NexusIQApiService nexusIQApiService;

	@Autowired
	private NexusIQDataService nexusIQDataService;

	public static void main(String[] args) {
		SpringApplication.run(MgenApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		nexusIQApiService.makeReport(new Organizations(), "/organizations", true);
		nexusIQApiService.makeReport(new ApplicationEvaluations(), "/reports/applications", true);
		nexusIQApiService.makeReport(new Waivers(), "/reports/components/waivers", true);

		JsonObject dataObj = nexusIQDataService.getData("/policies");
		String policyIds = getPolicyIds(dataObj);
		String policyViolationsEndpoint = "/policyViolations?" + policyIds;
		nexusIQApiService.makeReport(new PolicyViolations(), policyViolationsEndpoint, true);
	}

	private String getPolicyIds(JsonObject obj) {
		log.info("Getting policy ids");

		JsonArray results = obj.getJsonArray("policies");

		String p = "";

		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
			String id = result.getString("id");
			String pname = result.getString("name");

			p = p.concat("p=" + id + "&");
		}

		p = this.removeLastChar(p);
		return p;
	}

	private String removeLastChar(String s) {
		return (s == null || s.length() == 0)
				? null
				: (s.substring(0, s.length() - 1));
	}
}
