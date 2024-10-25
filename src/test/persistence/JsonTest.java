package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.Exam;

public class JsonTest {
    protected void checkExam(Exam exam, String subject, int date, int time, String location, int goalMark) {
        assertEquals(subject, exam.getSub());
        assertEquals(date, exam.getDate());
        assertEquals(time, exam.getTime());
        assertEquals(location, exam.getLocation());
        assertEquals(goalMark, exam.getGoalMark());
    }
}
