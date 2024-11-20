package ui;

import model.Exam;
import model.ExamControl;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class GUI extends JFrame implements ActionListener {
    private static final String EXAMLIST_FILE = "./data/examListings.txt";
    private Exam exam;
    private ExamControl examControl;

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

    public GUI() {
        super("Exam List App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 500));

        mainPage();
        makeEaxmListPanel();
        addExampanel();
        addActualMarkpanel();

        JLabel startMsg = new JLabel("Plan you exam here!");
        addLabel(startMsg);

        mainButtonsInitialize();
        addButtons(button1, button2, button3, button4, button5, button7, button6);
        addActionToButton();

        mainMenu.setVisible(true);
    }

    // --- add list
    public void addExampanel() {
        addExamPage = new JPanel(new GridLayout(0, 2));
        JButton returnToMain = new JButton("Main meun");
        returnToMain.setActionCommand("Main meun");
        returnToMain.addActionListener(this);
        addMenuButton(returnToMain, addExamPage);
    }

    public void addExampanelSet() {
        add(addExamPage);
        addExamPage.setVisible(true);
        mainMenu.setVisible(false);
        addExamPage.setVisible(false);
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

        listed = false;
        addExamButtonsSetting(); // 오른쪽 입력하는 칸
    }
    // --- add list

    // --- add actual mark
    public void addActualMarkpanel() {
        addActaulMakrPage = new JPanel(new GridLayout(0, 2));
        JButton returnToMain = new JButton("Main meun");
        returnToMain.setActionCommand("Main meun");
        returnToMain.addActionListener(this);
        addMenuButton(returnToMain, addActaulMakrPage);
    }

    public void addActualMarkSet() {
        add(addActaulMakrPage);
        addActaulMakrPage.setVisible(true);
        mainMenu.setVisible(false);
        addActaulMakrPage.setVisible(false);
    }

    public void createActaulMakrPage() {
        addActualMark = new JButton("Add"); // 버튼 만들기
        addActualMark.setActionCommand("Add");
        addActualMark.addActionListener(this);

        actualMark = new JLabel("Actual Mark: ");
        text6 = new JTextField(10);

        // listed = false;
        addExamButtonsSetting(); // 오른쪽 입력하는 칸
    }
    // --- add actual mark

    public void addExamButtonsSetting() {
        addActualMark.setBackground(Color.BLACK);
        addActualMark.setForeground(Color.WHITE);

        actualMark.setFont(new Font("TimesNewRoman", Font.BOLD, 24));

        text6.setMaximumSize(new Dimension(1200, 400));
    }

    // Create start msg text label and add to the main
    public void addLabel(JLabel label) { // 텍스트 입력시 기본정보
        label.setFont(new Font("TimesNewRoman", Font.BOLD, 50));
        mainMenu.add(label);
    }

    // EFFECTS: Make the main menu and set the colour.
    public void mainPage() {
        mainMenu = new JPanel(); // 메인 메뉴 만들고 배경색 설정
        mainMenu.setBackground(Color.darkGray);
        add(mainMenu);
        listings = new JLabel(); // ??
        listings.setText("No Listings available");
    }

    // EFFECTS: create exam list panel and set the size and shape with scroll.
    public void makeEaxmListPanel() {
        eaxmListPanel = new JPanel(new GridLayout(2, 1)); // 실제 2:1 패널 만든듯
        JScrollPane scroll = new JScrollPane(listings, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 스크롤 버튼을 만들기

        JButton mainMenuButton = new JButton("Main Menu"); // return 버튼 생성
        mainMenuButton.setActionCommand("Main menu"); // 버튼을 눌렀을때 동작을 말하는 것 같음
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, eaxmListPanel); // 버튼 추가

        listings.setFont(new Font("TimesNewRoman", Font.BOLD, 12)); // 글시체 이게 list 같음
        eaxmListPanel.add(scroll);
    }

    // EFFECTS: information about main button. Colour,size..ect
    public void addMenuButton(JButton button1, JPanel panel) { // 메인버튼에 대한 정보
        button1.setFont(new Font("TimesNewRoman", Font.BOLD, 12));
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.white);
        panel.add(button1);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: Set each button run their job
    public void addActionToButton() {
        // 엑션 버튼 커맨더
        button1.addActionListener(this);
        button1.setActionCommand("Current Exam List");
        button1.addActionListener(this);
        button1.setActionCommand("Add Exam schedule");
        button1.addActionListener(this);
        button1.setActionCommand("Modify Exam");
        button1.addActionListener(this);
        button1.setActionCommand("Add actual mark");
        button1.addActionListener(this);
        button1.setActionCommand("Calculate GPA");
        button1.addActionListener(this);
        button1.setActionCommand("Svae file");
        button1.addActionListener(this);
        button1.setActionCommand("Load file");
        button1.addActionListener(this);
        button1.setActionCommand("Quit the app");
    }

    // EFFECTS: if user click the button, go to the right method
    public void actionPerformed(ActionEvent action) {
        // 사용자가 버튼을 클릭하면 화면이 전환되게 하는 메서드
        if (action.getActionCommand().equals("Current Exam List")) { // 유저가 클릭한게 view ~와 같다면
            curExamListSet();
        } else if (action.getActionCommand().equals("Add Exam schedule")) {
            addExampanelSet();
        } else if (action.getActionCommand().equals("Modify Exam")) {

        } else if (action.getActionCommand().equals("Add actual mark")) {
            addActualMarkSet();
        } else if (action.getActionCommand().equals("Calculate GPA")) {

        } else if (action.getActionCommand().equals("Svae file")) {

        } else if (action.getActionCommand().equals("Load file")) {

        } else if (action.getActionCommand().equals("Quit the app")) {

        }
    }

    // EFFECTS: Make buttons which are in the main panel and labels them
    public void mainButtonsInitialize() {
        button1 = new JButton("Current Exam List");
        button2 = new JButton("Add Your Exam");
        button3 = new JButton("Modify Your Exam");
        button4 = new JButton("Add Your Actual Mark");
        button5 = new JButton("Save Exma to File");
        button7 = new JButton("Load File");
        button6 = new JButton("Quit Application");
    }

    public void curExamListSet() {
        add(eaxmListPanel); // carListingPanel을 화면에 추가함
        eaxmListPanel.setVisible(true); // 사용자가 정보를 볼 수 있음
        mainMenu.setVisible(false); // main안보이게
        eaxmListPanel.setVisible(false); // listingspage가 안보이게
    }

    // EFFECTS: add buttons to main menu at once
    public void addButtons(JButton button1, JButton button2, JButton button3, JButton button4, JButton button5,
            JButton button6, JButton button7) {
        addButton(button1, mainMenu);
        addButton(button2, mainMenu);
        addButton(button3, mainMenu);
        addButton(button4, mainMenu);
        addButton(button5, mainMenu);
        addButton(button6, mainMenu);
        addButton(button7, mainMenu);
    }

    // MODIFES: this
    // EFFECTS: add the button and set the button's imformation
    public void addButton(JButton button1, JPanel panel) {
        button1.setFont(new Font("TimesNewRoman", Font.BOLD, 12));
        button1.setBackground(Color.white);
        panel.add(button1);
        pack(); // 프레임 크기를 자동 조절
        setLocationRelativeTo(null); // 화면에 표시될 위치 설정 null > 화면 중앙에 위치
        setVisible(true); // 프레임을 화면에 표시
        setResizable(false); // 사용자가 프레임 크기를 조정할 수 없도록 설정
    }
}
