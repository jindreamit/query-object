package com.hero.x.query.object.tree.node;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.exception.TreeEvaluateException;
import com.hero.x.query.object.tree.node.handler.IFilterHandler;
import com.hero.x.query.object.tree.node.handler.filter.ALL_FilterHandler;
import com.hero.x.query.object.tree.node.handler.filter.*;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class FilterNode extends AbstractNode<FilterNode, FilterType>
{
    private static final Map<FilterType, IFilterHandler> HANDLER_MAP;

    static
    {
        Map<FilterType, IFilterHandler> map = new EnumMap<>(FilterType.class);
        map.put(FilterType.EQ, new EQ_FilterHandler());
        map.put(FilterType.NE, new NE_FilterHandler());
        map.put(FilterType.GT, new GT_FilterHandler());
        map.put(FilterType.GTE, new GTE_FilterHandler());
        map.put(FilterType.LT, new LT_FilterHandler());
        map.put(FilterType.LTE, new LTE_FilterHandler());
        map.put(FilterType.AND, new And_FilterHandler());
        map.put(FilterType.OR, new OR_FilterHandler());
        map.put(FilterType.ALL, new ALL_FilterHandler());
        map.put(FilterType.EXIST, new EXIST_FilterHandler());
        HANDLER_MAP = Collections.unmodifiableMap(map);
    }

    public FilterNode(FilterType type, String path, Object value)
    {
        super(type, path, value);
    }

    public boolean evaluate(Context context, WrappedObject object)
    {
        if (object == null)
        {
            throw new TreeEvaluateException(String.format("object is null,path:%s", path));
        }
        IFilterHandler handler = HANDLER_MAP.get(type);
        if (handler == null)
        {
            throw new TreeEvaluateException("Unsupported node type: " + type);
        }
        return handler.evaluate(this, context, object);
    }

}
