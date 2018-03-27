package tests.api.common;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapMatcher extends BaseMatcher<Map<String, String>> {

    private final LinkedHashMap<String, String> expected;

    public MapMatcher(LinkedHashMap<String, String> expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object o) {
        if (!(o instanceof LinkedHashMap)) {
            return false;
        }
        final LinkedHashMap actual = (LinkedHashMap) o;
        final List<Map.Entry<String, String>> expectedList = new ArrayList<>(expected.entrySet());
        final List actualList = new ArrayList(actual.entrySet());
        return expectedList.equals(actualList);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expected.toString());
    }
}