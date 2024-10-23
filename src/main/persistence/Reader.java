package persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;;


public class Reader {
    private String source;

    public Reader(String source){
        this.source = source;
    }
    
    public JSONObject read() throws IOException{
        String jsonData = new String(Files.readAllBytes(Paths.get(source)));
        return new JSONObject(jsonData);
    }
}
