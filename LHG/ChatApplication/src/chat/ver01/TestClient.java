package chat.ver01;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.TreeMap;

public class TestClient{
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel idLabel, passLabel, logStatusLabel;
    private static JTextField idText;
    private static JPasswordField passwordText;
    private static JButton LoginBtn, SignUpBtn;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;

    public boolean isLoginStatus() {
        return loginStatus;
    }

    private static boolean loginStatus = false;
    public void setLoginStatus(boolean loginStatus){
        this.loginStatus = loginStatus;
    }
    public static boolean getLoginStatus(){
        return loginStatus;
    }
    public TestClient(){
        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);

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

        SignUpBtn = new JButton("회원가입");
        SignUpBtn.setBounds(100, 100, 80, 25);
        panel.add(SignUpBtn);

        logStatusLabel = new JLabel("");
        logStatusLabel.setBounds(10, 80, 80, 25);
        panel.add(logStatusLabel);

        frame.setVisible(true);

        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginCheck();
            }
        });
        SignUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("회원가입 버튼 클릭");
                SignUpPage signUpPage = new SignUpPage();
                frame.dispose();
            }
        });
    }
    public TestClient(Socket socket, String username){
        try{
            this.socket = socket;
            this.username = username;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = idText.getText();
        } catch(IOException e){
            System.out.println("실패");
        }

    }
    public boolean loginCheck(){
        loginStatus = false;
        try {
            String path = "C:\\Users\\hg146\\OneDrive\\바탕 화면\\j_study\\LHG\\db\\test1.txt";
            TreeMap<String, String> userList = new TreeMap<String, String>();
            File file = new File(path);
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
                    loginStatus = true;
                    logStatusLabel.setText("로그인 되었습니다.");
                    break;
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
        return loginStatus;
    }
    public void sendMessage(){
        try{
            // clientHandler 생성자에서 readLine으로 기다림
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

             Scanner sc = new Scanner(System.in);
             while(socket.isConnected()){
                 String messageToSend = sc.nextLine();
                 bufferedWriter.write(username + ": " + messageToSend);
                 bufferedWriter.newLine();
                 bufferedWriter.flush();
             }
        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    // 브로드캐스트 메세지 수신
    public void listenForMessage(){
        // 메세지 수신을 위한 스레드
        // 객체 생성 후, 실행
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while(socket.isConnected()) {
                    try{
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket!=null) socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("반갑습니다.");

        TestClient client = new TestClient();
        Socket socket = null;
        while(true){
            if(getLoginStatus()==true){
                socket = new Socket("localhost",1234);
                TestClient client2 = new TestClient(socket, idText.getText());
                client2.listenForMessage();
                client2.sendMessage();
                break;
            }
            try {
                System.out.println(getLoginStatus());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

//                            client.sendMessage();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    if(getLoginStatus()==true){
//                        Socket socket = null;
//                        try {
//                            socket = new Socket("localhost",1234);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        TestClient client = new TestClient(socket, idText.getText());
//                        try {
//                            Thread.sleep(500);
//                            client.listenForMessage();
//                            client.sendMessage();
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                        break;
//                    } else{
//                        System.out.println(getLoginStatus());
//                    }
//                }
//            }
//        });
//        thread.start();
    }


}
