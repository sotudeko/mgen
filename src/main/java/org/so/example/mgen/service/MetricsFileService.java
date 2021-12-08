package org.so.example.mgen.service;

import javax.json.JsonObject;
import javax.json.JsonReader;

public interface MetricsFileService {
    public void makeCsvFile(FileIoService f, JsonReader r);
    public void makeCsvFile(FileIoService f, JsonObject o);
}
