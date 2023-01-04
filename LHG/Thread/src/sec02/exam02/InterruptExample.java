// interrupt() 메소드를 사용하여 run() 메소드를 정상 종료하는 방법
package sec02.exam02;

public class InterruptExample {
    public static void main(String[] args) {
        PrintThread2 printThread2 = new PrintThread2();
        printThread2.start();

        try{Thread.sleep(1000);}catch (InterruptedException e){}

        printThread2.interrupt();
    }
}
