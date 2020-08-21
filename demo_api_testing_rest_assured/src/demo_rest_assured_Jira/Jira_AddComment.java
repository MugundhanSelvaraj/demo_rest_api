package demo_rest_assured_Jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

import files.Payload;
import files.ReusableMethods;

public class Jira_AddComment {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
		
		//Using session filter to get session value
		SessionFilter session = new SessionFilter();
		
		//Login to jira
		given().log().all().header("Content-Type","application/json")
		.body(Payload.jiraLogin()).filter(session)
		.when().post("rest/auth/1/session")
		.then().log().all();
		
		// Add comment for the jira issue		
		String response = given().log().all().pathParam("issueId", "10102").
		header("Content-Type","application/json").filter(session)
		//.cookie("JSESSIONID","2359CEEC78C7EB534A3FB937CA444418")
		.body(Payload.addComment())
		.when().post("rest/api/2/issue/{issueId}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		//JsonPath class  - Reusable to get path id
		String commentId = ReusableMethods.rawToJson(response).getString("id");
		System.out.println("The newly added comment ID is "+commentId);

	}

}
