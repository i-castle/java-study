package chat.ver01;

public class ApiEnv {
    private String clientId, clientSecret, apiUrl;
    private ApiEnv apiEnv;
    public ApiEnv(String apiUrl){

    }
    public void setApiUrl(ApiEnv apiEnv){
        this.apiEnv = apiEnv;
    }
    public String getApiUrl(){
        return apiUrl;
    }
}
