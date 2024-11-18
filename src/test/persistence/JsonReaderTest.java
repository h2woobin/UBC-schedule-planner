package persistence;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import model.Exam;

import static org.junit.jupiter.api.Assertions.*;
import model.ExamControl;

public class JsonReaderTest extends JsonTest {
    private ExamControl.InnerExamControl examControl;

    @Test
    void testReaderNonExistentFile() {
        // ExamControl ouExamControl = new ExamControl();
        // examControl = ouExamControl.new InnerExamControl();

        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            List<Exam> exams = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        ExamControl ouExamControl = new ExamControl();
        examControl = ouExamControl.new InnerExamControl();

        JsonReader reader = new JsonReader("./data/testWriterEmptyWorkRoom.json");
        try {
            List<Exam> exams = reader.read();
            Exam testExam = exams.get(0);

            assertEquals(1, exams.size());
            assertEquals("cpsc", testExam.getSub());
            assertEquals(241010, testExam.getDate());
            assertEquals(11, testExam.getTime());
            assertEquals("B101", testExam.getLocation());
            assertEquals(85, testExam.getGoalMark());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
