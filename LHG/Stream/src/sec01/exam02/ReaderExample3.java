// 문자 입력 스트림
package sec01.exam02;

import java.io.FileReader;
import java.io.Reader;

public class ReaderExample3 {
    public static void main(String[] args) throws Exception{
        Reader rd = new FileReader("./Temp/test7.txt");

        // 길이 100의 배열 생성
        char[] buffer = new char[5];

        while(true){
            // 3개 문자를 읽고, 길이가 5인 배열의 buffer[2], buffer[3], buffer[4]에 저장
            // 빈 공간에는 null이 저장
            int data = rd.read(buffer, 2, 3);
            if(data==-1) break;
            for(int i=0; i<data; i++) {
                // 읽은 문자 수만큼 반복하며 배열에 저장된 문자 출력
                System.out.println(buffer[i]);
            }
        }
        rd.close();
    }
}
