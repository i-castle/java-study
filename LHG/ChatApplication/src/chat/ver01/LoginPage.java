package chat.ver01;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.TreeMap;

public class LoginPage{
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel idLabel, passLabel;
    private static JTextField idText;
    private static JPasswordField passwordText;
    private static JButton LoginBtn, SignUpBtn;
    private static JLabel success;
    public static void main(String[] args) {

        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);

        // cols, rows
        panel.setLayout(null);

        idLabel = new JLabel("아이디");
        idLabel.setBounds(10, 20, 80, 25);
        panel.add(idLabel);

        // TextField, 매개값은 길이
        idText = new JTextField(20);
        idText.setBounds(100, 20, 165, 25);
        panel.add(idText);

        passLabel = new JLabel("패스워드");
        passLabel.setBounds(10, 50,80,25);
        panel.add(passLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        LoginBtn = new JButton("로그인");
        LoginBtn.setBounds(10, 100, 80, 25);
//        LoginBtn.addActionListener(new GUILogin());
        panel.add(LoginBtn);

        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        SignUpBtn = new JButton("회원가입");
        SignUpBtn.setBounds(100, 100, 80, 25);
        panel.add(SignUpBtn);

        frame.setVisible(true);

        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 파일이 존재할 경우
                try {
                    TreeMap<String, String> userList = new TreeMap<String, String>();
                    File file = new File("./db/test1.txt");
                    FileReader fr = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fr);
                    while(true){
                        String data = reader.readLine();
                        if(data==null) break;
                        String[] arr = data.split("/");
                        String id = idText.getText();
                        String passwd = passwordText.getText();
                        System.out.println(id +" / "+passwd);
                        if(id.equals(arr[0]) && passwd.equals(arr[1])){
                            System.out.println("로그인 성공!");
                        } else if(id.equals(arr[0]) || passwd.equals(arr[1])){
                            System.out.println("아이디나 패스워드를 확인해주세요.");
                        } else {
                            System.out.println("아이디가 없습니다. 회원가입을 해주세요.");
                        }
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("파일 생성 필요");
                    JOptionPane.showMessageDialog(null, "회원가입 필요");
                } catch (IOException ex) {
                    System.out.println("파일 읽기 실패");
                }

            }
        });
        SignUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Click Sign Up btn");
                SignUpPage signUpPage = new SignUpPage();
            }
        });
    }
}
