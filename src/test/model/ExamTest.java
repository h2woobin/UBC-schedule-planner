package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals("cpsc",testExamOne.getSub());
        assertEquals(241016, testExamOne.getDate());
        assertEquals(15,testExamTwo.getTime());
        assertTrue(testExamTwo.getGoalMark() >= 0);
    }
}
