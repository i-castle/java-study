package sec01.exam02;

import java.io.FileWriter;
import java.io.Writer;

public class WriterExample {
    public static void main(String[] args) throws Exception{
        Writer wr = new FileWriter("./Temp/test4.txt");

        char a = 'A';
        char b = 'B';
        char c = 'C';

        // 한 문자씩 출력
        wr.write(a);
        wr.write(b);
        wr.write(c);

        wr.flush();
        wr.close();

    }
}
