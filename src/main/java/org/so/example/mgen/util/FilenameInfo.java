package org.so.example.mgen.util;

import org.springframework.stereotype.Component;

@Component
public class FilenameInfo {

    public static final String applicationEvaluationsCsvFile = "application_evaluations.csv";
    public static final String[] applicationEvaluationsFileHeader = {"Application Name", "Evaluation Date", "Stage"};

    public static final String[] componentWaiversFileHeader = {"Application Name", "Stage", "Package Url", "Policy Name", "Threat Level", "Comment", "Create Date", "Expiry Time"};
    public static final String componentWaiversCsvFile = "component_waivers.csv";

    public static final String releaseQuarantineSummarCsvFile = "application_evaluations.csv";
    public static final String[] releaseQuarantineSummarFileHeader = {"Application Name", "Evaluation Date", "Stage"};

    public static final String organizationsCsvFile = "organizations.csv";
    public static final String[] organizationsFileHeader = {"Id", "Name"};
}
