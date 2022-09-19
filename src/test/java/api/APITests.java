package api;

import api.pojo.*;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class APITests {


    private static final String passwordChange = "dmVyeXN0cm9uZ3Bhc3M=";
    private static final String passwordRepeat = "dmVyeXN0cm9uZ3Bhc3M=";
    private static final String name = "New";
    private static final String surname = "User";
    private static final String currencyCode = "USD";


    @Test
    public void shouldGrantClientCredentials() {
        Assert.assertNotNull(getGuestToken());
    }

    @Test
    public void shouldRegisterNewPlayer() {
        String username = RandomStringUtils.random(8, true, false);
        String email = username + "@example.com";
        NewPlayerCredentials newPlayer = registerNewPlayer(username, passwordChange, passwordRepeat, email, name, surname, currencyCode);
        Assert.assertNotNull(newPlayer.getId());
        Assert.assertEquals(username, newPlayer.getUsername());
        Assert.assertEquals(email, newPlayer.getEmail());
        Assert.assertEquals(name, newPlayer.getName());
        Assert.assertEquals(surname, newPlayer.getSurname());
    }

    @Test
    public void shouldLoginAsNewPlayer(){
        String username = RandomStringUtils.random(8, true, false);
        String email = username + "@example.com";
        NewPlayerCredentials newPlayer = registerNewPlayer(username, passwordChange, passwordRepeat, email, name, surname, currencyCode);
        Assert.assertNotNull(getUserToken(username, passwordChange));

    }

    @Test
    public void shouldGetPlayerProfile(){
        String username = RandomStringUtils.random(8, true, false);
        String email = username + "@example.com";
        NewPlayerCredentials newPlayer = registerNewPlayer(username, passwordChange, passwordRepeat, email, name, surname, currencyCode);
        NewPlayerCredentials playerProfile = getPlayerProfile(getUserToken(newPlayer.username, passwordChange), newPlayer.getId());
        Assert.assertEquals(playerProfile.getUsername(), newPlayer.getUsername());
        Assert.assertEquals(playerProfile.getEmail(), newPlayer.getEmail());
        Assert.assertEquals(playerProfile.getName(), newPlayer.getName());
        Assert.assertEquals(playerProfile.getSurname(), newPlayer.getSurname());
    }

    @Test
    public void shouldNotGetOtherPlayerProfile(){
        String username = RandomStringUtils.random(8, true, false);
        String email = username + "@example.com";
        NewPlayerCredentials newPlayer = registerNewPlayer(username, passwordChange, passwordRepeat, email, name, surname, currencyCode);
        getOtherProfile(getUserToken(newPlayer.username, passwordChange), newPlayer.getId());

    }

    public static String getGuestToken() {
        Specification.useSpecification(Specification.requestSpecificationWithAuth(), Specification.get200());
        String grantType = "client_credentials";
        String scope = "guest:default";
        GuestTokenRequest guest = new GuestTokenRequest(grantType, scope);
        UserCredentials userCredentials = given()
                .body(guest)
                .when().log().all()
                .post("/v2/oauth2/token")
                .then().log().all()
                .extract().as(UserCredentials.class);
        return userCredentials.getAccess_token();
    }

    public static NewPlayerCredentials registerNewPlayer(String username, String passwordChange, String passwordRepeat, String email, String name, String surname, String currencyCode) {
        Specification.useSpecification(Specification.requestSpecificationWithToken(getGuestToken()), Specification.get201());
        NewPlayerRequest newPlayerRequest = new NewPlayerRequest(username, passwordChange, passwordRepeat, email, name, surname, currencyCode);
        NewPlayerCredentials newPlayerCredentials = given()
                .body(newPlayerRequest)
                .when().log().all()
                .post("/v2/players")
                .then().log().all()
                .extract().as(NewPlayerCredentials.class);
        return  newPlayerCredentials;
    }

    public static String getUserToken(String username, String password){
        Specification.useSpecification(Specification.requestSpecificationWithAuth(), Specification.get200());
        String grantType = "password";
        UserTokenRequest user = new UserTokenRequest(grantType, username, password);
        UserCredentials userCredentials = given()
                .body(user)
                .when().log().all()
                .post("/v2/oauth2/token")
                .then().log().all()
                .extract().as(UserCredentials.class);
        return userCredentials.getAccess_token();
    }

    public static NewPlayerCredentials getPlayerProfile(String userToken, Integer id){
        Specification.useSpecification(Specification.requestSpecificationWithToken(userToken), Specification.get200());
        NewPlayerCredentials playerProfile = given()
                .when().log().all()
                .get("/v2/players/" + id)
                .then().log().all()
                .extract().as(NewPlayerCredentials.class);
        return playerProfile;
    }

    public static void getOtherProfile(String userToken, Integer id){
        Specification.useSpecification(Specification.requestSpecificationWithToken(userToken), Specification.get404());
        RestAssured.given()
                .when().log().all()
                .get("/v2/players/" + (id-1))
                .then().log().all();
    }

}
