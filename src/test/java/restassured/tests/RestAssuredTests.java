package restassured.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pet.Category;
import pet.Pet;

import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTests {
    private static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri("https://petstore.swagger.io/v2")
            .setContentType(ContentType.JSON)
            .build();
    private static final ResponseSpecification RES_SPEC_200 = new ResponseSpecBuilder()
            .expectStatusCode(200).build();
    private static final ResponseSpecification RES_SPEC_404 = new ResponseSpecBuilder()
            .expectStatusCode(404).build();

    @Test
    public void post200() {
        Pet newPet = new Pet();
        newPet.setId(123);
        newPet.setName("Pesik");
        newPet.setStatus("available");
        Category c = new Category();
        c.setName("Dogs");
        newPet.setCategory(c);
        Pet postPet = RestAssured.given().spec(REQ_SPEC)
                .body(newPet)
                .expect().spec(RES_SPEC_200)
                .when().post("/pet").as(Pet.class);
        Assertions.assertEquals(newPet, postPet);

        RestAssured.given().spec(REQ_SPEC)
                .expect().spec(RES_SPEC_200)
                .when().get("/pet/123")
                .then().body("name", equalTo("Pesik"));
    }

    @Test
    public void get200() {
        Pet newPet = new Pet();
        newPet.setId(456);
        newPet.setName("Kitty");
        newPet.setStatus("available");
        Category c = new Category();
        c.setName("Cats");
        newPet.setCategory(c);
        RestAssured.given().spec(REQ_SPEC)
                .body(newPet)
                .expect().spec(RES_SPEC_200)
                .when().post("/pet");

        Pet getPet = RestAssured.given().spec(REQ_SPEC)
                .expect().spec(RES_SPEC_200)
                .when().get("/pet/456")
                .then().body("name", equalTo("Kitty"))
                .extract().as(Pet.class);
        Assertions.assertEquals(newPet, getPet);
    }

    @Test
    public void get404() {
        Pet newPet = new Pet();
        newPet.setId(456);
        newPet.setName("Kitty");
        newPet.setStatus("available");
        Category c = new Category();
        c.setName("Cats");
        newPet.setCategory(c);
        RestAssured.given().spec(REQ_SPEC)
                .body(newPet)
                .expect().spec(RES_SPEC_200)
                .when().post("/pet");

        RestAssured.given().spec(REQ_SPEC)
                .expect().spec(RES_SPEC_200)
                .when().delete("/pet/456");

        RestAssured.given().spec(REQ_SPEC)
                .expect().spec(RES_SPEC_404)
                .when().get("/pet/456")
                .then().body("code", equalTo(1),
                "type", equalTo("error"),
                "message", equalTo("Pet not found"));
    }

    @Test
    public void put200() {
        Pet newPet = new Pet();
        newPet.setId(321);
        newPet.setName("Homa");
        newPet.setStatus("available");
        Category c = new Category();
        c.setName("Hamsters");
        newPet.setCategory(c);

        Pet changedNamePet = new Pet();
        changedNamePet.setId(321);
        changedNamePet.setName("Homyak");
        changedNamePet.setStatus("available");
        Category cat = new Category();
        cat.setName("Hamsters");
        changedNamePet.setCategory(cat);

        RestAssured.given().spec(REQ_SPEC)
                .body(newPet)
                .expect().spec(RES_SPEC_200)
                .when().post("/pet")
                .then().body("name", equalTo("Homa"));

        Pet putPet = RestAssured.given().spec(REQ_SPEC)
                .body(changedNamePet)
                .expect().spec(RES_SPEC_200)
                .when().put("/pet").as(Pet.class);
        Assertions.assertEquals(changedNamePet, putPet);

        RestAssured.given().spec(REQ_SPEC)
                .expect().spec(RES_SPEC_200)
                .when().get("/pet/321")
                .then().body("name", equalTo("Homyak"));
    }

    @Test
    public void put404() {
        Pet newPet = new Pet();
        newPet.setId(321);
        newPet.setName("Homa");
        newPet.setStatus("available");
        Category c = new Category();
        c.setName("Hamsters");
        newPet.setCategory(c);

        Pet changedNamePet = new Pet();
        changedNamePet.setId(321);
        changedNamePet.setName("Homyak");
        changedNamePet.setStatus("available");
        Category cat = new Category();
        cat.setName("Hamsters");
        changedNamePet.setCategory(cat);

        RestAssured.given().spec(REQ_SPEC)
                .body(newPet)
                .expect().spec(RES_SPEC_200)
                .when().post("/pet");

        RestAssured.given().spec(REQ_SPEC)
                .expect().spec(RES_SPEC_200)
                .when().delete("/pet/321");

        RestAssured.given().spec(REQ_SPEC)
                .body(changedNamePet)
                .expect().spec(RES_SPEC_404)
                .when().put("/pet");
    }

    @Test
    public void delete200() {
        Pet newPet = new Pet();
        newPet.setId(987);
        newPet.setName("Rattsy");
        newPet.setStatus("available");
        Category c = new Category();
        c.setName("Rats");
        newPet.setCategory(c);
        RestAssured.given().spec(REQ_SPEC)
                .body(newPet)
                .expect().spec(RES_SPEC_200)
                .when().post("/pet");

        RestAssured.given().spec(REQ_SPEC)
                .expect().spec(RES_SPEC_200)
                .when().delete("/pet/987")
                .then().body("code", equalTo(200),
                "type", equalTo("unknown"),
                "message", equalTo("987"));

        RestAssured.given().spec(REQ_SPEC)
                .expect().spec(RES_SPEC_404)
                .when().get("/pet/987")
                .then().body("code", equalTo(1),
                "type", equalTo("error"),
                "message", equalTo("Pet not found"));
    }

    @Test
    public void delete404() {
        Pet newPet = new Pet();
        newPet.setId(987);
        newPet.setName("Rattsy");
        newPet.setStatus("available");
        Category c = new Category();
        c.setName("Rats");
        newPet.setCategory(c);

        Pet changedIDPet = new Pet();
        changedIDPet.setId(555);
        changedIDPet.setName("Rattsy");
        changedIDPet.setStatus("available");
        Category cat = new Category();
        cat.setName("Rats");
        changedIDPet.setCategory(cat);

        RestAssured.given().spec(REQ_SPEC)
                .body(newPet)
                .expect().spec(RES_SPEC_200)
                .when().post("/pet");

        RestAssured.given().spec(REQ_SPEC)
                .body(changedIDPet)
                .expect().spec(RES_SPEC_200)
                .when().put("/pet")
                .then().body("id", equalTo(555));

        RestAssured.given().spec(REQ_SPEC)
                .expect().spec(RES_SPEC_404)
                .when().delete("/pet/321");
    }
}