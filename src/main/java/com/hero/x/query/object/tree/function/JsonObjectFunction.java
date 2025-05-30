package com.hero.x.query.object.tree.function;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hero.x.query.object.tree.IObjectFunction;

import java.util.List;

public class JsonObjectFunction implements IObjectFunction
{
    private void verifyType(Object o)
    {
        if (!(o instanceof JSONObject))
        {
            throw new RuntimeException("unSupport type :" + o.getClass().getSimpleName());
        }
    }

    @Override
    public List<? super Object> getList(Object o, String path)
    {
        verifyType(o);
        JSONObject t = (JSONObject) o;
        List<String> pathList = splitPath(path);
        for (int i = 0; i < pathList.size(); i++)
        {
            if (i == pathList.size() - 1)
            {
                return t.getJSONArray(pathList.get(i));
            }
            t = t.getJSONObject(pathList.get(i));
        }
        return new JSONArray();
    }

    @Override
    public Object getProperty(Object o, String path)
    {
        verifyType(o);
        JSONObject t = (JSONObject) o;
        List<String> pathList = splitPath(path);
        for (int i = 0; i < pathList.size(); i++)
        {
            if (i == pathList.size() - 1)
            {
                return t.get(pathList.get(i));
            }
            t = t.getJSONObject(pathList.get(i));
        }
        return null;
    }

    @Override
    public void setProperty(Object o, String path, Object value)
    {
        verifyType(o);
        JSONObject t = (JSONObject) o;
        List<String> pathList = splitPath(path);
        for (int i = 0; i < pathList.size(); i++)
        {
            if (i == pathList.size() - 1)
            {
                t.put(pathList.get(i), value);
                return;
            }
            t = t.getJSONObject(pathList.get(i));
        }
    }
}
