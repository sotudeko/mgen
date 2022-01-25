package org.so.example.mgen.util;

import org.springframework.stereotype.Component;

@Component
public class FilenameInfo {

    public static final String successMetricsCsvFile = "successmetrics.csv";

    public static final String applicationEvaluationsCsvFile = "application_evaluations.csv";
    public static final String[] applicationEvaluationsFileHeader = {"applicationName", "avaluationDate", "stage"};

    public static final String policyViolationsCsvFile = "policy_violations.csv";
    public static final String[] policyViolationsFileHeader = {"policyName", "reason", "applicationName", "openTime", "component", "stage", "threatLevel"};

    public static final String waiversCsvFile = "waivers.csv";
    public static final String[] waiversFileHeader = {"applicationName", "stage", "packageUrl", "policyName", "threatLevel", "comment", "createDate", "expiryTime"};

    public static final String quarantinedComponentsCsvFile = "quarantined_components.csv";
    public static final String[] quarantinedComponentsFileHeader = {"packageUrl", "repository", "quarantineDate"};

    public static final String quarantinedComponentsSummaryCsvFile = "quarantined_components_summary.csv";
    public static final String[] quarantinedComponentsSummaryFileHeader = {"repositoryCount", "quarantineEnabledCount", "quarantine nabled", "totalComponentCount", "quarantinedComponentCount"};

    public static final String autoReleasedFromQuarantineComponentsCsvFile = "autoreleased_from_quarantine_components.csv";
    public static final String[] autoReleasedFromQuarantineComponentsFileHeader = {"displayName", "repository", "quarantineDate", "dateCleared"};

    public static final String autoReleasedFromQuarantineSummaryCsvFile = "autoreleased_from_quarantine_components_summary.csv";
    public static final String[] autoReleasedFromQuarantineSummaryFileHeader = {"MTD", "YTD"};

    public static final String autoReleasedFromQuarantineConfigCsvFile = "autoreleased_from_quarantine_config.csv";
    public static final String[] autoReleasedFromQuarantineConfigFileHeader = {"id", "name", "autoReleaseQuarantineEnabled"};

}
