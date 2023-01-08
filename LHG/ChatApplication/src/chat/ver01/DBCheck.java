package chat.ver01;

import javax.swing.*;
import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.io.FileWriter;
public class DBCheck {
    private static String id, passwd;
    private static TreeMap<String, String> userList = new TreeMap<String, String>();
    public DBCheck(String id, String passwd){
        this.id = id;
        this.passwd = passwd;
    }
    public static void getDB(){
    }
    public static boolean existFile(){
        File file = new File("./db/test1.txt");
        if(file.exists()) return true;
        else return false;
    }
    public static void main(String[] args) {
        TreeMap<String, String> userList = new TreeMap<String, String>();

        File file = new File("./db/test1.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            while(true){
                String data = reader.readLine();
                if(data==null) break;
                String[] arr = data.split("/");
                userList.put(arr[0], arr[1]);
            }
            if(userList.containsKey(id)){
                System.out.println("아이디가 있습니다.");
            } else {
                userList.put(id, passwd);
                Set set = userList.entrySet();
                Iterator iterator = set.iterator();
                // 라인 단위 출력을 위해 PrintStream 사용
                FileWriter fw = new FileWriter("./db/test1.txt");
                PrintWriter pw = new PrintWriter(fw);
                while(iterator.hasNext()){
                    Object obj = iterator.next();
                    String str = obj.toString();
                    String[] arr = str.split("=");
                    // 추가로 입력받은 id, passwd를 포함하여 출력 스트림
                    pw.println(arr[0]+"/"+arr[1]);
                }
                pw.flush();
                pw.close();
                System.out.println("회원가입 성공");
            }
        } catch (FileNotFoundException e) {
            System.out.println("파일 생성이 필요함");
//             fw = null;
            try {
                FileWriter fw = new FileWriter("./db/test1.txt");
                Writer wr = new BufferedWriter(fw);
                userList.put(id, passwd);
                wr.write(id + "/");
                wr.write(passwd);
                System.out.println(id + "/" + passwd);
                wr.flush();
                wr.close();
                System.out.println("회원가입 성공");
            } catch (IOException ex) {
                //
            }
        } catch (IOException e) {
            //
        }
    }
}
