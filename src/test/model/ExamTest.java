package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExamTest {
    private Exam testExamOne;
    private Exam testExamTwo;
    private ExamControl.InnerExamControl examControl;
    private List<Exam> listOfExam;

    @BeforeEach
    void runBefore() {
        examControl = new ExamControl().new InnerExamControl();
        listOfExam = examControl.getExamList();
        testExamOne = new Exam("cpsc", 241016, 11, "B015", 85);
        testExamTwo = new Exam("chem", 241017, 15, "B017", 85);
    }

    @Test
    void testConstruture() {
        assertEquals("cpsc", testExamOne.getSub());
        assertEquals(241016, testExamOne.getDate());
        assertEquals(15, testExamTwo.getTime());
        assertEquals(85, testExamTwo.getGoalMark());
        assertEquals("B015", testExamOne.getLocation());
    }

    @Test
    void testRemoveExam() {
        listOfExam.add(testExamOne);
        listOfExam.add(testExamTwo);
        assertEquals(2, listOfExam.size());
        examControl.removeExam("chem");
        assertEquals(1, listOfExam.size());
    }

    @Test
    void testChangeSubject() {
        testExamOne.setSubject("bio");
        assertEquals("bio", testExamOne.getSub());
        testExamOne.setSubject("math");
        assertEquals("math", testExamOne.getSub());
    }

}
