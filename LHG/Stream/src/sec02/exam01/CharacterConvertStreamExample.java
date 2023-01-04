package sec02.exam01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class CharacterConvertStreamExample {
    public static void main(String[] args) throws Exception{
       write("문자 변환 스트림을 사용합니다.");
       String data = read();
       System.out.println(data);
    }
    
    public static void write(String str) throws Exception {
        // FileOuputStream에 OutputStreamWriter 보조스트림을 연결
        FileOutputStream fos = new FileOutputStream("./Temp/test1.txt");
        Writer writer = new OutputStreamWriter(fos);
        writer.write(str);
        writer.flush();
        writer.close();
    }
    
    public static String read() throws Exception{
        // FileInputStream에 InputStreamReader 보조스트림을 연결
        FileInputStream fis = new FileInputStream("./Temp/test1.txt");
        Reader reader = new InputStreamReader(fis);
        
        char[] buffer = new char[100];
        // InputStreamReader 보조 스트림을 이용하여 문자 입력
        int readCharNum = reader.read(buffer);
        reader.close();
        // char 배열에서 읽은 수만큼 문자열로 변환
        String data2 = new String(buffer, 0, readCharNum);
        return data2;
    }
}
