package model;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import persistence.Writable;

// Represent detail of exams imformation 
public class Exam implements Writable {
    private String location; // the room number or name
    private String subject; // exam subject
    private int date; // Date of exam
    private int time; // Time of exam
    private int goalMark; // the mark user wants to get in their exam
    private int actMark;

    // Create new Exam
    public Exam(String subject, int date, int time, String location, int goalMark) {
        this.subject = subject;
        this.date = date;
        this.time = time;
        this.location = location;
        this.goalMark = goalMark;
        this.actMark = 0;
    }

    public String getSub() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGoalMark() {
        return goalMark;
    }

    public void setGoalMark(int goalMark) {
        this.goalMark = goalMark;
    }

    public int getActMark() {
        return actMark;
    }

    public void setActMark(int actMark) {
        this.actMark = actMark;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Subject", subject);
        json.put("Date", date);
        json.put("Time", time);
        json.put("Location", location);
        json.put("Goal Mark", goalMark);
        return json;
    }

}
