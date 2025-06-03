package com.hero.x.query.object.tree.node;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class WrappedObject
{
    private final WrappedObject parent;
    private final ParameterizedType parameterizedType;
    private final Object object;

    private WrappedObject(WrappedObject parent, ParameterizedType parameterizedType, Object object)
    {
        this.parent = parent;
        this.parameterizedType = parameterizedType;
        this.object = object;
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
        return new WrappedObject(null, null, object);
    }

    public static WrappedObject wrapRoot(Object object)
    {
        if (Map.class.isAssignableFrom(object.getClass()) || List.class.isAssignableFrom(object.getClass()))
        {
            throw new RuntimeException("unSupport type :" + object.getClass().getSimpleName());
        }
        return new WrappedObject(null, null, object);
    }

    public static WrappedObject wrapNode(WrappedObject parent, Object object)
    {
        return new WrappedObject(parent, null, object);
    }

    public static WrappedObject wrapMapNode(WrappedObject parent, ParameterizedType type, Object object)
    {
        return new WrappedObject(parent, type, object);
    }

    public static WrappedObject wrapListNode(WrappedObject parent, ParameterizedType type, Object object)
    {
        return new WrappedObject(parent, type, object);
    }
}
