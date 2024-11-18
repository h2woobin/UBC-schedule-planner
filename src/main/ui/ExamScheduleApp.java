package ui;

import model.Exam;
import model.ExamControl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;


public class ExamScheduleApp {
    private Scanner scanner = new Scanner(System.in);
    private boolean runningApp = true;
    private ExamControl examControl;

    private static final String EXAMLIST_FILE = "./data/examList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public ExamScheduleApp() throws FileNotFoundException {
        examControl = new ExamControl();
        jsonReader = new JsonReader(EXAMLIST_FILE);
        jsonWriter = new JsonWriter(EXAMLIST_FILE);
        runApp();
    }

    public void runApp() throws FileNotFoundException {
        while (this.runningApp == true) {
            System.out.println("Wellcome to the schedule app.");
            intro();
        }
    }

    public void saveFile(ExamControl examControl) {
        line();
        try {
            JSONObject json = examControl.toJson();

            jsonWriter.open();
            jsonWriter.write(json);
            jsonWriter.close();
            System.out.println("Saved list to " + EXAMLIST_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("No file or can't save the file: ");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Exam from file
    private void loadExam() {
        line();
        try {
            List<Exam> exams = jsonReader.read();
            examControl.getExamList().addAll(exams);
            System.out.println("Loaded exam list from " + EXAMLIST_FILE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + EXAMLIST_FILE);
        }
    }

    public void intro() {
        String alphabet = "";

        while (!alphabet.equalsIgnoreCase("q")) {
            introQuestion();
            System.out.print("Select an alphabet you want to do: ");
            alphabet = scanner.nextLine();

            if (alphabet.equals("a")) {
                addSubject();
            } else if (alphabet.equals("b")) {
                changeImfor();
            } else if (alphabet.equals("c")) {
                System.out.println("Your GPA: " + examControl.gpa());
            } else if (alphabet.equals("e")) {
                addActualMark();
            } else if (alphabet.equals("s")) {
                saveFile(examControl);
            } else if (alphabet.equals("l")) {
                loadExam();
            } else if (alphabet.equals("p")) {
                getExam();
            } else if (alphabet.equals("q")) {
                quitApp();
            } else {
                System.out.println("You put the wrong alphabet!");
            }
        }

    }

    public void introQuestion() {
        line();
        System.out.println("a: Add the subject and details");
        System.out.println("b: Modify details of subject or delete the subject");
        System.out.println("c: Calculate the average score and get my GPA");
        System.out.println("e: Add your actual mark");
        System.out.println("s: Save file");
        System.out.println("l: load the file");
        System.out.println("p: Print the eaxm list");
        System.out.println("q: Quit the app");
        line();
    }

    public void addSubject() {
        String state = "Y";

        while (state.equalsIgnoreCase("Y")) {
            System.out.print("Subject: ");
            String subject = scanner.nextLine();

            System.out.print("Date(YYMMDD): ");
            int date = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Time: ");
            int time = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Location: ");
            String location = scanner.nextLine();

            System.out.print("Goal Mark: ");
            int goalMark = scanner.nextInt();
            scanner.nextLine();

            examControl.addSubject(subject, date, time, location, goalMark); // list에 넣는것도 포함되어있음

            line();

            System.out.println("Do you want to add more? (Y/N): ");
            state = scanner.nextLine();
        }
    }

    public void changeImfor() {
        System.out.println("Press the alphabet -> Delete (D) or Modify (M)/: ");
        String alpha = scanner.nextLine();
        getExam();
        line();

        if (alpha.equalsIgnoreCase("D")) {
            System.out.print("Which subject do you want to delete: ");
            String delSub = scanner.nextLine();
            examControl.deleteSubject(delSub);
            System.out.println(delSub + "is deleted.");

        } else if (alpha.equalsIgnoreCase("M")) {
            System.out.println("Which subject do you want to modify: ");
            String modSub = scanner.nextLine();
            System.out.print("#1: Subject's name \n#2: Location \n#3: Date \n#4: Time \n#5: Goal Mark\n");
            int modNum = scanner.nextInt();
            scanner.nextLine();

            modifySubject(modSub, modNum);
            System.out.println(modSub + "'s information has been changed. ");
            getExam();
            line();
        }
    }

    public void modifySubject(String modSub, int modNum) {
        for (Exam exam : examControl.getExamList()) {
            if (exam.getSub().equalsIgnoreCase(modSub)) {
                switch (modNum) {
                    case 1:
                        System.out.println("Enter new subject: ");
                        String newSubject = scanner.nextLine();
                        exam.setSubject(newSubject);
                        break;
                    case 2:
                        System.out.println("Enter new location: ");
                        String newLocation = scanner.nextLine();
                        exam.setLocation(newLocation);
                        break;
                    case 3:
                        System.out.println("Enter new date (YYMMDD): ");
                        int newDate = scanner.nextInt();
                        scanner.nextLine();
                        exam.setDate(newDate);
                        break;
                    case 4:
                        System.out.println("Enter new goal mark: ");
                        int newGoalMark = scanner.nextInt();
                        scanner.nextLine();
                        exam.setGoalMark(newGoalMark);
                        break;
                    default:
                        System.out.println("Invalid selection");
                        break;
                }
            }
        }
    }

    public void addActualMark() {
        for (Exam e : examControl.getExamList()) {
            System.out.print(e.getSub() + ": ");
            int mark = scanner.nextInt();
            scanner.nextLine();
            e.setActMark(mark);
        }
        System.out.println("Your marks have been added.");
    }

    public void getExam() {
        if (!examControl.getExamList().isEmpty()) {
            for (Exam examObject : examControl.getExamList()) {
                line();
                System.out.println("Subject: " + examObject.getSub());
                System.out.println("Date: " + examObject.getDate());
                System.out.println("Time: " + examObject.getTime());
                System.out.println("Location: " + examObject.getLocation());
                System.out.println("Goal Mark: " + examObject.getGoalMark());
            }
        } else {
            line();
            System.out.println("List is Empty!");
        }
    }

    public void quitApp() {
        System.out.println("Thank you for using our app.");
        System.out.println("Fake it till make it!");
        this.runningApp = false;
    }

    public void line() {
        System.out.println(("----------------------------"));
    }

}