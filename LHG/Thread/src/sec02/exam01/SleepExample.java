// 스레드 안전하게 종료하기 위한 방법 (stop() 메소드 대체)
package sec02.exam01;

import sec01.exam04.MainThread;

public class SleepExample {
    public static void main(String[] args) {
        PrintThread printThread = new PrintThread();
        printThread.start();
        
        try{Thread.sleep(1000);}catch (InterruptedException e){}
        
        // 스레드 종료위해서 stop 필드를 true로 변경
        printThread.setStop(true);
        
    }
}
