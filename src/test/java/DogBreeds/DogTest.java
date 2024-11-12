package DogBreeds;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;
import java.util.List;

public class DogTest {

    @Test
    public void testGetDogBreeds() {
        // Set the base URI for the Dog API
        RestAssured.baseURI = "https://dog.ceo";

        // Send a GET request to /api/breeds/list/all
        Response response = RestAssured
                .given()
                .when()
                .get("/api/breeds/list/all")
                .then()
                .statusCode(200)  // Assert that the status code is 200
                .extract()
                .response();

        // Print the response body
        System.out.println("Response Body: " + response.getBody().asString());

        // Parse the response JSON to check for specific breeds
        Map<String, List<String>> breeds = response.jsonPath().getMap("message");

        // Assert that certain breeds exist in the response
        Assert.assertTrue(breeds.containsKey("bulldog"), "Response should contain 'bulldog'");
        Assert.assertTrue(breeds.containsKey("retriever"), "Response should contain 'retriever'");
        Assert.assertTrue(breeds.containsKey("terrier"), "Response should contain 'terrier'");

        // Check if bulldog has specific sub-breeds
        List<String> bulldogSubBreeds = breeds.get("bulldog");
        Assert.assertTrue(bulldogSubBreeds.contains("boston"), "Bulldog should have 'boston' as a sub-breed");
        Assert.assertTrue(bulldogSubBreeds.contains("english"), "Bulldog should have 'english' as a sub-breed");
        Assert.assertTrue(bulldogSubBreeds.contains("french"), "Bulldog should have 'french' as a sub-breed");

        // Check if the response status is 'success'
        String status = response.jsonPath().getString("status");
        Assert.assertEquals(status, "success", "Expected status to be 'success'");
    }
}
