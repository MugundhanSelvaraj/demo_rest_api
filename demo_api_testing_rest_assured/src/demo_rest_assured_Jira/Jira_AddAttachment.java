package demo_rest_assured_Jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

import java.io.File;

import files.Payload;
import files.ReusableMethods;

public class Jira_AddAttachment {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
		
		//Using session filter to get session value
		SessionFilter session = new SessionFilter();
		
		//Login to jira
		String response = given().log().all().header("Content-Type","application/json")
		.body(Payload.jiraLogin()).filter(session)
		.when().post("rest/auth/1/session")
		.then().log().all().extract().response().asString();
		
 		String sessionValue = ReusableMethods.rawToJson(response).getString("session.value");
		System.out.println(sessionValue);
		
		// Add attachment for the jira issue		
		given().log().all().pathParam("issueId", "10102").header("X-Atlassian-Token","no-check")
		.header("content-Type","multipart/form-data").filter(session)
		.multiPart("file", new File("JiraSampleAttachment.txt"))
		.when().post("rest/api/2/issue/{issueId}/attachments")
		.then().log().all().assertThat().statusCode(200);

	}

}
