package demo_rest_assured_Jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class Jira_GetIssue {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
		
		//Using session filter to get session value
		SessionFilter session = new SessionFilter();
		
		//Login to jira
		given().log().all().relaxedHTTPSValidation().header("Content-Type","application/json")
		.body(Payload.jiraLogin()).filter(session)
		.when().post("rest/auth/1/session")
		.then().log().all();
		
		String jiraComment = "REST API Test comment - New";
		
		// Add comment for the jira issue		
		String response = given().log().all().pathParam("issueId", "10102").
		header("Content-Type","application/json").filter(session)
		.body(Payload.addComment())
		.when().post("rest/api/2/issue/{issueId}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		//JsonPath class  - Reusable to get path id
		String commentId = ReusableMethods.rawToJson(response).getString("id");
		System.out.println("The newly added comment ID is "+commentId);
		
		//Get Issue API
		String getIssueResponse = given().log().all().pathParam("issueId", "10102")
			.queryParam("fields", "comment").header("Content-Type","application/json").filter(session)
			.when().get("rest/api/2/issue/{issueId}")
			.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(getIssueResponse);
		int noOfComments = js1.get("fields.comment.comments.size()");
		System.out.println("Total number of added comments "+noOfComments);
		
		//verify if the comment is available
		for(int i=0;i<noOfComments;i++) {
			if(commentId.equalsIgnoreCase(js1.get("fields.comment.comments["+i+"].id").toString())) {
				String actualComment = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(actualComment);
				Assert.assertEquals(actualComment, jiraComment);
				break;
			}
			
		}
		
	}

}
