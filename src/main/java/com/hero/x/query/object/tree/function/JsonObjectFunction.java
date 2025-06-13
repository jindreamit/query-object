package com.hero.x.query.object.tree.function;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hero.x.query.object.tree.IObjectFunction;
import com.hero.x.query.object.tree.exception.JsonFunctionException;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.filter.TypeUtil;

import java.util.List;

public class JsonObjectFunction implements IObjectFunction
{
    @Override
    public WrappedObject getProperty(WrappedObject o, String path)
    {
        checkPrimitiveType(o, path);
        WrappedObject t = o;
        List<String> pathList = splitPath(path);
        for (int i = 0; i < pathList.size(); i++)
        {
            String fieldName = pathList.get(i);
            if (i == pathList.size() - 1)
            {
                return fetchField(t, fieldName);
            }
            t = fetchField(o, fieldName);
        }
        return null;
    }

    @Override
    public void setProperty(WrappedObject o, String path, Object value)
    {
        checkPrimitiveType(o, path);
        WrappedObject t = o;
        List<String> pathList = splitPath(path);
        for (int i = 0; i < pathList.size(); i++)
        {
            String fieldName = pathList.get(i);
            if (i == pathList.size() - 1)
            {
                setPropertyInternal(value, t, fieldName);
                return;
            }
            t = fetchField(o, fieldName);
        }
    }

    private void setPropertyInternal(Object value, WrappedObject t, String fieldName)
    {
        if (t.getObject() instanceof JSONArray)
        {
            JSONArray jsonArray = t.getAs();
            jsonArray.set(Integer.parseInt(fieldName), value);
        } else if (t.getObject() instanceof JSONObject)
        {
            JSONObject jsonObject = t.getAs();
            jsonObject.put(fieldName, value);
        }
    }

    private WrappedObject fetchField(WrappedObject o, String fieldName)
    {
        if (o.getObject() instanceof JSONArray)
        {
            JSONArray jsonArray = o.getAs();
            return WrappedObject.wrapNode(o, jsonArray.get(Integer.parseInt(fieldName)), fieldName);
        } else if (o.getObject() instanceof JSONObject)
        {
            JSONObject jsonObject = o.getAs();
            return WrappedObject.wrapNode(o, jsonObject.get(fieldName), fieldName);
        } else
        {
            throw new JsonFunctionException(String.format("object is not JSONObject or JSONArray,type:%s", o.getObject().getClass()));
        }
    }

    private static void checkPrimitiveType(WrappedObject o, String path)
    {
        if (TypeUtil.isPrimitiveOrWrapperOrString(o.getObject().getClass()))
        {
            WrappedObject parent = o.getParent();
            throw new JsonFunctionException(String.format("Primitive type can not get or set property,objectType:%s,parentType:%s,fieldName:%s",
                o.getObject().getClass(),
                parent == null ? "null" : parent.getObject().getClass(),
                path
            ));
        }
    }

    @Override
    public void push(WrappedObject wrappedObject, Object value)
    {
        List<? super Object> list = wrappedObject.getAs();
        list.add(value);
    }
}
