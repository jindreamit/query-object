package test;

import java.lang.reflect.*;
import java.util.Map;

public class TestGeneric {

    public Map<String, Map<String, String>> nestedMap;

    public static void main(String[] args) throws Exception {
        Field field = TestGeneric.class.getDeclaredField("nestedMap");
        Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;

            Type[] args1 = pt.getActualTypeArguments();
            System.out.println("Top-level key type: " + args1[0]);
            System.out.println("Top-level value type: " + args1[1]);

            if (args1[1] instanceof ParameterizedType) {
                ParameterizedType nestedPt = (ParameterizedType) args1[1];
                Type[] args2 = nestedPt.getActualTypeArguments();
                System.out.println("Nested key type: " + args2[0]);
                System.out.println("Nested value type: " + args2[1]);
            }
        }
    }
}
