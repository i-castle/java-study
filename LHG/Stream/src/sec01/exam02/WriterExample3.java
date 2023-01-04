// 배열 일부 출력
package sec01.exam02;

import java.io.FileWriter;
import java.io.Writer;

public class WriterExample3 {
    public static void main(String[] args) throws Exception{
        Writer wr = new FileWriter("./Temp/test6.txt");

        char[] arr = {'A', 'B', 'C', 'D', 'E'};

        // 배열의 모든 문자를 출력
        wr.write(arr, 1, 3);

        wr.flush();
        wr.close();

    }
}
