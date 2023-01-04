package sec01.exam01;

// Runnable은 작업 스레드가 실행할 수 있는 코드를 가지고 있는 객체ㅔ
// 인터페이스 타입이라 구현 객체를 만들어 대입

import java.awt.*;
public class BeepTask implements Runnable{
    @Override
    public void run() {
        // thread 실행 코드
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        for(int i=0; i<5; i++){
            // beep음 발생
            toolkit.beep();
            try{Thread.sleep(500);}catch (Exception e){}
        }
    }
}

