package model;

import java.util.ArrayList;
import java.util.List;

import persistence.Writable;

import org.json.JSONArray;
import org.json.JSONObject;
import model.EventLog;
import model.Event;

public class ExamControl implements Writable {
    private List<Exam> examList = new ArrayList<>();
    private Exam examObject;

    // EFFECTS: add imformation about subject and add it to the list
    public void addSubject(String subject, int date, int time, String location, int goalMark) {
        examObject = new Exam(subject, date, time, location, goalMark);

        if (examList.stream().noneMatch(e -> e.getSub().trim().toLowerCase().equals(subject.toLowerCase().trim()))) {
            examList.add(examObject);
            EventLog.getInstance().logEvent(new Event("Subject added: " + subject));
        }
    }

    // REQUIRED: examList.contains(subject)
    // EFFECTS: choose the subject which user wants to delete and delete.
    public void deleteSubject(String subject) {
        boolean remove = examList.removeIf(exam -> exam.getSub().trim().equalsIgnoreCase(subject.toLowerCase().trim()));
        if (remove) {
            EventLog.getInstance().logEvent(new Event("Subject removed: " + subject));
        }
    }

    // REQUIRED: examList.contains(subject)
    // EFFECTS: add user's actual mark for calculate their gpa
    public void addActualMark(String subject, int actMark) {
        for (Exam e : examList) {
            if (e.getSub().equals(subject)) {
                e.setActMark(actMark);
                EventLog.getInstance()
                        .logEvent(new Event("Actual mark added for subject: " + subject + " (Mark: " + actMark + ")"));
            }
        }
    }

    // REQUIRED: !examList.isEmpty()
    // EFFECTS: calculate user's average score
    public double averageScore() {
        double total = 0;
        for (Exam e : examList) {
            total += e.getActMark();
        }
        return total / examList.size();
    }

    // REQUIRED: averageScore() != null
    // EFFECTS: calculate user's gpa and return gpa as String type
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

    // EFFECTS: get exam List
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