package demo_rest_assured_pojo_deserialization;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import files.ReusableMethods;
import io.restassured.parsing.Parser;

public class GetCoursePojo {

	public static void main(String[] args) {
		/* web steps to launch google sign in is removed for automation as per update in 2020
		
		//invoke .exe file
		System.setProperty("webdriverchrome.driver","D:\\Softwares\\chromedriver.exe");
		
		//Create driver object for chrome browser
		//Class name = ChromeDriver
		 ChromeDriver  driver = new ChromeDriver();
		 
		 */
		
		
		//Get the authorization code from url
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F1gEWixI9HF_A1IL-eA79wqVjHuvrdA14aFKRERx_wYRCDYmGzd-E-a2t-ecdNgcu3eujFN2Y5S2xoQu9cSRezdc&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
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
		
		//use access token and hit courses - store response in pojo class object
		GetCourses gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.then().assertThat().log().all().statusCode(200).extract().as(GetCourses.class);
		
/*
 * Validations of responses
 */
		//validate the instructor in response
		Assert.assertEquals(gc.getInstructor(), "RahulShetty");
		
		//validate the url in response
		String url1 = gc.getUrl();
		Assert.assertEquals(url1, "rahulshettycademy.com");
		System.out.println("url is "+url1);
						
		//validate the services in response
		Assert.assertEquals(gc.getServices(), "projectSupport");
		System.out.println("Services are - "+gc.getServices());
		
		//validate price for api course 'SoapUI Webservices testing'
		List<Api> apiCourses = gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++) {
			if((apiCourses.get(i).getCourseTitle()).equalsIgnoreCase("SoapUI Webservices testing")){
				System.out.println("Price for 'SoapUI Webservices testing' - "+apiCourses.get(i).getPrice());
			}
		}

/*
 * Validation using arraylist and TestNG assertions
 */
		//validate and print course titles under web automation
		List<WebAutomation> webCourses = gc.getCourses().getWebAutomation();
		for(int i=0;i<webCourses.size();i++) {
			String webCoursesTitle = webCourses.get(i).getCourseTitle();
			System.out.println("Web Courses Title"+i+"-"+webCoursesTitle);
		}
		
		//validate all api course titles matches as expected
		String[] expectedApiCourses = {"Rest Assured Automation using Java","SoapUI Webservices testing"};
		
		//created an array list to save all titles
		ArrayList<String> actual = new ArrayList<String>();
		for(int i=0;i<apiCourses.size();i++) {
			actual.add(apiCourses.get(i).getCourseTitle());
		}
		//compare the actual and expected
		List<String> expected = Arrays.asList(expectedApiCourses);
		Assert.assertTrue(actual.equals(expected));
	}


}
