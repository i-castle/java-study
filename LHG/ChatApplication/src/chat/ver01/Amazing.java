package chat.ver01;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Amazing extends JFrame {

    BufferedImage img = null;
    JTextField loginTextField;
    JPasswordField passwordField;
    JButton bt;

    // 메인
    public static void main(String[] args) {
        new Amazing();
    }

    // 생성자
    public Amazing() {
        setTitle("로그인 테스트");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 레이아웃 설정
        setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1000, 500);
        layeredPane.setLayout(null);

        // 패널1
        // 이미지 받아오기
        try {
            img = ImageIO.read(new File("LHG/ChatApplication/img/login.jpeg"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패");
            System.exit(0);
        }

        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 1600, 900);


        // 로그인 필드
        loginTextField = new JTextField(15);
        loginTextField.setBounds(731, 399, 280, 30);
        layeredPane.add(loginTextField);
        loginTextField.setOpaque(false);
        loginTextField.setForeground(Color.green);
        loginTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        // 패스워드
        passwordField = new JPasswordField(15);
        passwordField.setBounds(731, 529, 280, 30);
        passwordField.setOpaque(false);
        passwordField.setForeground(Color.green);
        passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        layeredPane.add(passwordField);

        // 로그인버튼 추가
        bt = new JButton(new ImageIcon("img/btLogin_hud.png"));
        bt.setBounds(755, 689, 104, 48);

        // 버튼 투명처리
        bt.setBorderPainted(false);
        bt.setFocusPainted(false);
        bt.setContentAreaFilled(false);

        layeredPane.add(bt);

        // 마지막 추가들
        layeredPane.add(panel);
        add(layeredPane);
        setVisible(true);
    }

    class MyPanel extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }
    }

}