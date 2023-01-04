package sec02.exam03;

public class PrintThread3 extends Thread{
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                System.out.println("실행 중");
                Thread.sleep(1);
            }
        } catch (InterruptedException e){}
        
        System.out.println("자원 정리");
        System.out.println("자원 종료");
    }
}
