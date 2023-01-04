package sec01.exam01;

import java.io.FileInputStream;
import java.io.InputStream;

public class ReadExample2 {
    public static void main(String[] args) throws Exception{
        InputStream is = new FileInputStream("./Temp/test2.db");

        // 길이 100 배열 생성
        byte[] buffer = new byte[100];

        while(true){
            // 배열 길이만큼 읽기, 한꺼번에 100byte를 읽고 배열에 저장
            // 즉, 파일에 읽을 바이트가 100개 이상 남아있을 경우에 한꺼번에 읽고, 그렇지 않다면 읽을 수 있는 바이트 수만큼 읽는다.
            int data = is.read(buffer);
            if(data==-1) break;
            for(int i=0; i<data; i++){
                System.out.println(buffer[i]);
            }
        }
        is.close();
    }
}
