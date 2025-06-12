package com.hero.x.query.object.tree.node.handler.filter;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.FilterNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IFilterHandler;

import java.util.List;

public class ALL_FilterHandler implements IFilterHandler
{
    @Override
    public boolean evaluate(FilterNode filterNode, Context context, WrappedObject wrappedObject)
    {
        WrappedObject property = context.getObjectFunction().getProperty(wrappedObject, filterNode.getPath());
        List<? super Object> list = property.getAs();
        int i = 0;
        for (Object o : list)
        {
            WrappedObject element = WrappedObject.wrapListNode(property, property.getParameterizedType(), o, String.valueOf(i));
            for (FilterNode child : filterNode.getChildren())
            {
                if (!child.evaluate(context, element))
                {
                    return false;
                }
            }
            i++;
        }
        return true;
    }
}
