import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class ReqResTests {

    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());

        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
    }


    @Test
    public void loginTest(){
        given()

                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("login")
                .then()
                .statusCode(200)
                .body("token",notNullValue());
    }

    @Test
    public void getSingleUserTest(){
        given()

                .get("users/2")
                .then().log().all()
                .statusCode(200)
                .body("data.id",equalTo(2));
    }

    @Test
    public void deleteUserTest(){
        given()
                .delete("users/2")
                .then().log().all()
                .statusCode(204);
    }

}
