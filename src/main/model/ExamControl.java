package model;

import java.util.ArrayList;
import java.util.List;

import persistence.Writable;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExamControl implements Writable {
    private List<Exam> examList = new ArrayList<>();
    private Exam examObject;


    public void addSubject(String subject, int date, int time, String location, int goalMark) {
        examObject = new Exam(subject, date, time, location, goalMark);

        if (examList.stream().noneMatch(e -> e.getSub().equals(subject))) {
            examList.add(examObject);
        }
    }

    public void deleteSubject(String subject) {
        for (Exam e : examList) {
            if (e.getSub().equals(subject)) {
                examList.remove(e);
            }
        }
    }

    public void addActualMark(String subject, int actMark) {
        for (Exam e : examList) {
            if (e.getSub().equals(subject)) {
                e.setActMark(actMark);
            }
        }
    }

    public double averageScore() {
        double total = 0;
        for (Exam e : examList) {
            total += e.getActMark();
        }
        return total / examList.size();
    }

    public String gpa() {
        String gpa = "";
        double average = averageScore();
        if (average >= 90) {
            gpa = "A+";
        } else if (average >= 80) {
            gpa = "B+";
        } else if (average >= 70) {
            gpa = "C+";
        } else if (average >= 60) {
            gpa = "D";
        } else {
            gpa = "F";
        }
        return gpa;
    }

    public List<Exam> getExamList() {
        return examList;
    }

    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Exam exam : examList) {
            jsonArray.put(exam.toJson());
        }

        JSONObject json = new JSONObject();
        json.put("exams", jsonArray);
        return json;
    }

}