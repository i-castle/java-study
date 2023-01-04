package sec01.exam01;

import java.io.FileOutputStream;
import java.io.OutputStream;
public class WriteExample {
    public static void main(String[] args) throws Exception{
        OutputStream os = new FileOutputStream("./Temp/test1.db");
        byte a = 4;
        byte b = 5;
        byte c = 6;

        // 1byte씩 출력
        os.write(a);
        os.write(b);
        os.write(c);

        // 출력 스트림은 출력할 바이트를 바로 보내는 것이 아니라 내부 버퍼(저장소)에 우선 저장을 함
        // 그래서 flush() 메소드를 사용하여 출력 버퍼에 잔류하는 모든 바이트를 출력
        os.flush();
        // 출력스트림을 닫음
        os.close();
    }
}
