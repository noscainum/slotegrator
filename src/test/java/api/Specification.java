package api;


import java.util.Base64;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import util.ConfProperties;

import static io.restassured.RestAssured.given;

public class Specification {
    private final static String url = ConfProperties.getProperty("apiurl");
    private final static String username = ConfProperties.getProperty("baseauthusername");
    private final static String password = "";
    private final static String authBasic = Base64.getEncoder().encodeToString((String.format("%s:%s", username, password)).getBytes());

    public static RequestSpecification requestSpecificationWithAuth() {
        RequestSpecification spec = given().config(RestAssured.config()
                        .headerConfig(HeaderConfig.headerConfig()
                                .overwriteHeadersWithName("Content-Type")
                                .overwriteHeadersWithName("Authorization")))
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("Authorization", String.format("Basic %s", authBasic))
                .baseUri(url);
        return spec;
    }

    public static RequestSpecification requestSpecificationWithToken(String token) {
        RequestSpecification spec = given().config(RestAssured.config()
                        .headerConfig(HeaderConfig.headerConfig()
                                .overwriteHeadersWithName("Authorization")))
                .header("Authorization", String.format("Bearer %s", token));
        return spec;
    }

    public static ResponseSpecification get200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification get201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }

    public static ResponseSpecification get404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    public static void useSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
