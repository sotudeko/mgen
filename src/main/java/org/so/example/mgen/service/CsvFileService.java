package org.so.example.mgen.service;

import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import javax.json.JsonReader;

@Service
public interface CsvFileService {
    public void makeCsvFile(JsonReader reader);
    public void makeCsvFile(JsonObject reader);
}
