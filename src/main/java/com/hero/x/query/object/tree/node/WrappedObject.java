package com.hero.x.query.object.tree.node;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class WrappedObject
{
    private final WrappedObject parent;
    private final Field filedInParent;
    private final ObjectTypeInfo typeInfo;
    private final Object object;

    private WrappedObject(WrappedObject parent, Field filedInParent, ObjectTypeInfo typeInfo, Object object)
    {
        this.parent = parent;
        this.filedInParent = filedInParent;
        this.typeInfo = typeInfo;
        this.object = object;
    }


    public abstract static class BaseTypeInfo
    {
        private final Type type;

        protected BaseTypeInfo(Type type)
        {
            this.type = type;
        }


        public Type getType()
        {
            return type;
        }
    }

    public static class ObjectTypeInfo extends BaseTypeInfo
    {
        public ObjectTypeInfo(Type type)
        {
            super(type);
        }
    }

    public static class ListTypeInfo extends ObjectTypeInfo
    {
        private final ObjectTypeInfo elementTypeInfo;

        public ListTypeInfo(Type type, ObjectTypeInfo elementTypeInfo)
        {
            super(type);
            this.elementTypeInfo = elementTypeInfo;
        }

        public ObjectTypeInfo getElementTypeInfo()
        {
            return elementTypeInfo;
        }
    }

    public static class MapTypeInfo extends ObjectTypeInfo
    {
        private final ObjectTypeInfo keyTypeInfo;
        private final ObjectTypeInfo valueTypeInfo;

        public MapTypeInfo(Type type, ObjectTypeInfo keyTypeInfo, ObjectTypeInfo valueTypeInfo)
        {
            super(type);
            this.keyTypeInfo = keyTypeInfo;
            this.valueTypeInfo = valueTypeInfo;
        }

        public ObjectTypeInfo getKeyTypeInfo()
        {
            return keyTypeInfo;
        }

        public ObjectTypeInfo getValueTypeInfo()
        {
            return valueTypeInfo;
        }
    }

    public enum Type
    {
        PlainObject,
        Map,
        List,
    }

    public WrappedObject getParent()
    {
        return parent;
    }

    public Field getFiledInParent()
    {
        return filedInParent;
    }

    public ObjectTypeInfo getTypeInfo()
    {
        return typeInfo;
    }

    public Object getObject()
    {
        return object;
    }

    public <T> T getAs()
    {
        return (T) object;
    }

    public static WrappedObject wrapForJson(Object object){
        return new WrappedObject(null, null, new ObjectTypeInfo(Type.PlainObject), object);
    }

    public static WrappedObject wrapRoot(Object object)
    {
        if (Map.class.isAssignableFrom(object.getClass()) || List.class.isAssignableFrom(object.getClass()))
        {
            throw new RuntimeException("unSupport type :" + object.getClass().getSimpleName());
        }
        return new WrappedObject(null, null, new ObjectTypeInfo(Type.PlainObject), object);
    }

    public static WrappedObject wrapNode(WrappedObject parent, Field filedInParent, Object object)
    {
        return new WrappedObject(parent, filedInParent, new ObjectTypeInfo(Type.PlainObject), object);
    }
}
