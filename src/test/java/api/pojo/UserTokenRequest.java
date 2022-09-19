package api.pojo;

public class UserTokenRequest {
    public String grant_type;
    public String username;
    public String password;

    public UserTokenRequest(String grant_type, String username, String password) {
        this.grant_type = grant_type;
        this.username = username;
        this.password = password;
    }
}
