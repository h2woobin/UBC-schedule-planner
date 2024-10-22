package ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Exam;
import model.ExamControl;

// Application for making exam schedules
public class ExamScheduleApp {
    private Scanner scanner = new Scanner(System.in);
    private boolean runningApp = true;
    private List<Exam> examList;
    private ExamControl.InnerExamControl examControl;
    private String gpa;

    // EFFECTS: run the application
    public ExamScheduleApp() {
        ExamControl ouExamControl = new ExamControl();
        examControl = ouExamControl.new InnerExamControl();
        this.examList = examControl.getExamList();
        runApp();
    }

    // EFFECTS: processes user input
    public void runApp() {
        while (this.runningApp == true) {
            System.out.println("Wellcome to the schedule app.");
            intro();
        }
    }

    // EFFECTS: asking to users what they want to do in this application.
    public void introQuestion() {
        line();
        System.out.println("a: Add the subject and details");
        System.out.println("b: Modify details of subject or delete the subject");
        System.out.println("c: Calculate the average score and get my GPA");
        System.out.println("q: Quit the app");
        line();
    }

    /*
     * EFFECTS: Repeatedly ask to users to choose an action by selecting a ~ q.
     * - a: Add new Exam
     * - b: Delete Exam or modify information about Exam
     * - c: give average score and GPA
     * - q: stop the application
     */
    public void intro() {
        String alphabet = "";

        while (!alphabet.equalsIgnoreCase("q")) {
            introQuestion();
            System.out.println("Select an alphabet you want to do");
            alphabet = scanner.nextLine();

            if (alphabet.equals("a")) {
                addSubject();
            } else if (alphabet.equals("b")) {
                delOrModSubject();
            } else if (alphabet.equals("c")) {
                gpa();
            } else if (alphabet.equals("q")) {
                quitApp();
            } else {
                System.out.println("You put the wrong alphabet!");
            }
        }

    }

    /*
     * EFFECTS: Ask user to enter information about their exam and save in examList.
     * After user done putting information, application will ask add more or not.
     * If users chose "Y", it will keep ask adding information, otherwise stop.
     */
    public void addSubject() {
        String state = "Y";

        while (state.equalsIgnoreCase("Y")) {
            System.out.println("Subject: ");
            String subject = scanner.nextLine();

            System.out.println("Date(YYMMDD): ");
            int date = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Time: ");
            int time = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Location: ");
            String location = scanner.nextLine();

            System.out.println("Goal Mark: ");
            int goalMark = scanner.nextInt();
            scanner.nextLine();

            Exam examDetail = new Exam(subject, date, time, location, goalMark);
            examList.add(examDetail);

            line();

            System.out.println("Do you want to add more? (Y/N): ");
            String answer = scanner.nextLine();
            state = answer;
            if (state.equalsIgnoreCase("n")) {
                getExam();
            }
        }
    }

    public void getExam() {
        if (!examList.isEmpty()) {
            for (Exam examObject : examList) {
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

    /*
     * EFFECTS: Prompts the user to either delete or modify a subject.
     * If 'D' is entered, prompts the user for the subject to delete and removes it
     * if found.
     * If 'M' is entered, allows the user to modify the details of an existing
     * subject.
     * If the subject is not found, prints an error message.
     * If an invalid choice is entered, prints an error message.
     * https://us.prairielearn.com/pl/workspace/2200330
     */
    public void delOrModSubject() {
        System.out.println("Press the alphabet / Delete (D) or Modify (M)/: ");
        String alpha = scanner.nextLine();

        if (alpha.equalsIgnoreCase("D")) {
            System.out.print("Which subject do you want to delete: ");
            String delSub = scanner.nextLine();
            boolean isDeleted = examControl.removeExam(delSub);
            getExam();
            if (!isDeleted) {
                System.out.println("Can't find " + delSub + " subject!");
            }
        } else if (alpha.equalsIgnoreCase("M")) {
            modSub();
        } else {
            System.out.println("Sorry, You didn't press D or M.");
        }
    }

    public void modSub() {
        System.out.println("Which subject do you want to modify: ");
        String modSub = scanner.nextLine();
        System.out.print("#1: Subject's name \n#2: Location \n#3: Date \n#4: Time \n#5: Goal Mark\n");
        int modNum = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;
        for (Exam exam : examList) {
            if (exam.getSub().equalsIgnoreCase(modSub)) {
                found = true;
                switch (modNum) {
                    case 1:
                        System.out.println("Enter new subject: ");
                        String newSubject = scanner.nextLine();
                        exam.setSubject(newSubject);
                        break;
                    case 2:
                        System.out.println("Enter new location: ");
                        String newLocation = scanner.nextLine();
                        exam.setSubject(newLocation);
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
                getExam();
            }
        }
        System.out.println(modSub + "'s information has been changed. ");
    }

    /*
     * EFFECTS: Users can put their grade and it will calculate their average score.
     * Also, it will give user their gpa.
     */
    public double averageScore() {
        double totalMark = 0;

        for (int i = 0; i < examList.size(); i++) {
            boolean validInput = false;

            while (!validInput) {
                try {
                    System.out.print("Enter the mark you got (ex.85.00): ");
                    double actualMark = scanner.nextDouble();
                    scanner.nextLine();

                    if (actualMark < 0 || actualMark > 100) {
                        System.out.println("Please enter a mark between 0 and 100.");
                    } else {
                        totalMark += actualMark;
                        validInput = true;
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number (e.g., 85.00).");
                    scanner.next();
                }
            }
        }

        double averageScore = totalMark / examList.size();
        return averageScore;
    }

    public void gpa() {
        double aveScore = averageScore();
        if (aveScore >= 90) {
            gpa = "A+";
        } else if (aveScore >= 80) {
            gpa = "B+";
        } else if (aveScore >= 70) {
            gpa = "C+";
        } else if (aveScore >= 60) {
            gpa = "D";
        } else {
            gpa = "F";
        }
        System.out.println("Your average score is: " + aveScore);
        System.out.println("Your gpa is: " + gpa);
    }

    /*
     * MODIFES: this
     * EFFECTS: close the application.
     */
    public void quitApp() {
        System.out.println("Thank you for using our app.");
        System.out.println("Fake it till make it!");
        this.runningApp = false;
    }

    public void line() {
        System.out.println(("----------------------------"));
    }

}
