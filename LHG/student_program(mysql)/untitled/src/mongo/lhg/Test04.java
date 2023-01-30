package mongo.lhg;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Projections;
import static com.mongodb.client.model.Indexes.descending;

public class Test04 {
    private static int stu_num, kor_score, eng_score, mat_score;
    public static void inputFunc(){
        Scanner sc = new Scanner(System.in);
        System.out.print("국어 점수를 입력하세요 > ");
        kor_score = Integer.parseInt(sc.nextLine());
        System.out.print("영어 점수를 입력하세요 > ");
        eng_score = Integer.parseInt(sc.nextLine());
        System.out.print("수학 점수를 입력하세요 > ");
        mat_score = Integer.parseInt(sc.nextLine());
    }
    public static String getGrade(double grade){
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
        // 로그 출력 방지
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);
        Scanner sc = new Scanner(System.in);
        // local 주소
        String MongoDB_IP = "127.0.0.1";
        // 포트 번호 27027로 설정
        int MongoDB_PORT = 27027;

        try {
            // 몽고db 연결
            MongoClient mongoClient = new MongoClient(new ServerAddress(MongoDB_IP, MongoDB_PORT));
            // 데이터베이스 - test03
            MongoDatabase db = mongoClient.getDatabase("test03");
            // 컬렉션 - students
            MongoCollection<Document> students = db.getCollection("students");
            System.out.println("------------------------");
            System.out.println("학생성적관리프로그램(ver0.8.0)");
            System.out.println("------------------------");
            while(true) {
                try {
                    System.out.print("메뉴:1.리스트 2.보기 3.입력 4.수정 5.삭제 0.종료 > ");
                    int input = Integer.parseInt(sc.nextLine());
                    // 모든 값 출력
                    if (input == 1) {
                        // id를 제외한 나머지 필드 값을 가져오기 위해 projection 사용하여, find의 인자로 보냄
                        Bson projectionFields = Projections.fields(
                                Projections.include("stu_num", "kor", "eng", "mat"),
                                Projections.excludeId());
                        // 학번 기준 내림차순 정렬
                        Bson sort = descending("stu_num");
                        FindIterable<Document> allListDoc = students.find()
                                .projection(projectionFields)
                                .sort(sort);
                        System.out.println("학번\t\t\t국어\t영어\t수학");
                        for(Document doc : allListDoc) {
                            System.out.println(doc.get("stu_num")+"\t"+doc.get("kor")+"\t"+doc.get("eng")+"\t"+doc.get("mat"));
                        }
                    }
                    // 특정 학번 조회, 출력 (합계, 평균, 학점 포함)
                    else if (input == 2) {
                        System.out.print("조회할 학번을 입력하세요 > ");
                        int input_stu_num = Integer.parseInt(sc.nextLine());
                        Bson projectionFields = Projections.fields(
                                Projections.include("stu_num", "kor", "eng", "mat"),
                                Projections.excludeId());
                        // 입력된 학번과 같은 document를 students 컬렉션에서 찾음
                        Document specViewDoc = students.find(eq("stu_num", input_stu_num))
                                .projection(projectionFields)
                                .first();
                        // 입력된 학번이 있을 경우, 조회
                        if (specViewDoc!=null) {
                            System.out.println("학번\t\t\t국어\t영어\t수학\t합계\t평균\t\t학점");
                            int kor = Integer.parseInt(String.valueOf(specViewDoc.get("kor")));
                            int eng = Integer.parseInt(String.valueOf(specViewDoc.get("eng")));
                            int mat = Integer.parseInt(String.valueOf(specViewDoc.get("mat")));
                            int sum = kor+eng+mat;
                            double avg = (sum/3);
                            System.out.println(specViewDoc.get("stu_num")+"\t"+kor+"\t"+eng+"\t"+mat+"\t"+sum+"\t"+avg*1.0+"\t"+getGrade(avg*1.0));
                        } else {
                            System.out.println("조회된 학번이 없습니다.\n다시 입력해주세요.");
                        }
                    }
                    // 학생 정보 입력
                    else if (input == 3) {
                        System.out.print("등록할 학번을 입력하세요 > ");
                        stu_num = Integer.parseInt(sc.nextLine());
                        // 이미 입력된 학번이 있는지 체크를 하기 위함
                        Document check_doc = students.find(Filters.eq("stu_num", stu_num)).first();
                        if(check_doc==null){
                            inputFunc();
                            Document insertDoc = null;
                            insertDoc = new Document("stu_num", stu_num).append("kor", kor_score).append("eng", eng_score).append("mat", mat_score);
                            students.insertOne(insertDoc);
                            System.out.println("입력이 완료되었습니다.");
                        } else {
                            // 이미 학번이 students 컬렉션에 있는 경우
                            System.out.println("이미 존재하는 학번입니다.");
                        }
                    }
                    // 학생 정보 수정
                    else if (input == 4) {
                        System.out.print("수정할 학번을 입력하세요 > ");
                        stu_num = Integer.parseInt(sc.nextLine());
                        Document modDoc = students.find(Filters.eq("stu_num", stu_num)).first();
                        // 입력한 학번에 해당하는 document가 있을 경우, 수정
                        if (modDoc!=null) {
                            inputFunc();
                            students.updateOne(Filters.eq("stu_num", stu_num),
                                    Updates.combine(Updates.set("kor", kor_score), Updates.set("eng", eng_score), Updates.set("mat", mat_score)));
                            System.out.println("수정이 완료되었습니다.");
                        } else {
                            System.out.println("수정할 학번이 없습니다.\n다시 입력해주세요.");
                        }
                    }
                    // 학생 정보 삭제
                    else if (input == 5) {
                        System.out.print("삭제할 학번을 입력하세요 > ");
                        int del_stu_num = Integer.parseInt(sc.nextLine());
                        Document delDoc = students.find(Filters.eq("stu_num", del_stu_num)).first();
                        // 입력한 학번에 해당하는 document가 있을 경우, 삭제
                        if (delDoc!=null) {
                            students.deleteOne(Filters.eq("stu_num", del_stu_num));
                            System.out.println("삭제가 완료되었습니다.");
                        } else {
                            System.out.println("삭제할 학번이 없습니다.\n다시 입력해주세요.");
                        }
                    }
                    // 종료
                    else if (input==0){
                        System.out.println("-------------------");
                        System.out.println("시스템을 종료합니다.");
                        System.out.println("-------------------");
                        break;
                    }
                    // 메뉴 보기에 없는 숫자 입력 시, 출력
                    else{
                        System.out.println("보기에 해당하는 숫자를 입력해주세요.");
                    }
                } catch(NumberFormatException e){
                    System.out.println("숫자만 입력해주세요.");
                    // 에러 발생으로 인한 스캐너 재할당
                    sc = new Scanner(System.in);
                }
            }
        } catch(MongoException e){
            System.out.println("몽고DB 연결 실패");
        }
    }
}
