package org.so.example.mgen.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class NexusIQAPIPagingService {
    private static final Logger log = LoggerFactory.getLogger(NexusIQAPIPagingService.class);

    @Autowired
    private FileIoService fileIoService;

    @Value("${iq.url}")
    private String iqUrl;

    @Value("${iq.user}")
    private String iqUser;

    @Value("${iq.passwd}")
    private String iqPasswd;

    @Value("${iq.api}")
    private String iqApi;

    public void makeReport(CsvFileService cfs, String endPoint) throws IOException {

        int page = 1;
        int pageSize = 10;
        int pageCount = 0;

        JsonReader reader = null;

        try {
            do {
                InputStream is = getConnection(endPoint, page, pageSize).getInputStream();
                reader = Json.createReader(is);
                JsonObject obj = reader.readObject();

                page = obj.getInt("page");
                pageSize = obj.getInt("pageSize");
                pageCount = obj.getInt("pageCount");

                //log.info(("page number: " + page + "(" + pageCount + ")"));

                cfs.makeCsvFile(fileIoService, obj);
                page += 1;
            } while (page <= pageCount);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        finally {
            reader.close();
        }

        return;
    }

    private URLConnection getConnection(String endPoint, int page, int pageSize) throws IOException {
        //String query = endPoint + "?" + page + "&pageSize=" + pageSize + "&sortBy=" + sortField + "&asc=asc";
        String query = endPoint + "?" + "page=" + page + "&pageSize=" + pageSize + "&asc=true";

        String urlString = iqUrl + iqApi + query;
        //log.info("Fetching data from " + urlString);

        URL url = new URL(urlString);

        String authString = iqUser + ":" + iqPasswd;
        byte[] encodedAuth = Base64.encodeBase64(authString.getBytes(StandardCharsets.ISO_8859_1));
        String authStringEnc = new String(encodedAuth);

        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        return urlConnection;
    }


}
