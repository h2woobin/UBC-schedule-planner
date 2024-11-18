package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ExamTest {
    private ExamControl tesExamControl;

    @BeforeEach
    void runBefore() {
        tesExamControl = new ExamControl();
    }

    @Test
    void testExamAndAddExam() {
        tesExamControl.addSubject("cpsc", 241016, 11, "B015", 85);
        tesExamControl.addSubject("chem", 241017, 15, "B017", 85);
        assertEquals(2, tesExamControl.getExamList().size());

        Exam examOne = tesExamControl.getExamList().get(0);

        assertEquals("cpsc", examOne.getSub());
        assertEquals(241016, examOne.getDate());
        assertEquals(11, examOne.getTime());
        assertEquals(85, examOne.getGoalMark());
        assertEquals("B015", examOne.getLocation());

        examOne.setSubject("ling");
        assertEquals("ling", examOne.getSub());

        examOne.setDate(240505);
        assertEquals(240505, examOne.getDate());

        examOne.setLocation("C123");
        assertEquals("C123", examOne.getLocation());

        examOne.setGoalMark(99);
        assertEquals(99, examOne.getGoalMark());

        examOne.setTime(13);
        assertEquals(13, examOne.getTime());
        
    }

    @Test
    void testAddSameSubject(){
        tesExamControl.addSubject("chem", 241016, 11, "B015", 85);
        tesExamControl.addSubject("chem", 241017, 15, "B017", 85);

        assertEquals(1, tesExamControl.getExamList().size());
    }

    @Test
    void testDeleteSubject() {
        tesExamControl.addSubject("cpsc", 241016, 11, "B015", 85);
        tesExamControl.addSubject("chem", 241017, 15, "B017", 85);
        assertEquals(2, tesExamControl.getExamList().size());

        Exam examOne = tesExamControl.getExamList().get(0);

        tesExamControl.deleteSubject("cpsc");
        assertEquals(1, tesExamControl.getExamList().size());

        tesExamControl.deleteSubject("cpsc");
        assertEquals(1, tesExamControl.getExamList().size());
    }

    @Test
    void testAddActualMark() {
        tesExamControl.addSubject("cpsc", 241016, 11, "B015", 85);
        tesExamControl.addSubject("chem", 241017, 15, "B017", 85);

        Exam examOne = tesExamControl.getExamList().get(0);
        assertEquals(0, examOne.getActMark());

        tesExamControl.addActualMark("cpsc", 85);
        assertEquals(85, examOne.getActMark());
    }

    @Test
    void testAverageAndGpa() {
        tesExamControl.addSubject("cpsc", 241016, 11, "B015", 85);
        tesExamControl.addSubject("chem", 241017, 15, "B017", 85);

        Exam examOne = tesExamControl.getExamList().get(0);

        tesExamControl.addActualMark("cpsc", 85);
        tesExamControl.addActualMark("chem", 90);

        assertEquals("B+", tesExamControl.gpa());

        examOne.setActMark(100);
        assertEquals("A+", tesExamControl.gpa());

        examOne.setActMark(60);
        assertEquals("C+", tesExamControl.gpa());

        examOne.setActMark(40);
        assertEquals("D", tesExamControl.gpa());

        examOne.setActMark(10);
        assertEquals("F", tesExamControl.gpa());
    }
}
