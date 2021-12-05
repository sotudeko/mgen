package org.so.example.mgen.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Service
public class NexusIQApiDataService {
    private static final Logger log = LoggerFactory.getLogger(NexusIQApiDataService.class);

    @Value("${iq.url}")
    private String iqUrl;

    @Value("${iq.user}")
    private String iqUser;

    @Value("${iq.passwd}")
    private String iqPasswd;

    @Value("${iq.api}")
    private String iqApi;

    public JsonObject getData(String endPoint) throws IOException {
        String urlString = iqUrl + iqApi + endPoint;
        log.info("Fetching data from " + urlString);

        URL url = new URL(urlString);

        String authString = iqUser + ":" + iqPasswd;
        byte[] encodedAuth = Base64.encodeBase64(authString.getBytes(StandardCharsets.ISO_8859_1));
        String authStringEnc = new String(encodedAuth);

        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        JsonObject dataObj = null;

        try {
            InputStream is = urlConnection.getInputStream();
            JsonReader reader = Json.createReader(is);
            dataObj = reader.readObject();
            reader.close();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return dataObj;
    }
}
