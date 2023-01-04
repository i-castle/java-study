// 배열 전체
package sec01.exam02;

import java.io.FileWriter;
import java.io.Writer;

public class WriterExample2 {
    public static void main(String[] args) throws Exception{
        Writer wr = new FileWriter("./Temp/test5.txt");

        char[] arr = {'A', 'B', 'C'};

        // 배열의 모든 문자를 출력
        wr.write(arr);

        wr.flush();
        wr.close();

    }
}
