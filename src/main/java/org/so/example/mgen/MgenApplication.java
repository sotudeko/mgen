package org.so.example.mgen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.reports.*;
import org.so.example.mgen.service.FileIoService;
import org.so.example.mgen.service.NexusIQAPIPagingService;
import org.so.example.mgen.service.NexusIQApiService;
import org.so.example.mgen.service.PolicyIdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MgenApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(MgenApplication.class);

	@Autowired
	private PolicyIdsService policyIdsService;

	@Autowired
	private NexusIQAPIPagingService nexusIQAPIPagingService;

	@Autowired
	private NexusIQApiService nexusIQApiService;

	@Autowired
	private FileIoService fileIoService;

	public static void main(String[] args) {
		SpringApplication.run(MgenApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Starting MgenApplication");

		fileIoService.initMetricsDir();

		nexusIQApiService.makeReport(new ApplicationEvaluations(), "/reports/applications");
		nexusIQApiService.makeReport(new Waivers(), "/reports/components/waivers");
		nexusIQApiService.makeReport(new PolicyViolations(), policyIdsService.getPolicyIdsEndpoint());
		nexusIQApiService.makeReport(new AutoReleasedFromQuarantineSummary(), "/firewall/releaseQuarantine/summary");
		nexusIQApiService.makeReport(new QuarantinedComponentsSummary(), "/firewall/quarantine/summary");
		nexusIQApiService.makeReport(new AutoReleasedFromQuarantineConfig(), "/firewall/releaseQuarantine/configuration");

		nexusIQAPIPagingService.makeReport(new QuarantinedComponents(), "/firewall/components/quarantined");
		nexusIQAPIPagingService.makeReport(new AutoReleasedFromQuarantineComponents(), "/firewall/components/autoReleasedFromQuarantine");
	}
}
