package demo_rest_assured_oauth;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReusableMethods;

public class GetCourse_OAuth {

	public static void main(String[] args) {
		/* web steps to launch google sign in is removed for automation as per update in 2020
		
		//invoke .exe file
		System.setProperty("webdriverchrome.driver","D:\\Softwares\\chromedriver.exe");
		
		//Create driver object for chrome browser
		//Class name = ChromeDriver
		 ChromeDriver  driver = new ChromeDriver();
		 
		 */
		
		
		//Get the authorization code from url
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F1gFkRM9kvS9UM71wu5JKBzdgpBcUdJivHDXOAgwPg8aFYZnojhw6dBz9zmOFca8BoK2o3JH0554tcS-8b5z61IE&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
		String urlCode = url.split("code=")[1];
		String authorizationCode = urlCode.split("&scope=")[0];
		System.out.println(authorizationCode);
		
		//Post authorization code to the resource api and get access token
		String accessTokenResponse = given().log().all().queryParam("code", authorizationCode)
		.queryParam("grant_type", "authorization_code").urlEncodingEnabled(false)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.println(accessTokenResponse);
		
		String accessToken = ReusableMethods.rawToJson(accessTokenResponse).getString("access_token");
		
		//GetCourse Api using access token
		String coursesResponse = given().queryParam("access_token", accessToken)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.then().assertThat().log().all().statusCode(200).extract().asString();
		
		String instructorName = ReusableMethods.rawToJson(coursesResponse).getString("instructor");
		System.out.println(instructorName);
		
		Assert.assertEquals(instructorName, "RahulShetty");
	}


}
