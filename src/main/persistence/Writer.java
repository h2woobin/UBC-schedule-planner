package persistence;

import model.Exam;
import org.json.JSONObject;

import java.io.*;

// 내가 하고싶은거 > 시험 리스트 만들어서 파일에 저장하기 / 저장한거 불러오기 

public class Writer {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public Writer(String destination){
        this.destination = destination;
    }

    public void open() throws FileNotFoundException{
        writer = new PrintWriter(new File(destination));
    }

    public void write(JSONObject jsonObject){
        saveToFile(jsonObject.toString(TAB));
    }

    public void close(){
        writer.close();
    }

    public void saveToFile(String a){
        writer.print(a);
    }
}
