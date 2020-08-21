package demo_rest_assured_basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.Payload;

public class Basics_AddPlace_Store_Place_Id {

	public static void main(String[] args) {
		
		//validate if Add Place API is working as expected and to retrieve the place_id for other test 

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		//JsonPath class to read the string response and get the path(place_id)
		JsonPath js = new JsonPath(response);
		String place_id = js.getString("place_id");
		
		System.out.print("The place_id from response of AddPlace api is: "+place_id);
		
		
	}

}
