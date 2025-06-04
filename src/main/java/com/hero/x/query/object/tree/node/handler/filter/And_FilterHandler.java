package com.hero.x.query.object.tree.node.handler.filter;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.FilterNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IFilterHandler;

public class And_FilterHandler implements IFilterHandler
{
    @Override
    public boolean evaluate(FilterNode filterNode, Context context, WrappedObject wrappedObject)
    {
        for (FilterNode child : filterNode.getChildren())
        {
            if (!child.evaluate(context, wrappedObject))
            {
                return false;
            }
        }
        return true;
    }
}
