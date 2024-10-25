package model;

import java.util.ArrayList;
import java.util.List;
// Handle all exam lists that user made.

import org.json.JSONArray;
import org.json.JSONObject;

public class ExamControl {

    public class InnerExamControl {
        private List<Exam> examList = new ArrayList<>();

        public List<Exam> getExamList() {
            return examList;
        }

        /*
         * REQUIRES: subject should be not null String.
         * MODIFIES: this.examList
         * EFFECTS: remove all imformation what user wants to remove. Check if the
         * subject that user wants to delete
         * is in the exam list or not. If it's in it, it will be removed and return
         * true. Otherwise, mothod will give false.
         */
        public boolean removeExam(String subject) {
            for (int i = 0; i < examList.size(); i++) {
                Exam exam = examList.get(i);
                if (exam.getSub().equalsIgnoreCase(subject)) {
                    examList.remove(i);
                    return true;
                }
            }
            return false;
        }

        public void addExam(Exam exam) {
            examList.add(exam);
        }

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
}