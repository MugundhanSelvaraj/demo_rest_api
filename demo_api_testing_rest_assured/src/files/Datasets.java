package files;

import org.testng.annotations.DataProvider;

public class Datasets {
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		
		//array = collection of elements
		//multidimensional array = collection of arrays
		return new Object[][] {{"estdf","87329"},{"sstdf","8731"},{"cwetee","533"}};
	}

}
