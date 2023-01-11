package chat.ver01;

import java.io.*;
import java.util.ArrayList;
import java.net.Socket;

public class ClientHandler implements Runnable{
    // 인스턴스화한 모든 클라이언트 핸들러 개체의 정적 arraylist
    // 모든 클라이언트를 담고 클라이언트가 메시지를 보낼 때마다 클라이언트 배열 목록을 반복하고 각 클라이언트에 메세지를 보낼 수 있게함
    // 클래스의 각 객체가 아닌 클래스에 속하길 원하기 때문에 static
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    // 서버에서 전달되는 socket
    private Socket socket;
    // 메세지 읽음
    private BufferedReader br;
    private BufferedWriter bw;
    private String clientUsername;
    public ClientHandler(Socket socket){
        try{
            // 소켓은 서버 또는 클라이언트 핸들러와 우리 사이의 연결을 나타냄
            this.socket = socket;
            // 스트림을 문자 스트림으로 래핑
            // 서버에서 보내는 것
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 클라이언트 메세지 읽음
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 엔터키 누르면 새 줄을 통해 읽음
            this.clientUsername = br.readLine();
            // 클라이언트를 arraylist에 추가하여 클라이언트가 그룹에 참여하고 다른 사용자로부터 메세지 수신 가능
            clientHandlers.add(this);
            broadcastMessage(clientUsername + "가 입장하였습니다.");
        } catch (IOException e){
            closeEverything(socket, br, bw);
        }
    }
    @Override
    public void run(){
        String messageFromClient;
        System.out.println(clientUsername+"가 접속하였습니다.");
        while(socket.isConnected()){
            // 메세지를 받을 때까지 계속 기다림으로 스레드에서 실행
            try{
                // 스레드를 사용하여 애플리케이션의 나머지 부분이 이 코드로 인해 멈춰지지 않도록 함
                messageFromClient = br.readLine();
                broadcastMessage(messageFromClient);
            } catch(IOException e){
                closeEverything(socket, br, bw);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend){
        // arraylist 루프로 각 클라이언트에 메세지 전송
        // 각 클라이언트는 브로드캐스팅 되는 메세지를 기다리고 있을 것이므로 각 클라이언트를 보낼 떄
        // 각 클라이언트는 메세지를 기다리는 별도의 스레드를 가지게 됨
        for(ClientHandler clientHandler : clientHandlers){
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bw.write(messageToSend);
                    clientHandler.bw.newLine(); // 엔터키랑 동일, 더이상 나로부터 데이터 안 기다림
                    clientHandler.bw.flush();
                }
            } catch(IOException e){
                closeEverything(socket, br, bw);
            }
        }
    }
    // 클라이언트가 나갔을 경우, 해당 메소드를 통해 clientHandlers 리스트에서 클라이언트를 remove
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER:"+clientUsername+"has left the chat!");
    }

    // try-catch 대신 사용, 클라이언트를 제거하고 소켓, 입출력스트림을 모두 닫음
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket!=null) socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
