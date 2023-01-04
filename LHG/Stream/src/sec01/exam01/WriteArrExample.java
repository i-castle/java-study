package sec01.exam01;

import java.io.OutputStream;
import java.io.FileOutputStream;

public class WriteArrExample{
    public static void main(String[]args) throws Exception{
        OutputStream os = new FileOutputStream("./Temp/test2.db");
        byte[] arr = {10,20,30};
        os.write(arr);
        
        // 출력 버퍼에 잔류하는 모든 바이트를 출력
        os.flush();
        // 출력스트림을 닫음
        os.close();
    }

}
