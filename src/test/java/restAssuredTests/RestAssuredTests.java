package restAssuredTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pet.Pet;

public class RestAssuredTests {

    @Test
    public void postRequest() {
        ObjectMapper om = new ObjectMapper();
        Pet myPet = null;
        try {
            myPet = om.readValue("{\n" +
                    "  \"id\": 123,\n" +
                    "  \"category\": {\n" +
                    "    \"id\": 1,\n" +
                    "    \"name\": \"Собаки\"\n" +
                    "  },\n" +
                    "  \"name\": \"Песик\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"Собакены\"\n" +
                    "  ],\n" +
                    "  \"tags\": [\n" +
                    "    {\n" +
                    "      \"id\": 1,\n" +
                    "      \"name\": \"Маленькие собаки\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"status\": \"available\"\n" +
                    "}", Pet.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Pet newPet = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(myPet)
                .post("https://petstore.swagger.io/v2/pet")
                .as(Pet.class);
        Assertions.assertEquals(myPet, newPet);
    }

    @Test
    public void
}