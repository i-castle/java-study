package sec01.exam01;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class WriteArrExample2 {
    public static void main(String[] args) throws Exception{
        OutputStream os = new FileOutputStream("./Temp/test3.db");
        byte[] arr = {10,20,30,40,50};

        // write(byte[] b, b[off], b.length)
        os.write(arr, 1, 3);

        os.flush();
        os.close();
    }
}
