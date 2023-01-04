// 문자열 출력
package sec01.exam02;

import java.io.FileWriter;
import java.io.Writer;

public class WriterExample4 {
    public static void main(String[] args) throws Exception{
        Writer wr = new FileWriter("./Temp/test7.txt");

        String str = "ABC";

        // 배열의 모든 문자를 출력
        wr.write(str);

        wr.flush();
        wr.close();

    }
}
