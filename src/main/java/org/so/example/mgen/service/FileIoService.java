package org.so.example.mgen.service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class FileIoService {

    public void writeCsvFile(String filename, List<String[]> data){
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename));

            for (String[] array : data) {
                //log.info("- " + Arrays.toString(array));
                writer.write(String.join(",", Arrays.asList(array)));
                writer.newLine();
            }

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }
}
