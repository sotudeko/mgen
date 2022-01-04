package org.so.example.mgen.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.so.example.mgen.reports.ApplicationEvaluations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.nio.file.StandardCopyOption;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import java.io.InputStream;


@Service
public class FileIoService {
    private static final Logger log = LoggerFactory.getLogger(ApplicationEvaluations.class);

    @Value("${metrics.dir}")
    private String metricsDir;

    @Value("${data.successmetrics}")
	private String successmetricsFile;


    public void writeCsvFile(String filename, List<String[]> data){

        String metricsFile = metricsDir + "/" + filename;

        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(metricsFile));

            for (String[] array : data) {
                writer.write(String.join(",", Arrays.asList(array)));
                writer.newLine();
            }

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Created file: " + metricsFile);
        return;
    }

    public void initMetricsDir() throws IOException {
        FileUtils.deleteDirectory(new File(metricsDir));
        Files.createDirectory(Paths.get(metricsDir));
        return;
    }

    private void deleteDirectory(String dirname){

        Path path = Paths.get(dirname);

        if (Files.exists(path)){
            try {
                Stream<Path> files = Files.walk(path);

                files.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::deleteOnExit);

                files.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        return;
    }
    
    public void writeSuccessMetricsFile(InputStream content) throws IOException {
	    File outputFile = new File(metricsDir + File.separator + successmetricsFile);
	    java.nio.file.Files.copy(content, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	    IOUtils.closeQuietly(content);
		return;
	}

}
