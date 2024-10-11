package ui;

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
    
    // EFFECTS: run the application 
    public ExamScheduleApp(){ 
        ExamControl ouExamControl = new ExamControl();
        examControl = ouExamControl.new InnerExamControl();
        this.examList = examControl.getExamList();
        runApp();
        }

        // EFFECTS: processes user input
    public void runApp(){
        while (this.runningApp == true) {
        System.out.println("Wellcome to the schedule app.");
        intro();
        }   
    }
    
    // EFFECTS: asking to users what they want to do in this application.
    public void introQuestion(){
        line();
        System.out.println("a: Add the subject and details");
        System.out.println("b: Modify details of subject");
        System.out.println("c: Calculate the average score and get my GPA");
        System.out.println("q: Quit the app");
        line();
    }
    /*
     * EFFECTS: Repeatedly ask to users to choose an action by selecting a ~ q.
     *          - a: Add new Exam
     *          - b: Delete Exam or modify information about Exam 
     *          - c: give average score and GPA
     *          - q: stop the application
     */
    public void intro(){
        String alphabet ="";
        
        while (!alphabet.equalsIgnoreCase("q")) {
            introQuestion();
            System.out.println("Select an alphabet you want to do");
            alphabet = scanner.nextLine();

            if(alphabet.equals("a")){
                addSubject();
            }else if(alphabet.equals("b")){
                delOrModSubject();
            }else if(alphabet.equals("c")){
                double ave = averageScore();
                String gpa = gpa();
                
                System.out.print("Your average score is: "+ ave);
                System.out.print("Your gpa is: "+ gpa);
            }else if(alphabet.equals("q")){
                quitApp();
            }else{
                System.out.print("You put the wrong alphabet!");
            }
        }    

    }

    /*
     * EFFECTS: Ask user to enter information about their exam and save in examList.
     *          After user done putting information, application will ask add more or not.
     *          If users chose "Y", it will keep ask adding information, otherwise stop.
     */
    public void addSubject(){
        String state = "Y";

        while(state.equalsIgnoreCase("Y")){
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
        }
    }
/*
 * EFFECTS: Prompts the user to either delete or modify a subject.
 *          If 'D' is entered, prompts the user for the subject to delete and removes it if found.
 *          If 'M' is entered, allows the user to modify the details of an existing subject.
 *          If the subject is not found, prints an error message.
 *          If an invalid choice is entered, prints an error message.
 */
    public void delOrModSubject(){
        System.out.println("Press the alphabet / Delete (D) or Modify (M)/: ");
        String alpha = scanner.nextLine();

        if(alpha.equalsIgnoreCase("D")){
            System.out.print("Which subject do you want to delete: ");
            String delSub = scanner.nextLine();
            boolean isDeleted = examControl.removeExam(delSub);
            if(!isDeleted) {
                System.out.println("Can't find "+ delSub +" subject!");
            }
        }else if(alpha.equalsIgnoreCase("M")){
            System.out.println("Which subject do you want to modify: ");
            String modSub = scanner.nextLine();
            System.out.print("Subject:1 \nLocation:2 \nDate:3 \ntime:4 \ngoalMark:5 \nEnter the number:  ");
            int modNum = scanner.nextInt();
            scanner.nextLine();

            boolean found = false;
            for(Exam exam :examList){
                if(exam.getSub().equalsIgnoreCase(modSub)){
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
        }
        if (!found) {
            System.out.println("Can't find "+ modSub +" subject!");
            }
        }  
    }else{
        System.out.println("Sorry, You didn't press D or M.");
        }
    }
/*
 * EFFECTS: Users can put their grade and it will calculate their average score.
 *          Also, it will give user their gpa.
 */
    public double averageScore(){
        double totalMark = 0;
        for(int count = 0 ; count < examList.size(); count++) {
            System.out.println("Enter the mark you got: ");
            double actualMark = scanner.nextDouble();
            totalMark = totalMark + actualMark;        
        }
        double averageScore = totalMark / examList.size(); 
        return averageScore;
        }

    public String gpa(){
        String gpa ="null";
        if(averageScore() >= 90){
            gpa = "A+";
        }else if(averageScore() >= 80 && averageScore() < 70){
            gpa = "B+";
        }else if(averageScore() >= 70 && averageScore() < 60){
            gpa = "C+";
        }else if(averageScore() >= 60 && averageScore() < 50){
            gpa = "D";
        }else if(averageScore() < 50){
            gpa = "F";
        }return gpa;
    }
    /*
     * MODIFES: this
     * EFFECTS: close the application.
     */
    public void quitApp(){
        System.out.println("Thank you for using our app.");
        System.out.println("Fake it till make it!");
        this.runningApp = false;
    }

    public void line(){
        System.out.println(("----------------------------"));
    }


}
