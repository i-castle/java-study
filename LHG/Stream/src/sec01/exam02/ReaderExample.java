// 문자 입력 스트림
package sec01.exam02;

import java.io.FileReader;
import java.io.Reader;

public class ReaderExample {
    public static void main(String[] args) throws Exception{
        Reader rd = new FileReader("./Temp/test5.txt");

        while(true){
            // 1개의 문자(2byte)를 읽고 int(4byte) 타입으로 리턴함
            int data = rd.read();
            if(data==-1) break;
            // 리턴받은 int를 char로 캐스팅하여 문자를 얻을 수 있음
            System.out.println((char) data);
        }
        rd.close();
    }
}
