package chat.ver01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    private ServerSocket serverSocket;

    public TestServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("새로운 클라이언트 접속");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch(IOException e){
            System.out.println("접속 실패");
        }
    }
    public void closeServerSocket(){
        try{
            if(serverSocket!= null) serverSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        TestServer server = new TestServer(serverSocket);
        // 서버 소켓을 생성자로 보내고 서버를 실행하거나 서버를 지속적으로 실행하기 위해 서버 시작 메서드를 만듬
        server.startServer();
    }
}
// 스레드는 인스턴스가 별도의 스레드에 의해 실행되는 클래스에 구현, 연결을 처리할 새 스레드 생성이 중요
// 각각의 새로운 클라이언트는 한 번에 하나의 클라이언트만 처리가능
// 먼저 스레드 객체를 생성한 다음 다시 한 번 실행가능을 구현하는 클래스의 인스턴스인 객체를 전달해야함
// 무한 루프 등을 사용할 경우, 무한 루프를 빠져나갈 때까지 다른 작업들은 동작을 하지 못하는데 스레드로 처리를 하면 함께 동작이 가능
// Server 파일이 실행되면 startServer() 메소드를 통해 서버소켓이 클라이언트를 받을 대기를 하고 있고,
// ClientHandler에 담고 Thread로 실행을 한다.
// accept로 무한히 대기하고 있기 때문에 클라이언트가 접속할 때마다 Thread가 생성된다.