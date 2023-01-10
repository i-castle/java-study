package chat.ver01;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ApiFunc extends Thread{
    private String msg;
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
        Trans trans = new Trans();

        String clientId = "sOS1gilSOHAlGQvRIEfZ";
        String clientSecret = "OVnTkwiZU5";

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text = "";
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

        String responseBody = trans.post(apiURL, requestHeaders, text);
        String[] temp = responseBody.split("\"");
        System.out.println(temp[15]);
    }
}
