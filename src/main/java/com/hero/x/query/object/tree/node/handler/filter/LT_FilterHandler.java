package com.hero.x.query.object.tree.node.handler.filter;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.exception.TreeEvaluateException;
import com.hero.x.query.object.tree.node.FilterNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IFilterHandler;

import java.math.BigDecimal;

public class LT_FilterHandler implements IFilterHandler
{
    @Override
    public boolean evaluate(FilterNode filterNode, Context context, WrappedObject wrappedObject)
    {
        WrappedObject property = context.getObjectFunction().getProperty(wrappedObject, filterNode.getPath());
        if (property == null)
        {
            throw new TreeEvaluateException(String.format("property is null,path:%s", filterNode.getPath()));
        }
        BigDecimal bd1 = new BigDecimal(property.getObject().toString());
        BigDecimal bd2 = new BigDecimal(filterNode.getValue().toString());
        return bd1.compareTo(bd2) < 0;
    }
}
