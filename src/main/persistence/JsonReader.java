package persistence;

import model.Exam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;

import org.json.*;

// Represents a reader that reads Exam from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads exam from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Exam> read() throws IOException {
        String jsonData = readFile(source);  
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseExams(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Exam from JSON object and returns it
    private Exam parseExam(JSONObject jsonObject) {
        String subject = jsonObject.getString("Subject");
        int time = jsonObject.getInt("Time");
        int date = jsonObject.getInt("Date");
        String location = jsonObject.getString("Location");
        int goalMark = jsonObject.getInt("Goal Mark");
        
        Exam ex = new Exam(subject, date, time, location, goalMark);
        return ex;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to exam
    private List<Exam> parseExams(JSONObject jsonObject) {
        List<Exam> exams = new ArrayList();
        JSONArray jsonArray = jsonObject.getJSONArray("exams"); 
        for (Object json : jsonArray) {
            JSONObject nextExamJson = (JSONObject) json;
            Exam exam = parseExam(nextExamJson);
            exams.add(exam);
        }
        return exams;
    } 

}
