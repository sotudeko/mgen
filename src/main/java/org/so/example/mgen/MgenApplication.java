package org.so.example.mgen;

import org.so.example.mgen.reports.ApplicationEvaluations;
import org.so.example.mgen.reports.Organizations;
import org.so.example.mgen.service.NexusIQApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MgenApplication implements CommandLineRunner {

	@Autowired
	private NexusIQApiService nexusIQApiService;
	
	public static void main(String[] args) {
		SpringApplication.run(MgenApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		nexusIQApiService.makeReport(new Organizations(), "/organizations");
		nexusIQApiService.makeReport(new ApplicationEvaluations(), "/reports/applications");

	}
}
