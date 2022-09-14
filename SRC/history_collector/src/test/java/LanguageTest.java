import java.io.IOException;

import org.example.functions.util.ConfigReader;
import org.example.functions.util.TypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.junit.jupiter.api.Test;

public class LanguageTest {

	ConfigReader configReader = ConfigReader.getConfigReader();
	TypeConverter typeConverter = TypeConverter.getTypeConverter();

	@Test
	void test() throws IOException {
		String fileString = configReader.readFile("/static/json/mock_graphql_result.json");
		JSONObject jsonObject = typeConverter.convertStringToJSONObject(
			fileString);
		JSONPointer jsonPointer = JSONPointer.builder()
			.append("data")
			.append("repository")
			.append("pullRequests")
			.append("nodes")
			.append("comments")
			.append("nodes")
			.append("publishedAt")
			.build();
		Object o = jsonPointer.queryFrom(jsonObject);
		System.out.println("s = " + o);
	}

	@Test
	void test1() {
		//test
		JSONArray objects = new JSONArray("[]");
		Object o = objects.get(0);
	}
}
