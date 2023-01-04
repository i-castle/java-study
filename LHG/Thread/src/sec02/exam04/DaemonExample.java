package sec02.exam04;

public class DaemonExample {
    public static void main(String[] args) {
        AutoSaveThread autoSaveThread = new AutoSaveThread();
        // AutoSaveThread를 daemon 스레드로 만듬
        autoSaveThread.setDaemon(true);
        autoSaveThread.start();
        
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}
        
        System.out.println("메인 스레드 종료");
    }
}
