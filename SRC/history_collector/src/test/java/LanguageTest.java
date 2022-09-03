import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LanguageTest {
	@Test
	void test() {
		List<String> strings = new ArrayList<>();

		String a = "hello.world";
		List<String> strings1 = Arrays.asList(a.split("\\."));
		System.out.println("strings1 = " + strings1);
	}
}
