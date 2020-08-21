package demo_rest_assured_advanced_payload_creation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class DynamicJsonPayload_ExternalDataInputs {
	
	@Test
	public void AddBook() {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type","application/json")
		//Dynamically build json payload with external data inputs
		.body(Payload.addBook("bknr","123458"))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js1 = ReusableMethods.rawToJson(response);
		String bookId = js1.getString("ID");
		System.out.println("Added Book ID - "+bookId);
	}
	

}
