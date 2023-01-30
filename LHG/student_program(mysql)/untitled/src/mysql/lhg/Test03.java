package mysql.lhg;

import java.sql.*;
import java.util.*;

public class Test03 {
    private static int stu_num, kor_score, eng_score, mat_score;
    private int userInput;
    public int getUserInput() {
        return userInput;
    }
    public void setUserInput(int userInput) {
        this.userInput = userInput;
    }
    // 사용자 입력 메소드
    public void inputFunc(){
        Scanner sc = new Scanner(System.in);
        System.out.print("국어 점수를 입력하세요 > ");
        kor_score = Integer.parseInt(sc.nextLine());
        System.out.print("영어 점수를 입력하세요 > ");
        eng_score = Integer.parseInt(sc.nextLine());
        System.out.print("수학 점수를 입력하세요 > ");
        mat_score = Integer.parseInt(sc.nextLine());
    }
    public String getGrade(double grade){
        if(grade>=90){
            return "A";
        } else if(grade>=80){
            return "B";
        } else if(grade>=70){
            return "C";
        } else if(grade>=60){
            return "D";
        } else{
            return "E";
        }
    }

    public static void main(String[] args) {
        Test03 test03 = new Test03();
        String driver = "com.mysql.jdbc.Driver";
        // test03 database 사용
        String url = "jdbc:mysql://localhost:3306/test03";
        Map<String, String> env = System.getenv();
        // mac 환경변수로 local.mysql.user가 적용돠지 않아 _를 사용
        String user = env.get("local_mysql_user");
        String password = env.get("local_mysql_password");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        String sql="";
        // MySQL 연결
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            System.out.println("------------------------");
            System.out.println("학생성적관리프로그램(ver0.7.0)");
            System.out.println("------------------------");
            while(true) {
                try {
                    System.out.print("메뉴:1.리스트 2.보기 3.입력 4.수정 5.삭제 0.종료 > ");
                    int input = Integer.parseInt(sc.nextLine());
                    if (input == 1) {
                        // 학번 기준 내림차순
                        sql="select * from students order by stu_num desc";
                        rs = stmt.executeQuery(sql);
                        // 학번, 국어, 영어, 수학 문구 출력
                        System.out.println("학번\t\t\t국어\t영어\t수학");
                        // rs.next()는 다음 값을 가르키기 때문에 if 조건에서 사용하고 while 조건에서 다시 호출 시, 다음 행으로 넘어가서 do while을 사용함
                        if (rs.next() == false) {
                            System.out.println("등록된 학생 정보가 없습니다.\t정보를 입력하고 조회해주세요.");
                        } else {
                            do {
                                System.out.print(rs.getString(1) + "\t");
                                System.out.print(rs.getString(2) + "\t");
                                System.out.print(rs.getString(3) + "\t");
                                System.out.println(rs.getString(4));
                            } while (rs.next());
                            System.out.println("학생 정보 출력이 완료되었습니다.");
                        }
                        // 상세 보기의 경우에만 학번과 평균 점수가 나옴
                    } else if (input == 2) {
                        System.out.print("상세 조회할 학번을 입력하세요 > ");
                        test03.setUserInput(Integer.parseInt(sc.nextLine()));
                        sql = "select stu_num, kor, eng, mat, sum(kor+eng+mat), round(sum(kor+eng+mat)/3,2) from students";
                        sql+= " where stu_num=" + test03.getUserInput() + " group by stu_num";
                        rs = stmt.executeQuery(sql);
                        if(!rs.isBeforeFirst()){
                            System.out.println("조회된 학번이 없습니다.\n다시 입력해주세요.");
                        }
                        else {
                            System.out.println("학번\t\t\t국어\t영어\t수학\t합계\t평균\t\t학점");
                            // 학번은 1개의 값만 오기에 while이 아닌 if
                            if (rs.next()) {
                                System.out.print(rs.getString(1) + "\t");
                                System.out.print(rs.getString(2) + "\t");
                                System.out.print(rs.getString(3) + "\t");
                                System.out.print(rs.getString(4) + "\t");
                                System.out.print(rs.getString(5) + "\t");
                                System.out.print(rs.getString(6) + "\t");
                                System.out.println(test03.getGrade(rs.getDouble(6)));
                            }
                        }
                    } else if (input == 3) {
                        System.out.print("등록할 학번을 입력하세요 > ");
                        stu_num = Integer.parseInt(sc.nextLine());
                        String check_sql = "select stu_num from students";
                        rs = stmt.executeQuery(check_sql);
                        boolean isExist = false;
                        while(rs.next()){
                            if(stu_num==rs.getInt(1)){
                                isExist = true;
                                break;
                            }
                        }
                        if(isExist==false){
                            test03.inputFunc();
                            sql = "insert into students values (";
                            sql += "'" + stu_num + "','" + kor_score + "','" + eng_score + "','" + mat_score + "')";
                            stmt.executeUpdate(sql);
                            System.out.println("입력에 성공하였습니다.");
                        } else {
                            System.out.println("이미 학번이 존재합니다.\n확인 부탁드립니다.");
                        }
                    } else if (input == 4) {
                        System.out.print("수정할 학번을 입력하세요 > ");
                        stu_num = Integer.parseInt(sc.nextLine());
                        String check_sql = "select stu_num from students";
                        rs = stmt.executeQuery(check_sql);
                        boolean isExist = false;
                        while(rs.next()){
                            if(stu_num==rs.getInt(1)){
                                isExist = true;
                                break;
                            }
                        }
                        if(isExist==true){
                            test03.inputFunc();
                            sql = "update students set kor=";
                            sql += "'" + kor_score + "', eng='" + eng_score + "', mat='" + mat_score + "' where stu_num=" + stu_num;
                            stmt.executeUpdate(sql);
                            System.out.println("수정에 성공하였습니다.");
                        } else {
                            System.out.println("수정할 학번이 없습니다.\n확인 부탁드립니다.");
                        }
                    } else if (input == 5) {
                        System.out.print("삭제할 학번을 입력하세요 > ");
                        int stu_num = Integer.parseInt(sc.nextLine());
                        String check_sql = "select stu_num from students";
                        rs = stmt.executeQuery(check_sql);
                        boolean isExist = false;
                        while(rs.next()){
                            if(stu_num==rs.getInt(1)){
                                isExist = true;
                                break;
                            }
                        }
                        if(isExist==true){
                            sql = "delete from students where stu_num=" + stu_num;
                            stmt.executeUpdate(sql);
                            System.out.println("삭제에 성공하였습니다.");
                        } else {
                            System.out.println("삭제할 학번이 없습니다.\n확인 부탁드립니다.");
                        }
                    } else if (input == 0) {
                        System.out.println("------------------------");
                        System.out.println("시스템을 종료합니다.");
                        System.out.println("------------------------");
                        break;
                    } else {
                        System.out.println("보기에 있는 숫자를 입력해주세요.");
                    }
                } catch(NumberFormatException e){
                    System.out.println("숫자만 입력해주세요.\n 다시 시도해주세요.");
                    // 재입력을 위해 스캐너 재할당
                    sc = new Scanner(System.in);
                } catch (SQLException e) {
                    System.out.println("삭제에 실패했습니다.\n 다시 시도해주세요.");
                }
            }
        } catch (Exception e) {
            System.out.println("MySQL 연결에 실패");
        } finally {
            try {
                if (rs!=null)rs.close();
                if (stmt!=null) stmt.close();
                if (conn!=null) conn.close();
            } catch (SQLException e) {
                System.out.println("종료 실패");
            }
        }
    }
}
