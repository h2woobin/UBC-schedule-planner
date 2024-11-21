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
        // makeEaxmListPanel();
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

    // --- add actual mark -----------------------------------------------------
    public void addActualMarkpanel() {
        addActaulMakrPage = new JPanel(new GridLayout(0, 2));
        JButton returnToMain = new JButton("Main menu");
        returnToMain.setActionCommand("Main menu");
        returnToMain.addActionListener(this);
        addButtonWithe(returnToMain, addActaulMakrPage);
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
        addAcutalMarkButtonsSetting(); // 오른쪽 입력하는 칸
    }

    public void addAcutalMarkButtonsSetting() {
        addActualMark.setBackground(Color.BLACK);
        addActualMark.setForeground(Color.WHITE);

        actualMark.setFont(new Font("TimesNewRoman", Font.BOLD, 24));

        text6.setMaximumSize(new Dimension(1200, 400));
    }
    // --- add actual mark -----------------------------------------------------

    // Create start msg text label and add to the main
    public void addLabel(JLabel label) { // 텍스트 입력시 기본정보
        label.setFont(new Font("TimesNewRoman", Font.BOLD, 50));
        mainMenu.add(label);
    }

    // ----------------------------------------------------------------------
    // public void curExamListSet() {
    //     add(eaxmListPanel); // carListingPanel을 화면에 추가함
    //     eaxmListPanel.setVisible(true); // 사용자가 정보를 볼 수 있음
    //     mainMenu.setVisible(false); // main안보이게
    //     eaxmListPanel.setVisible(false); // listingspage가 안보이게
    // }

    // // EFFECTS: create exam list panel and set the size and shape with scroll.
    // public void makeEaxmListPanel() {
    //     eaxmListPanel = new JPanel(new GridLayout(2, 1)); // 실제 2:1 패널 만든듯
    //     JScrollPane scroll = new JScrollPane(listings, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    //             JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 스크롤 버튼을 만들기

    //     JButton mainMenuButton = new JButton("Main Menu"); // return 버튼 생성
    //     mainMenuButton.setActionCommand("Main menu"); // 버튼을 눌렀을때 동작을 말하는 것 같음
    //     mainMenuButton.addActionListener(this);
    //     addButtonWithe(mainMenuButton, eaxmListPanel); // 버튼 추가

    //     listings.setFont(new Font("TimesNewRoman", Font.BOLD, 12)); // 글시체 이게 list 같음
    //     eaxmListPanel.add(scroll);
    // }
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
        // 엑션 버튼 커맨더
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
        if (action.getActionCommand().equals("Current Exam List")) { // 유저가 클릭한게 view ~와 같다면
            // curExamListSet();
        } else if (action.getActionCommand().equals("Add Exam schedule")) {
            addExampanelSet();
        } else if (action.getActionCommand().equals("Modify Exam")) {

        } else if (action.getActionCommand().equals("Add actual mark")) {
            addActualMarkSet();
        } else if (action.getActionCommand().equals("Calculate GPA")) {

        } else if (action.getActionCommand().equals("Save file")) {
            // saveExam();
        } else if (action.getActionCommand().equals("Load file")) {

        } else if (action.getActionCommand().equals("Quit the app")) {
            System.exit(0);
        } else if (action.getActionCommand().equals("Main Menu")) {
            returnToMainMenu();
        } else if (action.getActionCommand().equals("Add")) {
            // addExmaToList();
        }
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
        mainMenu.setVisible(true);
        eaxmListPanel.setVisible(false);
        addActaulMakrPage.setVisible(false);
    }
}
