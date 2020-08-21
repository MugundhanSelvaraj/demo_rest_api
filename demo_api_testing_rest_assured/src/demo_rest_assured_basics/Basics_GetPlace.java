package demo_rest_assured_basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class Basics_GetPlace {

	public static void main(String[] args) {
		
		//Add Place API code

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		//JsonPath class to read the string response(converted to json format) and parse to get the path(place_id)
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");		
		System.out.print("The place_id from response of AddPlace api is: "+placeId);
		
		//Update Place API code
		String newAddress = "27 Errol Gardens, US";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		//Get Place API code
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		//Reusable rawToJson method - Returns object
		String actualAddress = ReusableMethods.rawToJson(getPlaceResponse).getString("address");
		
		//testng method and assertion
		System.out.print("The actual address is: "+actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
		 
	}

}
