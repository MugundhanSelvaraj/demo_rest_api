package demo_rest_assured_parsing_json;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse_SumValidation {
	
	@Test
	public void SumCourses() {
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		
		JsonPath js1 = new JsonPath(Payload.CoursePrice());
		
		int noOfCourses = js1.getInt("courses.size()");		
		int TotalPurchaseAmt = js1.getInt("dashboard.purchaseAmount");
		
				int sumOfAllCoursePrice = 0;
				for(int i=0;i<noOfCourses;i++) {
					int coursePrice = js1.getInt("courses["+i+"].price");	
					int courseCopies = js1.getInt("courses["+i+"].copies");
					int totalPriceOfEachCourse =(courseCopies * coursePrice);
					sumOfAllCoursePrice = sumOfAllCoursePrice + totalPriceOfEachCourse;
				}

					System.out.println("Sum of all Course prices: "+sumOfAllCoursePrice);
					Assert.assertEquals(sumOfAllCoursePrice, TotalPurchaseAmt);

	
	}

}
