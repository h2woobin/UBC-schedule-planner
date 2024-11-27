package persistence;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.Exam;
import model.ExamControl;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Exam ex = new Exam("cpsc", 241010, 11, "B101", 85);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }

    }

    @Test
    void testWriterEmptyExam() {
        ExamControl tesExamControl = new ExamControl();

        try {
            tesExamControl.addSubject("cpsc", 241010, 11, "B101", 85);

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            JSONObject json = tesExamControl.toJson();
            writer.open();
            writer.write(json);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            List<Exam> exams = reader.read();
            assertEquals(1, exams.size());
            Exam testExam = exams.get(0);

            assertEquals("cpsc", testExam.getSub());
            assertEquals(241010, testExam.getDate());
            assertEquals(11, testExam.getTime());
            assertEquals("B101", testExam.getLocation());
            assertEquals(85, testExam.getGoalMark());

            checkExam(testExam, "cpsc", 241010, 11, "B101", 85);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
