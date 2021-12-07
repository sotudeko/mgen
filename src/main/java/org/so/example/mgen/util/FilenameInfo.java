package org.so.example.mgen.util;

import org.springframework.stereotype.Component;

@Component
public class FilenameInfo {

    public static final String applicationEvaluationsCsvFile = "applicationevaluations.csv";
    public static final String[] applicationEvaluationsFileHeader = {"Application Name", "Evaluation Date", "Stage"};

    public static final String waiversCsvFile = "componentwaivers.csv";
    public static final String[] waiversFileHeader = {"Application Name", "Stage", "Package Url", "Policy Name", "Threat Level", "Comment", "Create Date", "Expiry Time"};

    public static final String quarantinedComponentsCsvFile = "quarantinedComponents.csv";
    public static final String[] quarantinedComponentsFileHeader = {"Id", "Name"};

    public static final String autoReleasedFromQuarantineComponentsCsvFile = "autoReleasedFromQuarantineComponents.csv";
    public static final String[] autoReleasedFromQuarantineComponentsFileHeader = {"Application Name", "Evaluation Date", "Stage"};

    public static final String autoReleasedFromQuarantineConfigCsvFile = "autoReleasedFromQuarantineConfig.csv";
    public static final String[] autoReleasedFromQuarantineConfigFileHeader = {"Id", "Name"};

    public static final String quarantinedComponentsSummaryCsvFile = "quarantinedComponentsSummary.csv";
    public static final String[] quarantinedComponentsSummaryFileHeader = {"Id", "Name"};

    public static final String autoReleasedFromQuarantineSummaryCsvFile = "autoReleasedFromQuarantineSummary.csv";
    public static final String[] autoReleasedFromQuarantineSummaryFileHeader = {"Id", "Name"};

    public static final String organizationsCsvFile = "organizations.csv";
    public static final String[] organizationsFileHeader = {"Id", "Name"};

    public static final String policyViolationsCsvFile = "policyViolations.csv";
    public static final String[] policyViolationsFileHeader = {"Policy Name", "Reason", "Application Name", "Open Time", "Component", "Stage"};
}
