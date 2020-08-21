package demo_rest_assured_pojo_serialization;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;

public class AddNewPlaceGoogleMaps {
	public static void main(String[] args) {
		
		//create object for payload - pojo class and assign values
		AddPlace ap = new AddPlace();
		
		ap.setAccuracy(50);
		ap.setAddress("309, Whitefield, Bangalore-48");
		ap.setLanguage("English-IN");
		ap.setName("Imperio Hotel");
		ap.setPhone_number("080 24293000");
		ap.setWebsite("https://imperio.com");
		
		//passing object in setLocation method
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(31.123241);
		ap.setLocation(loc);
		
		//Passing array list values in setTypes method
		ArrayList<String> typesList = new ArrayList();
		typesList.add("hotel");
		typesList.add("Mall");
		ap.setTypes(typesList);
		
		
		
		//Passing payload in request using Pojo class - Object
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response response = given().queryParam("key", "qaclick123").body(ap)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		String resp = response.asString();
		System.out.println(resp);
		
		
	}

}