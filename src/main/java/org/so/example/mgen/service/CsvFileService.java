package org.so.example.mgen.service;

import javax.json.JsonObject;
import javax.json.JsonReader;

public interface CsvFileService {
    public void makeCsvFile(JsonReader reader);
    public void makeCsvFile(JsonObject reader);
}
