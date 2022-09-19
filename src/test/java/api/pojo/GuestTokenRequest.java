package api.pojo;

public class GuestTokenRequest {
    public String grant_type;
    public String scope;

    public GuestTokenRequest(String grant_type, String scope) {
        this.grant_type = grant_type;
        this.scope = scope;
    }
}
