package org.so.example.mgen.util;

import org.springframework.stereotype.Component;

@Component
public class FilenameInfo {

    public static final String successMetricsCsvFile = "successmetrics.csv";

    public static final String applicationEvaluationsCsvFile = "applicationevaluations.csv";
    public static final String[] applicationEvaluationsFileHeader = {"ApplicationName", "EvaluationDate", "Stage"};

    public static final String waiversCsvFile = "componentwaivers.csv";
    public static final String[] waiversFileHeader = {"ApplicationName", "Stage", "PackageUrl", "PolicyName", "ThreatLevel", "Comment", "CreateDate", "ExpiryTime"};

    public static final String quarantinedComponentsCsvFile = "quarantinedComponents.csv";
    public static final String[] quarantinedComponentsFileHeader = {"Package Url", "Repository", "Time"};

    public static final String autoReleasedFromQuarantineComponentsCsvFile = "autoReleasedFromQuarantineComponents.csv";
    public static final String[] autoReleasedFromQuarantineComponentsFileHeader = {"Application Name", "Evaluation Date", "Stage"};

    public static final String autoReleasedFromQuarantineConfigCsvFile = "autoReleasedFromQuarantineConfig.csv";
    public static final String[] autoReleasedFromQuarantineConfigFileHeader = {"Config Id", "Name", "Enabled"};

    public static final String quarantinedComponentsSummaryCsvFile = "quarantinedComponentsSummary.csv";
    public static final String[] quarantinedComponentsSummaryFileHeader = {"Repository Count", "Quarantine Enabled Count", "Quarantine Enabled", "Total Component Count", "Quarantined Component Count"};

    public static final String autoReleasedFromQuarantineSummaryCsvFile = "autoReleasedFromQuarantineSummary.csv";
    public static final String[] autoReleasedFromQuarantineSummaryFileHeader = {"MTD", "YTD"};

    public static final String organizationsCsvFile = "organizations.csv";
    public static final String[] organizationsFileHeader = {"Id", "Name"};

    public static final String policyViolationsCsvFile = "policyViolations.csv";
    public static final String[] policyViolationsFileHeader = {"PolicyName", "Reason", "ApplicationName", "OpenTime", "Component", "Stage"};
}
