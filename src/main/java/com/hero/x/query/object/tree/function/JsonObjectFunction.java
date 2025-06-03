package com.hero.x.query.object.tree.function;


import com.alibaba.fastjson2.JSONObject;
import com.hero.x.query.object.tree.IObjectFunction;
import com.hero.x.query.object.tree.node.WrappedObject;

import java.util.List;

public class JsonObjectFunction implements IObjectFunction
{
    private void verifyType(Object o)
    {
        if (!(o instanceof WrappedObject))
        {
            throw new RuntimeException("unSupport type :" + o.getClass().getSimpleName());
        }
    }

    @Override
    public WrappedObject getProperty(WrappedObject o, String path)
    {
        verifyType(o);
        WrappedObject t = o;
        List<String> pathList = splitPath(path);
        for (int i = 0; i < pathList.size(); i++)
        {
            if (i == pathList.size() - 1)
            {
                JSONObject jsonObject = t.getAs();
                return WrappedObject.wrapForJson(jsonObject.get(pathList.get(i)));
            }
            JSONObject jsonObject = t.getAs();
            t = WrappedObject.wrapForJson(jsonObject.getJSONObject(pathList.get(i)));
        }
        return null;
    }

    @Override
    public void setProperty(WrappedObject o, String path, Object value)
    {
        verifyType(o);
        WrappedObject t = o;
        List<String> pathList = splitPath(path);
        for (int i = 0; i < pathList.size(); i++)
        {
            if (i == pathList.size() - 1)
            {
                JSONObject jsonObject = t.getAs();
                jsonObject.put(pathList.get(i), value);
                return;
            }
            JSONObject jsonObject = t.getAs();
            t = WrappedObject.wrapForJson(jsonObject.getJSONObject(pathList.get(i)));
        }
    }
}
