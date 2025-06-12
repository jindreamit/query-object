package com.hero.x.query.object.tree.node;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrappedObject
{
    private final WrappedObject parent;
    private final ParameterizedType parameterizedType;
    private final Object object;
    private final HashMap<String, WrappedObject> fieldNameAndObject = new HashMap<>();

    private WrappedObject(WrappedObject parent, ParameterizedType parameterizedType, Object object, String fieldName)
    {
        this.parent = parent;
        this.parameterizedType = parameterizedType;
        this.object = object;
        if (parent != null)
        {
            parent.addField(fieldName, this);
        }
    }

    public WrappedObject getField(String fieldName)
    {
        return fieldNameAndObject.get(fieldName);
    }

    public void addField(String fieldName, WrappedObject wrappedObject)
    {
        fieldNameAndObject.put(fieldName, wrappedObject);
    }

    public WrappedObject getParent()
    {
        return parent;
    }

    public ParameterizedType getParameterizedType()
    {
        return parameterizedType;
    }

    public Object getObject()
    {
        return object;
    }

    public <T> T getAs()
    {
        return (T) object;
    }

    public static WrappedObject wrapUnsafe(Object object)
    {
        return new WrappedObject(null, null, object, null);
    }

    public static WrappedObject wrapRoot(Object object)
    {
        if (Map.class.isAssignableFrom(object.getClass()) || List.class.isAssignableFrom(object.getClass()))
        {
            throw new RuntimeException("unSupport type :" + object.getClass().getSimpleName());
        }
        return new WrappedObject(null, null, object, null);
    }

    public static WrappedObject wrapNode(WrappedObject parent, Object object, String fieldName)
    {
        return new WrappedObject(parent, null, object, fieldName);
    }

    public static WrappedObject wrapMapNode(WrappedObject parent, ParameterizedType type, Object object, String filedName)
    {
        return new WrappedObject(parent, type, object, filedName);
    }

    public static WrappedObject wrapListNode(WrappedObject parent, ParameterizedType type, Object object, String filedName)
    {
        return new WrappedObject(parent, type, object, filedName);
    }
}
