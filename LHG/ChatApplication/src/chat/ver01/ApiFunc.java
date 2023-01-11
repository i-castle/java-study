package chat.ver01;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ApiFunc extends Thread{
    private String msg;
    private String desLang;
    public String getDesLang() {
        return desLang;
    }
    public void setDesLang(String desLang) {
        this.desLang = desLang;
    }
    public ApiFunc(String msg){
        setMsg(msg);
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return msg;
    }
    @Override
    public void run(){
        // 감지는 항상 실행 => ok
        // 조건으로 source==destination이면 trans 실행 x
        Trans trans = new Trans();

        String clientId = "sOS1gilSOHAlGQvRIEfZ";
        String clientSecret = "OVnTkwiZU5";

        try{
            DetectLang detectLang = new DetectLang(msg,clientId,clientSecret);
            setDesLang(detectLang.getResponseStr());
        } catch (Exception e){System.out.println("감지 실패");}

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        // trans
        String text = "";
        // detect
        String query;
        try {
            Thread.sleep(500);
            text = URLEncoder.encode(getMsg(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        } catch (InterruptedException e) {
            System.out.println("sleep fail");
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = trans.post(apiURL, requestHeaders, text, desLang);
        String[] temp = responseBody.split("\"");
        System.out.println("번역: " + temp[15]);
    }
    // des 언어 추가

}
