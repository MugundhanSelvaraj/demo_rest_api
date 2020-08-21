package demo_rest_assured_spec_builder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import demo_rest_assured_pojo_serialization.AddPlace;
import demo_rest_assured_pojo_serialization.Location;

public class AddPlace_SpecBuilder {
	public static void main(String[] args) {
		
/*
 * create object for payload - pojo class and assign values
 */
		AddPlace ap = new AddPlace();
		
		ap.setAccuracy(50);
		ap.setAddress("309, Whitefield, Bangalore-48");
		ap.setLanguage("English-IN");
		ap.setName("Imperio Hotel");
		ap.setPhone_number("080 24293000");
		ap.setWebsite("https://imperio.com");
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(31.123241);
		ap.setLocation(loc);
		//array values
		ArrayList<String> typesList = new ArrayList();
		typesList.add("hotel");
		typesList.add("Mall");
		ap.setTypes(typesList);
				
/*
 * Constructing Request spec builder
 */	
		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		//Constructing Response spec builder
		ResponseSpecification respSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
				
		RequestSpecification Request = given().spec(reqSpec).body(ap);
			
		Response response = Request.when().post("/maps/api/place/add/json")
		.then().spec(respSpec).extract().response();
		
		String resp = response.asString();
		System.out.println(resp);
		
	}

}