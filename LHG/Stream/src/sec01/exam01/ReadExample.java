// 바이트 입력 스트림
package sec01.exam01;

import java.io.FileInputStream;
import java.io.InputStream;

public class ReadExample {
    public static void main(String[] args) throws Exception{
        InputStream is = new FileInputStream("./Temp/test1.db");
        while(true){
            // 1byte씩 읽기
            int data = is.read();
            // data가 더 없을 경우, -1을 반환
            if(data==-1) break;
            System.out.println(data);
        }
        is.close();
    }
}
