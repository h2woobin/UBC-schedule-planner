package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new ExamScheduleApp();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Unable to find the file for loading/saving exams.");
        }

    }
}
