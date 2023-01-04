// Thread 하위 클래스로부터 생성
// Thread 클래스 생성 후, 상속
package sec01.exam02;

import java.awt.*;
public class BeepThread extends Thread{
    @Override
    public void run() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        for(int i=0; i<5; i++){
            toolkit.beep();
            try{Thread.sleep(500);}catch(Exception e){}
        }
    }
}
