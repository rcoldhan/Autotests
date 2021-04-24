package restAssuredTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTests {

    private static final String postRequestBody = "{" + "\"id\":123," + "\"category\":{" +
            "\"id\":1," + "\"name\":\"Dogs\"" + "}," + "\"name\":\"Pesik\"," +
            "\"photoUrls\":[" + "\"Doggie\"" + "]," + "\"tags\":[" + "{" + "\"id\":1," +
            "\"name\":\"Small dogs\"" + "}" + "]," + "\"status\":\"available\"" + "}";

    private static final String getRequestBody = "{" + "\"id\":456," + "\"category\":{" +
            "\"id\":2," + "\"name\":\"Cats\"" + "}," + "\"name\":\"Kitty\"," +
            "\"photoUrls\":[" + "\"Kittens\"" + "]," + "\"tags\":[" + "{" + "\"id\":1," +
            "\"name\":\"Longhaired cats\"" + "}" + "]," + "\"status\":\"available\"" + "}";

    private static final String putRequestBody = "{" + "\"id\":321," + "\"name\":\"Homa\"," + "\"photoUrls\":[]," + "\"tags\":[]," + "\"status\":\"available\"" + "}";
    private static final String putRequestBodyChangedName = "{" + "\"id\":321," + "\"name\":\"Homyak\"," + "\"photoUrls\":[]," + "\"tags\":[]," + "\"status\":\"available\"" + "}";
    private static final String putRequestBodyChangedID = "{" + "\"id\":555," + "\"name\":\"Homa\"," + "\"photoUrls\":[]," + "\"tags\":[]," + "\"status\":\"available\"" + "}";


    private static final String deleteRequestBody = "{" + "\"id\":987," + "\"category\":{" +
            "\"id\":4," + "\"name\":\"Rats\"" + "}," + "\"name\":\"Rattsy\"," +
            "\"photoUrls\":[" + "\"Rat\"" + "]," + "\"tags\":[" + "{" + "\"id\":1," +
            "\"name\":\"Gray rats\"" + "}" + "]," + "\"status\":\"available\"" + "}";

    @BeforeAll
    public static void baseURL() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void postRequest200() {
        Response postResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(postRequestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .and()
                .extract().response();
        Assertions.assertEquals(postRequestBody, postResponse.body().asString());

        RestAssured.given()
                .when()
                .get("/pet/123")
                .then()
                .statusCode(200)
                .body("name", equalTo("Pesik"));
    }

    @Test
    public void getRequest200() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(getRequestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .get("/pet/456")
                .then()
                .statusCode(200)
                .body("name", equalTo("Kitty"));
    }

    @Test
    public void getRequest404() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(getRequestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .get("/pet/456")
                .then()
                .statusCode(200)
                .body("name", equalTo("Kitty"));

        RestAssured.given()
                .when()
                .delete("/pet/456")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .get("/pet/456")
                .then()
                .statusCode(404);
    }

    @Test
    public void putRequest200() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(putRequestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .get("/pet/321")
                .then()
                .statusCode(200)
                .body("name", equalTo("Homa"));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(putRequestBodyChangedName)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Homyak"));

        RestAssured.given()
                .when()
                .get("/pet/321")
                .then()
                .statusCode(200)
                .body("name", equalTo("Homyak"));
    }

    @Test
    public void putRequest404() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(putRequestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .get("/pet/321")
                .then()
                .statusCode(200)
                .body("name", equalTo("Homa"));

        RestAssured.given()
                .when()
                .delete("/pet/321")
                .then()
                .statusCode(200);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(putRequestBodyChangedName)
                .when()
                .put("/pet/321")
                .then()
                .statusCode(404);
    }

    @Test
    public void deleteRequest200() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(deleteRequestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .get("/pet/987")
                .then()
                .statusCode(200)
                .body("name", equalTo("Rattsy"));

        RestAssured.given()
                .when()
                .delete("/pet/987")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .get("/pet/987")
                .then()
                .statusCode(404);
    }

    @Test
    public void deleteRequest404() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(putRequestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .get("/pet/321")
                .then()
                .statusCode(200)
                .body("id", equalTo(321));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(putRequestBodyChangedID)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("id", equalTo(555));

        RestAssured.given()
                .when()
                .get("/pet/321")
                .then()
                .statusCode(404);

        RestAssured.given()
                .when()
                .delete("/pet/321")
                .then()
                .statusCode(404);
    }

    @AfterAll
    public static void deleteAll() {
        RestAssured.delete("/pet/123");
        RestAssured.delete("/pet/456");
        RestAssured.delete("/pet/321");
        RestAssured.delete("/pet/555");
        RestAssured.delete("/pet/987");
    }
}