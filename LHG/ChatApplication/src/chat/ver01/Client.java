package chat.ver01;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private JFrame frame;
    private JPanel panel;

    private static String id;
    private static boolean loginCheck;
    public static String getId() {return id;}
    public static void setId(String id) {
        Client.id = id;
    }
    public static boolean isLoginCheck() {
        return loginCheck;
    }
    public static void setLoginCheck(boolean loginCheck) {
        Client.loginCheck = loginCheck;
    }

    public static void main(String[] args) {
        if(isLoginCheck()==true) { 
            Socket socket = null;
            InputStreamReader isr = null;
            OutputStreamWriter outputStreamWriter = null;
            BufferedReader bufferedReader = null;
            BufferedWriter bufferedWriter = null;
            Scanner sc = new Scanner(System.in);
            try {
                socket = new Socket("localhost", 1234);
                isr = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedReader = new BufferedReader(isr);
                System.out.println(id+"님이 입장하셨습니다.");
                while (true) {
                    System.out.println("입력하세요:");
                    String msgToSend = sc.nextLine();
                    ApiFunc apiFunc = new ApiFunc(msgToSend);
                    bufferedWriter.write(id + ": " + msgToSend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    apiFunc.start();
                    System.out.println("Server: " + bufferedReader.readLine());
                    if (msgToSend.equalsIgnoreCase("종료")) break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (socket != null)
                        socket.close();
                    if (isr != null)
                        isr.close();
                    if (outputStreamWriter != null)
                        outputStreamWriter.close();
                    if (bufferedReader != null)
                        bufferedWriter.close();
                    if (bufferedWriter != null)
                        bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("잘못된 접근");
        }
    }
}
