package chat.ver01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = null;
        // 서버소켓
        // 특정 번호에 수신 대기
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(true){
            try{
                socket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while(true){
                    String msgFromClient = bufferedReader.readLine();
                    System.out.println("Client: "+msgFromClient);
                    bufferedWriter.write("MSG Received");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    if(msgFromClient.equalsIgnoreCase("종료")) break;
                }
                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedWriter.close();
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
