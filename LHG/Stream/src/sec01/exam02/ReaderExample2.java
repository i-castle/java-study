// 문자 입력 스트림
package sec01.exam02;

import java.io.FileReader;
import java.io.Reader;

public class ReaderExample2 {
    public static void main(String[] args) throws Exception{
        Reader rd = new FileReader("./Temp/test6.txt");

        // 길이 100의 배열 생성
        char[] buffer = new char[100];

        while(true){
            int data = rd.read(buffer);
            if(data==-1) break;
            for(int i=0; i<data; i++) {
                // 읽은 문자 수만큼 반복하며 배열에 저장된 문자 출력
                System.out.println(buffer[i]);
            }
        }
        rd.close();
    }
}
