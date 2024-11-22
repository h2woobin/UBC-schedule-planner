package ui;

import model.Exam;
import model.ExamControl;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.io.UnsupportedEncodingException; 
import java.util.List;

public class GUI extends JFrame implements ActionListener {
    private static final String EXAMLIST_FILE = "./data/examListings.txt";
    private Exam exam;
    private ExamControl examControl;
    private List<Exam> examList = new ArrayList<>();

    private JPanel mainMenu;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;

    private JPanel eaxmListPanel;
    private JLabel listings;
    private boolean listed;

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

        mainButtonsInitialize(); // 메인페이지 8개 버튼 이름
        addButtons(button1, button2, button3, button4, button5, button7, button6); // 메인페이지 8개 버튼
        addActionToButton(); // 메인페이지 8개 버튼에 대한 커맨더

        mainMenu.setVisible(true); // 페이지가 보이게 함
    }

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

    public void createTopButtons(JPanel topPanel) {
        JButton returnToMain = new JButton("Main menu");
        returnToMain.setActionCommand("Main menu");
        returnToMain.addActionListener(this);

        // modifyExam = new JButton("Modify"); // 버튼 만들기
        // modifyExam.setActionCommand("Modify");
        // modifyExam.addActionListener(this);

        removeExam = new JButton("Remove"); // 버튼 만들기
        removeExam.setActionCommand("Remove");
        removeExam.addActionListener(this);

        addButtonBlack(returnToMain, topPanel);
        // addButtonBlack(modifyExam, topPanel);
        addButtonBlack(removeExam, topPanel);
    }

    public void createBottomButtons(JPanel bottomPanel) {
        subject = new JLabel("Subject: ");
        text1 = new JTextField(10);

        // date = new JLabel("Date (YY/MM/DD): ");
        // text2 = new JTextField(10);

        // time = new JLabel("Time (24-hour clock): ");
        // text3 = new JTextField(10);

        // location = new JLabel("Location:");
        // text4 = new JTextField(10);

        // goalMark = new JLabel("Goal mark: ");
        // text5 = new JTextField(10);

        modfiyInforSetting(); // 오른쪽 입력하는 칸

        bottomPanel.add(subject);
        bottomPanel.add(text1);
        // bottomPanel.add(date);
        // bottomPanel.add(text2);
        // bottomPanel.add(time);
        // bottomPanel.add(text3);
        // bottomPanel.add(location);
        // bottomPanel.add(text4);
        // bottomPanel.add(goalMark);
        // bottomPanel.add(text5);
    }

    public void modfiyInforSetting() { // 사용자 입력칸 세팅값
        subject.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        // date.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        // time.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        // location.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        // goalMark.setFont(new Font("TimesNewRoman", Font.BOLD, 24));

        text1.setMaximumSize(new Dimension(1200, 400));
        // text2.setMaximumSize(new Dimension(1200, 400));
        // text3.setMaximumSize(new Dimension(1200, 400));
        // text4.setMaximumSize(new Dimension(1200, 400));
        // text5.setMaximumSize(new Dimension(1200, 400));
    }
    // ------------여기까지과목을지우고수정하는메서드-------

    // ------여기서부터과목을추가하는메서드시작------------
    public void addExampanelSet() {
        add(addExamPage);
        addExamPage.setVisible(true);
        mainMenu.setVisible(false);
        revalidate(); // 레이아웃 갱신
        repaint();
    }

    public void addExampanel() {
        addExamPage = new JPanel(new GridLayout(0, 2));
        JButton returnToMain = new JButton("Main menu");
        returnToMain.setActionCommand("Main menu");
        returnToMain.addActionListener(this);

        addButtonBlack(returnToMain, addExamPage);

        createAddExamPage();
        addLabelsToListings();
    }

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
    public void addActualMarkSet() {
        add(addActaulMakrPage);
        addActaulMakrPage.setVisible(true);
        mainMenu.setVisible(false);
        revalidate(); // 레이아웃 갱신
        repaint();
    }

    public void addActualMarkpanel() {
        addActaulMakrPage = new JPanel(new GridLayout(0, 2));
        JButton returnToMain = new JButton("Main menu");
        returnToMain.addActionListener(this);
        returnToMain.setActionCommand("Main menu");

        addButtonBlack(returnToMain, addActaulMakrPage);

        createActaulMakrPage();
        addLabelsToListingsActualMark();
    }

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

    public void addAcutalMarkButtonsSetting() {
        addActualMark.setBackground(Color.BLACK);
        addActualMark.setForeground(Color.WHITE);
        addActualMark.setFont(new Font("TimesNewRoman", Font.BOLD, 24));

        subjectInputField.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        actualMark.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        text6.setMaximumSize(new Dimension(1200, 400));
    }

    public void addLabelsToListingsActualMark() {
        addActaulMakrPage.add(addActualMark);
        addActaulMakrPage.add(subjectLabel);
        addActaulMakrPage.add(subjectInputField);

        addActaulMakrPage.add(actualMark);
        addActaulMakrPage.add(text6);
    }
    // --- add actual mark -----------------------------------------------------

    // Create start msg text label and add to the main
    public void addLabel(JLabel label) { // 텍스트 입력시 기본정보
        label.setFont(new Font("TimesNewRoman", Font.BOLD, 50));
        mainMenu.add(label);
    }

    // ----------------------------------------------------------------------
    public void curExamListSet() {
        makeEaxmListPanel();
        add(eaxmListPanel); // carListingPanel을 화면에 추가함
        eaxmListPanel.setVisible(true); // 사용자가 정보를 볼 수 있음
        mainMenu.setVisible(false); // main안보이게
        revalidate();
        repaint();
    }

    // EFFECTS: create exam list panel and set the size and shape with scroll.
    public void makeEaxmListPanel() {
        eaxmListPanel = new JPanel(new BorderLayout());
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

        eaxmListPanel.add(scrollPane, BorderLayout.CENTER); // JList 추가
        eaxmListPanel.add(mainMenuButton, BorderLayout.SOUTH); // 버튼 추가
    }

    private String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

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

    // --------------------Main--------------------------------------------------
    // EFFECTS: Make the main menu and set the colour.
    public void mainPage() {
        mainMenu = new JPanel(); // 메인 메뉴 만들고 배경색 설정
        mainMenu.setBackground(Color.darkGray);
        add(mainMenu);
        listings = new JLabel(); // ??
        listings.setText("No Listings available");
    }

    // MODIFIES: this
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
        } else if (action.getActionCommand().equals("Save file")) { // X
            // saveExam();
        } else if (action.getActionCommand().equals("Load file")) { // X

        } else if (action.getActionCommand().equals("Main menu")) {// 0
            returnToMainMenu();
        } else if (action.getActionCommand().equals("Add")) { // 0
            addExamToList();
        } else if (action.getActionCommand().equals("Remove")) { // 0
            removeExmaToList();
        }
    }

    public void calculateGpa() {
        if (examControl == null || examControl.getExamList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No exams available to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String gpa = examControl.gpa();
        JOptionPane.showMessageDialog(this, "Your GPA is: " + gpa, "GPA Calculation", JOptionPane.INFORMATION_MESSAGE);
    }

    public void removeExmaToList() {
        String subject = text1.getText();

        if (examControl == null || examControl.getExamList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No exams available to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        examControl.deleteSubject(subject);

        text1.setText("");
    }

    public void addExamToList() {
        String subject = text1.getText();
        int date = Integer.parseInt(text2.getText());
        int time = Integer.parseInt(text3.getText());
        String location = text4.getText();
        int goalMark = Integer.parseInt(text5.getText());

        if (examControl == null) {
            examControl = new ExamControl(); // 초기화
        }

        examControl.addSubject(subject, date, time, location, goalMark);

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

    public void returnToMainMenu() {

        if (mainMenu != null) {
            mainMenu.setVisible(true); // 메인 메뉴 활성화
        }

        // 다른 패널 숨기기 전에 null 체크
        if (eaxmListPanel != null) {
            eaxmListPanel.setVisible(false);
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
