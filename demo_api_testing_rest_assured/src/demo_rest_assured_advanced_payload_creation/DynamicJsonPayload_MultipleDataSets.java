package demo_rest_assured_advanced_payload_creation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Datasets;
import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class DynamicJsonPayload_MultipleDataSets {
	
	//if Data Provider method is in same class, use as below
	//@Test(dataProvider="BooksData") 
	
	//if Data Provider method is in different class, use as below mentioning the dp class
	@Test(dataProvider="BooksData",dataProviderClass=Datasets.class)
	
	public void AddBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type","application/json")
		//Dynamically build json payload with multiple data sets as array
		.body(Payload.addBook(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js1 = ReusableMethods.rawToJson(response);
		String bookId = js1.getString("ID");
		System.out.println("Added Book ID - "+bookId);
	}
	
	
	// Organized this method to different class
	
	/*
	@DataProvider(name="BooksData")	 
	public Object[][] getData() {
		
		//array = collection of elements
		//multidimensional array = collection of arrays
		return new Object[][] {{"estdf","87329"},{"sstdf","8731"},{"cwetee","533"}};
	}
	*/
	

}
