package com.hero.x.query.object.tree.function;


import com.alibaba.fastjson2.JSONObject;
import com.hero.x.query.object.tree.IObjectFunction;
import com.hero.x.query.object.tree.node.WrappedObject;

import java.util.List;

public class JsonObjectFunction implements IObjectFunction
{
    @Override
    public WrappedObject getProperty(WrappedObject o, String path)
    {
        WrappedObject t = o;
        List<String> pathList = splitPath(path);
        for (int i = 0; i < pathList.size(); i++)
        {
            String fieldName = pathList.get(i);
            if (i == pathList.size() - 1)
            {
                JSONObject jsonObject = t.getAs();
                return WrappedObject.wrapNode(t, jsonObject.get(fieldName), fieldName);
            }
            JSONObject jsonObject = t.getAs();
            t = WrappedObject.wrapNode(t, jsonObject.getJSONObject(fieldName), fieldName);
        }
        return null;
    }

    @Override
    public void setProperty(WrappedObject o, String path, Object value)
    {
        WrappedObject t = o;
        List<String> pathList = splitPath(path);
        for (int i = 0; i < pathList.size(); i++)
        {
            String filedName = pathList.get(i);
            if (i == pathList.size() - 1)
            {
                JSONObject jsonObject = t.getAs();
                jsonObject.put(filedName, value);
                return;
            }
            JSONObject jsonObject = t.getAs();
            t = WrappedObject.wrapNode(o, jsonObject.getJSONObject(pathList.get(i)), filedName);
        }
    }

    @Override
    public void push(WrappedObject wrappedObject, Object value)
    {
        List<? super Object> list = wrappedObject.getAs();
        list.add(value);
    }
}
