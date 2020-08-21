package demo_rest_assured_advanced_payload_creation;

import org.testng.annotations.Test;

import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import java.io.IOException;

public class StaticJsonPayload_ExternalFile {
	
	@Test
	public void AddBook() throws IOException {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type","application/json")
		//Static json payload from external file
		.body(ReusableMethods.GenerateStringFromResource("C:\\Users\\Mugundhan\\Desktop\\API Testing Notes\\AddBookDetails.json"))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js1 = ReusableMethods.rawToJson(response);
		String bookId = js1.getString("ID");
		System.out.println("Added Book ID - "+bookId);
	}
	

}
