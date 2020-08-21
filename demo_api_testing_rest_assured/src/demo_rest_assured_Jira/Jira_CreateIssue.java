package demo_rest_assured_Jira;

import static io.restassured.RestAssured.given;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;

public class Jira_CreateIssue {

	public static void main(String[] args) {
		
		//Login to jira
		String response = given().log().all().header("Content-Type","application/json")
		.body(Payload.jiraLogin())
		.when().post("rest/auth/1/session")
		.then().extract().response().asString();
			
		//JsonPath class  - Reusable to get session value
		String sessionValue = ReusableMethods.rawToJson(response).getString("session.value");
		System.out.println("The jira login session value is "+sessionValue);
		
		// create new jira issue
		RestAssured.baseURI = "http://localhost:8080";
		String createIssueResponse = given().log().all().header("Content-Type","application/json")
		.cookie("JSESSIONID",sessionValue)
		.body(Payload.createIssue())
		.when().post("rest/api/2/issue")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		//JsonPath class  - Reusable to get path id
		String issueId = ReusableMethods.rawToJson(createIssueResponse).getString("id");
		System.out.println("The newly added issue ID is "+issueId);
	}

}
