package com.hero.x.query.object.tree.node.handler.filter;

public class TypeUtil
{
    public static boolean isPrimitiveOrWrapperOrString(Class<?> clazz)
    {
        return clazz.equals(boolean.class) || clazz.equals(Boolean.class) || clazz.equals(byte.class) || clazz.equals(Byte.class) || clazz.equals(char.class) ||
            clazz.equals(Character.class) || clazz.equals(short.class) || clazz.equals(Short.class) || clazz.equals(int.class) || clazz.equals(Integer.class) ||
            clazz.equals(long.class) || clazz.equals(Long.class) || clazz.equals(float.class) || clazz.equals(Float.class) || clazz.equals(double.class) ||
            clazz.equals(Double.class) || clazz.equals(void.class) || clazz.equals(Void.class) || clazz.equals(String.class);
    }
}
