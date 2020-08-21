package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath rawToJson(String response) {
		JsonPath js1 = new JsonPath(response);
		return js1;
	}
	
	public static String GenerateStringFromResource(String path) throws IOException {
		System.out.println(path);
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
