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

    public GUI(){
        super("Exam List App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 500));

        mainPage();
    }

    // EFFECTS: Make the main menu and set the colour.
    public void mainPage() {
        mainMenu = new JPanel(); // 메인 메뉴 만들고 배경색 설정
        mainMenu.setBackground(Color.darkGray);
        add(mainMenu);
        listings = new JLabel(); //??
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

    //EFFECTS: information about main button. Colour,size..ect
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
    }

    // EFFECTS: if user click the button, go to the right method
    public void actionPerformed(ActionEvent ae) { 
        // 사용자가 버튼을 클릭하면 화면이 전환되게 하는 메서드
        if (ae.getActionCommand().equals("Current Exam List")) { // 유저가 클릭한게 view ~와 같다면 
        
        }
    }
}
