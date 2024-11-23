package ui;

import model.Exam;
import model.ExamControl;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.util.List;

public class GUI extends JFrame implements ActionListener {
    private static final String EXAMLIST_FILE = "./data/examListings.txt";
    private ExamControl examControl;
    private JsonWriter writer;

    private JPanel mainMenu;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;

    private JPanel examListPanel;
    private JLabel listings;

    private JPanel addExamPage;
    private JButton addExam;
    private JTextField text1;
    private JTextField text2;
    private JTextField text3;
    private JTextField text4;
    private JTextField text5;
    private JLabel subject;
    private JLabel date;
    private JLabel location;
    private JLabel goalMark;
    private JLabel time;

    private JPanel addActaulMakrPage;
    private JButton addActualMark;
    private JTextField text6;
    private JLabel actualMark;

    private JPanel modifypage;
    private JButton removeExam;

    private JTextField subjectInputField;
    private JLabel subjectLabel;

    public GUI() {
        super("Exam List App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 500));

        examControl = new ExamControl(); // ++

        mainPage();
        makeEaxmListPanel();
        addExampanel();
        addActualMarkpanel();

        JLabel startMsg = new JLabel("Plan you exam here!");
        addLabel(startMsg);
        JLabel imageMain = new JLabel();
        addImage(imageMain);


        mainButtonsInitialize(); // 메인페이지 8개 버튼 이름
        addButtons(button1, button2, button3, button4, button5, button7, button6); // 메인페이지 8개 버튼
        addActionToButton(); // 메인페이지 8개 버튼에 대한 커맨더

        mainMenu.setVisible(true); // 페이지가 보이게 함
    }

    public void addImage(JLabel image){
        ImageIcon icon = new ImageIcon("./data/exam.png");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(600,300, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg); 
        image.setIcon(scaledIcon);
        image.setHorizontalAlignment(JLabel.CENTER); // 이미지 가운데 정렬
        mainMenu.add(image, BorderLayout.CENTER);
    }

    //EFFECTS: Create buttons which are in the main page
    public void mainButtonsInitialize() { // 메인페이지 8개 버튼 이름들
        button1 = new JButton("Current Exam List");
        button2 = new JButton("Add Exam schedule");
        button3 = new JButton("Modify Exam");
        button4 = new JButton("Add actual mark");
        button5 = new JButton("Calculate GPA");
        button6 = new JButton("Save file");
        button7 = new JButton("Load file");
        button8 = new JButton("Quit the app");
    }

    // ----------------여기서부터과목지우고수정하는페이지---------------
    // EFFECTS: Setting of modify page. Close main page and open modify page
    public void modifypanelSet() {
        if (modifypage == null) {
            modifypanel();
        }

        add(modifypage);
        modifypage.setVisible(true);
        mainMenu.setVisible(false);
        revalidate(); // 레이아웃 갱신
        repaint();
    }
    // MODIFES: this
    // EFFECTS: Create modify panel. Divied two pannels each has buttons
    public void modifypanel() {        
        modifypage = new JPanel();
        modifypage.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        createTopButtons(topPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        createBottomButtons(bottomPanel);

        modifypage.add(topPanel, BorderLayout.NORTH); // 상단 버튼
        modifypage.add(bottomPanel, BorderLayout.CENTER); // 하단 입력 칸
    }
    
    // MODIFES: this
    // EFFECTS: Create remove buttons and put them to top panel.
    public void createTopButtons(JPanel topPanel) {
        JButton returnToMain = new JButton("Main menu");
        returnToMain.setActionCommand("Main menu");
        returnToMain.addActionListener(this);

        removeExam = new JButton("Remove"); // 버튼 만들기
        removeExam.setActionCommand("Remove");
        removeExam.addActionListener(this);

        addButtonBlack(returnToMain, topPanel);
        addButtonBlack(removeExam, topPanel);
    }

    // MODIFES: this
    // EFFECTS: Create the box which user can enter the subject wants to remove
    public void createBottomButtons(JPanel bottomPanel) {
        subject = new JLabel("Subject: ");
        text1 = new JTextField(10);

        modfiyInforSetting(); // 오른쪽 입력하는 칸

        bottomPanel.add(subject);
        bottomPanel.add(text1);
    }
    // MODIFES: this
    // EFFECTS: set letter's font and size 
    public void modfiyInforSetting() { // 사용자 입력칸 세팅값
        subject.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        text1.setMaximumSize(new Dimension(1200, 400));
    }
    // -------여기까지과목을지우고수정하는메서드-----------

    // ------여기서부터과목을추가하는메서드시작------------
    // MODIFES: this
    // EFFECTS: add the page which can add information about exam
    public void addExampanelSet() {
        add(addExamPage);
        addExamPage.setVisible(true);
        mainMenu.setVisible(false);
        text1.setText("");
        revalidate(); // 레이아웃 갱신
        repaint();
    }
    // MODIFES: this
    // EFFECTS: create the adding panel and the mian button 
    public void addExampanel() {
        addExamPage = new JPanel(new GridLayout(0, 2));
        JButton returnToMain = new JButton("Main menu");
        returnToMain.setActionCommand("Main menu");
        returnToMain.addActionListener(this);

        addButtonBlack(returnToMain, addExamPage);

        createAddExamPage();
        addLabelsToListings();
    }

    // MODIFES: this
    // EFFECTS: create add button and boxs which user can add exam information
    public void createAddExamPage() {
        addExam = new JButton("Add"); // 버튼 만들기
        addExam.setActionCommand("Add");
        addExam.addActionListener(this);

        subject = new JLabel("Subject: ");
        text1 = new JTextField(10);

        date = new JLabel("Date (YY/MM/DD): ");
        text2 = new JTextField(10);

        time = new JLabel("Time (24-hour clock): ");
        text3 = new JTextField(10);

        location = new JLabel("Location:");
        text4 = new JTextField(10);

        goalMark = new JLabel("Goal mark: ");
        text5 = new JTextField(10);

        examInforSetting(); // 오른쪽 입력하는 칸
    }

    // MODIFES: this
    // EFFECTS: Set font and size of letters
    public void examInforSetting() { // 사용자 입력칸 세팅값
        addExam.setBackground(Color.BLACK);
        addExam.setForeground(Color.WHITE);
        addExam.setFont(new Font("TimesNewRoman", Font.BOLD, 12));

        subject.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        date.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        time.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        location.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        goalMark.setFont(new Font("TimesNewRoman", Font.BOLD, 24));

        text1.setMaximumSize(new Dimension(1200, 400));
        text2.setMaximumSize(new Dimension(1200, 400));
        text3.setMaximumSize(new Dimension(1200, 400));
        text4.setMaximumSize(new Dimension(1200, 400));
        text5.setMaximumSize(new Dimension(1200, 400));
    }

    // EFFECTS: add buttons to tha add exam page
    public void addLabelsToListings() {
        // 리스트 페이지에 입력된 정보 넣기
        addExamPage.add(addExam);
        addExamPage.add(subject);
        addExamPage.add(text1);
        addExamPage.add(date);
        addExamPage.add(text2);
        addExamPage.add(time);
        addExamPage.add(text3);
        addExamPage.add(location);
        addExamPage.add(text4);
        addExamPage.add(goalMark);
        addExamPage.add(text5);
    }
    // --------여기까지새로운시험을추가하는것------------------

    // --------시험점수를입력하는메서드------------------------

    // EFFECTS: add the page which user can put their actual mark
    public void addActualMarkSet() {
        add(addActaulMakrPage);
        addActaulMakrPage.setVisible(true);
        mainMenu.setVisible(false);
        revalidate(); // 레이아웃 갱신
        repaint();
    }

    // MODIFES: this
    // EFFECTS: create the panel and buttons
    public void addActualMarkpanel() {
        addActaulMakrPage = new JPanel(new GridLayout(0, 2));
        JButton returnToMain = new JButton("Main menu");
        returnToMain.addActionListener(this);
        returnToMain.setActionCommand("Main menu");

        addButtonBlack(returnToMain, addActaulMakrPage);

        createActaulMakrPage();
        addLabelsToListingsActualMark();
    }

    // MODIFES: this
    // EFFECTS: create the page which can add user's actual mark. If user enter invalid number, throw the error msg
    public void createActaulMakrPage() {
        addActualMark = new JButton("Add Actual Mark"); // 버튼 만들기
        addActualMark.setActionCommand("Add Actual Mark");
        addActualMark.addActionListener(e -> {
            String inputSubject = subjectInputField.getText();
            int actualMarkValue;

            try {
                actualMarkValue = Integer.parseInt(text6.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid actual mark.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (examControl != null) {
                examControl.addActualMark(inputSubject, actualMarkValue);
            } else {
                JOptionPane.showMessageDialog(this, "Subject not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            subjectInputField.setText("");
            text6.setText("");
        });
        subjectLabel = new JLabel("Subject: ");
        subjectInputField = new JTextField(10);

        actualMark = new JLabel("Actual Mark: ");
        text6 = new JTextField(10);

        addAcutalMarkButtonsSetting(); // 오른쪽 입력하는 칸
    }

    // MODIFES: this
    // EFFECTS: Set the font,size and colour of letters
    public void addAcutalMarkButtonsSetting() {
        addActualMark.setBackground(Color.BLACK);
        addActualMark.setForeground(Color.WHITE);
        addActualMark.setFont(new Font("TimesNewRoman", Font.BOLD, 24));

        subjectInputField.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        actualMark.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        text6.setMaximumSize(new Dimension(1200, 400));
    }

    // MODIFES: this
    // EFFECTS: add buttons to the page
    public void addLabelsToListingsActualMark() {
        addActaulMakrPage.add(addActualMark);
        addActaulMakrPage.add(subjectLabel);
        addActaulMakrPage.add(subjectInputField);

        addActaulMakrPage.add(actualMark);
        addActaulMakrPage.add(text6);
    }
    // --- add actual mark -----------------------------------------------------

    // MODFIES: this
    // EFFECTS: Create start msg text label and add to the main
    public void addLabel(JLabel label) { // 텍스트 입력시 기본정보
        label.setFont(new Font("TimesNewRoman", Font.BOLD, 50));
        mainMenu.add(label);
    }

    // --------------여기서부터가지고있는리스트만들기-------------------------------
    // MODIFES: this
    // EFFECTS: add current exam list page 
    public void curExamListSet() {
        makeEaxmListPanel();
        add(examListPanel); // carListingPanel을 화면에 추가함
        examListPanel.setVisible(true); // 사용자가 정보를 볼 수 있음
        mainMenu.setVisible(false); // main안보이게
        revalidate();
        repaint();
    }

    // MODIFES: this 
    // EFFECTS: create exam list panel and set the size and shape with scroll.
    public void makeEaxmListPanel() {
        examListPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (examControl == null) {
            listModel.addElement("No exams available.");
        } else if (examControl.getExamList().isEmpty()) {
            listModel.addElement("No exams available.");
        } else {
            for (Exam exam : examControl.getExamList()) {
                listModel.addElement(formatExamDetails(exam));
            }
        }
        JList<String> examJList = new JList<>(listModel);
        examJList.setCellRenderer(new DefaultListCellRenderer());
        JScrollPane scrollPane = new JScrollPane(examJList); // 스크롤 버튼을 만들기

        JButton mainMenuButton = new JButton("Main Menu"); // return 버튼 생성
        mainMenuButton.setActionCommand("Main menu"); // 버튼을 눌렀을때 동작을 말하는 것 같음
        mainMenuButton.addActionListener(this);

        examListPanel.add(scrollPane, BorderLayout.CENTER); // JList 추가
        examListPanel.add(mainMenuButton, BorderLayout.SOUTH); // 버튼 추가
    }
    
    //EFFECTS: replace input string to html letter
    private String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");  
    } 

    // EFFECTS: listting exam's information and trans the letter to html letter
    public String formatExamDetails(Exam exam) {
        return "<html>" + "Subject: " + escapeHtml(exam.getSub()) + "<br>" + "Date: "
                + escapeHtml(String.valueOf(exam.getDate())) + "<br>" + "Time: "
                + escapeHtml(String.valueOf(exam.getTime()))
                + "<br>" + "Location: "
                + escapeHtml(exam.getLocation()) + "<br>" + "Goal Mark: "
                + escapeHtml(String.valueOf(exam.getGoalMark())) + "<br>" + "Actual Mark: "
                + escapeHtml(String.valueOf(exam.getActMark())) + "<br>--------------------</html>";
    }
    // ----------------------------------------------------------------------

    // --------------------Main---------------------------------------------

    // MODFIES: this 
    // EFFECTS: Make the main menu and set the colour.
    public void mainPage() {
        mainMenu = new JPanel(); // 메인 메뉴 만들고 배경색 설정
        mainMenu.setBackground(Color.darkGray);
        add(mainMenu);
        listings = new JLabel(); // ??
        listings.setText("No Listings available"); 
    }

    // EFFECTS: Set each button run their job
    public void addActionToButton() {
        button1.addActionListener(this);
        button1.setActionCommand("Current Exam List");

        button2.addActionListener(this);
        button2.setActionCommand("Add Exam schedule");

        button3.addActionListener(this);
        button3.setActionCommand("Modify Exam");

        button4.addActionListener(this);
        button4.setActionCommand("Add actual mark");

        button5.addActionListener(this);
        button5.setActionCommand("Calculate GPA");

        button6.addActionListener(this);
        button6.setActionCommand("Save file");

        button7.addActionListener(this);
        button7.setActionCommand("Load file");

        button8.addActionListener(this);
        button8.setActionCommand("Quit the app");
    }

    // EFFECTS: if user click the button, go to the right method
    public void actionPerformed(ActionEvent action) {
        // 사용자가 버튼을 클릭하면 화면이 전환되게 하는 메서드
        if (action.getActionCommand().equals("Current Exam List")) { // 0
            curExamListSet();
        } else if (action.getActionCommand().equals("Add Exam schedule")) { // 0
            addExampanelSet();
        } else if (action.getActionCommand().equals("Modify Exam")) { // 0
            modifypanelSet();
        } else if (action.getActionCommand().equals("Add actual mark")) { // 0
            addActualMarkSet();
        } else if (action.getActionCommand().equals("Calculate GPA")) { //0
            calculateGpa();
        } else if (action.getActionCommand().equals("Save file")) { // 0
            saveFile();
        } else if (action.getActionCommand().equals("Load file")) { // 0
            loadFile();
        } else if (action.getActionCommand().equals("Main menu")) {// 0
            returnToMainMenu();
        } else if (action.getActionCommand().equals("Add")) { // 0
            addExamToList();
        } else if (action.getActionCommand().equals("Remove")) { // 0
            removeExmaToList();
        }
    }

    // EFFECTS: calculate use's gpa with actual mark. if there's no exam, throw the error msg
    public void calculateGpa() {
        if (examControl == null || examControl.getExamList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No exams available to calculate.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String gpa = examControl.gpa();
        JOptionPane.showMessageDialog(this, "Your GPA is: " + gpa, "GPA Calculation", JOptionPane.INFORMATION_MESSAGE);
    }


    // EFFECTS: remove the exam from exam list
    public void removeExmaToList() {
        String subject = text1.getText().trim();
        if (examControl == null || examControl.getExamList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No exams available to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        examControl.deleteSubject(subject); 
        text1.setText(""); 
        System.out.println("After Clearing text1: " + text1.getText()); // Ok
        
    }

    // EFFECTS: Add exam information to the exam list
    public void addExamToList() {
        String subject = text1.getText();
        System.out.println("text1: " + text1.getText()); // not working...
        int date = Integer.parseInt(text2.getText());
        int time = Integer.parseInt(text3.getText());
        String location = text4.getText();
        int goalMark = Integer.parseInt(text5.getText());

        if (examControl == null) {
            examControl = new ExamControl(); // 초기화
        }

        examControl.addSubject(subject, date, time, location, goalMark);

        revalidate(); // 레이아웃 갱신
        repaint();

        text1.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");

        
    }

    // EFFECTS: add buttons to main menu at once
    public void addButtons(JButton button1, JButton button2, JButton button3, JButton button4, JButton button5,
            JButton button6, JButton button7) {
        addButtonWithe(button1, mainMenu);
        addButtonWithe(button2, mainMenu);
        addButtonWithe(button3, mainMenu);
        addButtonWithe(button4, mainMenu);
        addButtonWithe(button5, mainMenu);
        addButtonWithe(button6, mainMenu);
        addButtonWithe(button7, mainMenu);
    }

    // MODIFES: this
    // EFFECTS: add the button and set the button's imformation
    public void addButtonWithe(JButton button1, JPanel panel) {
        button1.setFont(new Font("TimesNewRoman", Font.BOLD, 12));
        button1.setBackground(Color.white);
        panel.add(button1);
        pack(); // 프레임 크기를 자동 조절
        setLocationRelativeTo(null); // 화면에 표시될 위치 설정 null > 화면 중앙에 위치
        setVisible(true); // 프레임을 화면에 표시
        setResizable(false); // 사용자가 프레임 크기를 조정할 수 없도록 설정
    }

    // MODIFES: this
    // EFFECTS: set the button which is using many times. Size, colour and font 
    public void addButtonBlack(JButton button1, JPanel panel) {
        button1.setFont(new Font("TimesNewRoman", Font.BOLD, 12));
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);
        panel.add(button1);
        pack(); // 프레임 크기를 자동 조절
        setLocationRelativeTo(null); // 화면에 표시될 위치 설정 null > 화면 중앙에 위치
        setVisible(true); // 프레임을 화면에 표시
        setResizable(false); // 사용자가 프레임 크기를 조정할 수 없도록 설정
    }
    // MODIFES: this
    // EFFECTS: save file as data file 
    public void saveFile(){
        if (examControl == null || examControl.getExamList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No exams available to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        writer = new JsonWriter(EXAMLIST_FILE);
        try{
            writer.open();
            writer.write(examControl.toJson());
            writer.close();
            JOptionPane.showMessageDialog(this, "Exam list saved successfully!", "Save File", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(this, "Error saving to file.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }
    // MODIFES: this
    // EFFECTS: load file from recent save file
    public void loadFile(){
        if(examControl == null){
            examControl = new ExamControl();
        }

        JsonReader reader = new JsonReader(EXAMLIST_FILE);
        try{
            List<Exam> loadedExams = reader.read();
            examControl.getExamList().clear();
            examControl.getExamList().addAll(loadedExams);
            JOptionPane.showMessageDialog(this, "Exam list loaded successfully!", "Load File", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, "Error loading file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: set pages invisible except for main menu
    public void returnToMainMenu() {

        if (mainMenu != null) {
            mainMenu.setVisible(true); // 메인 메뉴 활성화
        }

        // 다른 패널 숨기기 전에 null 체크
        if (examListPanel != null) {
            examListPanel.setVisible(false);
        }
        if (addExamPage != null) {
            addExamPage.setVisible(false);
        }
        if (addActaulMakrPage != null) {
            addActaulMakrPage.setVisible(false);
        }
        if (modifypage != null) {
            modifypage.setVisible(false);
        }

        // UI 갱신
        revalidate();
        repaint();
    }
}
