package steavy.testing.m3.unittest;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertThat;

public class HamcrestExampleTest
{

    @Test
    public void mapShouldContainValue()
    {
        Map<String, Integer> values = getValues();

        assertThat(values, hasKey("B"));
    }

    private Map<String, Integer> getValues()
    {
        final HashMap<String, Integer> map = new HashMap();
        map.put("A", 1);
        map.put("B", 2);
        return map;
    }

    @Test
    public void listOrderingIsIrrelevant()
    {
        List<Integer> numbers = getNumbers();

        assertThat(numbers, hasItem(5));
    }

    private List<Integer> getNumbers()
    {
        return Arrays.asList(1, 2, 3, 5, 4);
    }

}
