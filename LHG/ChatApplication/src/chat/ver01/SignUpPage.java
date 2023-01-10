package chat.ver01;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
public class SignUpPage {
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel label;
    private static JButton submit, cancel;
    private static JTextField id;
    private static JPasswordField passwd;

    public SignUpPage(){
        frame = new JFrame();
        frame.setSize(350, 200);
        frame.setTitle("회원가입");
        // 현재 창 닫기
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        label = new JLabel("아이디");
        label.setBounds(10, 20, 80, 25);
        panel.add(label);

        id = new JTextField();
        id.setBounds(80, 20, 150, 25);
        panel.add(id);

        label = new JLabel("비밀번호");
        label.setBounds(10, 50, 80, 25);
        panel.add(label);

        passwd = new JPasswordField();
        passwd.setBounds(80, 50, 150, 25);
        panel.add(passwd);

        submit = new JButton("제출");
        submit.setBounds(10, 110, 100, 25);
        panel.add(submit);

        cancel = new JButton("취소");
        cancel.setBounds(120, 110, 100, 25);
        panel.add(cancel);

        frame.setVisible(true);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("submit clicked");
                try {
                    TreeMap<String, String> userList = new TreeMap<String, String>();
                    File file = new File("/Users/ihangyeol/Desktop/j_study/LHG/db/test1.txt");
                    try{
                        FileReader fr = new FileReader(file);
                        BufferedReader reader = new BufferedReader(fr);
                        while(true){
                            String data = reader.readLine();
                            if(data==null) break;
                            String[] arr = data.split("/");
                            userList.put(arr[0], arr[1]);
                        }
                        // 리스트 중복값 확인
                        if(userList.containsKey(id.getText())){
                            System.out.println("아이디가 있습니다.");
                        } else {
                            userList.put(id.getText(), passwd.getText());
                            Set set = userList.entrySet();
                            Iterator iterator = set.iterator();
                            // 라인 단위 출력을 위해 PrintStream 사용
                            FileWriter fw = new FileWriter(file);
                            PrintWriter pw = new PrintWriter(fw);
                            // 바이트 기반, 문자열 기반 둘 다 해도 되는데 뭔 차이?
//                                FileOutputStream fos = newFileWriter("./db/test1.txt");
//                                PrintStream ps = new PrintStream(fos);
                            // FileWriter fw = new FileWriter("./db/test1.txt");
                            // BufferedWriter writer = new BufferedWriter(fw);
                            while(iterator.hasNext()){
                                Object obj = iterator.next();
                                String str = obj.toString();
                                String[] arr = str.split("=");
                                // 추가로 입력받은 id, passwd를 포함하여 출력 스트림
                                pw.println(arr[0]+"/"+arr[1]);
                            }
                            pw.flush();
                            pw.close();
                            System.out.println("회원가입 성공");
                            frame.dispose();
                            new LoginPage();
                        }
                    } catch (FileNotFoundException f){
                        FileWriter fw = new FileWriter("/Users/ihangyeol/Desktop/j_study/LHG/db/test1.txt");
                        Writer wr = new BufferedWriter(fw);
                        userList.put(id.getText(), passwd.getText());
                        wr.write(id.getText() +"/");
                        wr.write(passwd.getText());
                        System.out.println(id.getText()+ "/" + passwd.getText());
                        wr.flush();
                        wr.close();
                        System.out.println("회원가입 성공");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("cancel clicked");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
    }
}
