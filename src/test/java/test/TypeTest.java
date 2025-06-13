package test;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeTest
{
    @Test
    public void test() throws Exception
    {
        Field embedMapMap = TypedObject.class.getDeclaredField("embedMapMap");
        Type genericType = embedMapMap.getGenericType();
        System.out.println(genericType);
        ParameterizedType type = (ParameterizedType) genericType;
        System.out.println(type.getActualTypeArguments()[0]);
        System.out.println(type.getActualTypeArguments()[0].getClass());
    }

    static class TypedObject
    {
        Map<String, Map<String, Integer>> embedMapMap = new HashMap<>();
        Map<String, List<Integer>> embedMapList = new HashMap<>();
    }
}
