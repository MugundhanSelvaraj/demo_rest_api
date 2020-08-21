package demo_rest_assured_parsing_json;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js1 = new JsonPath(Payload.CoursePrice());
		
		//Print No of courses returned by API
		int noOfCourses = js1.getInt("courses.size()");
		System.out.println("Number of courses: "+noOfCourses);

		//Print Purchase Amount
		int TotalPurchaseAmt = js1.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount of Courses: "+TotalPurchaseAmt);

		//Print Title of the first course
		String firstCourseTitle = js1.getString("courses[0].title");
		System.out.println("Title of the First Course: "+firstCourseTitle);

		//Print All course titles and their respective Prices
		for(int i=0;i<noOfCourses;i++) {
			String courseNames = js1.getString("courses["+i+"].title");
			int coursePrices = js1.getInt("courses["+i+"].price");
			System.out.println("Title of the course: "+courseNames);
			System.out.println("Course Price: "+coursePrices);
		}

		// no of copies sold by RPA Course
		for(int i=0;i<noOfCourses;i++) {
			String courseNames = js1.getString("courses["+i+"].title");
			if(courseNames.equalsIgnoreCase("RPA")){
				int courseCopies = js1.getInt("courses["+i+"].copies");
				System.out.println("RPA Course Copies: "+courseCopies);
				break;
			}
			else if(i==noOfCourses-1)
			{
				System.out.println("No RPA Course found");
			}
						
		}	
						
	}		
		
}
