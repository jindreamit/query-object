package com.hero.x.query.object.tree.node.handler;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.FilterNode;
import com.hero.x.query.object.tree.node.UpdateNode;
import com.hero.x.query.object.tree.node.WrappedObject;

public interface IUpdateHandler
{
    default boolean evaluate(FilterNode filterNode, Context context, WrappedObject wrappedObject)
    {
        if (filterNode == null)
        {
            return true;
        }
        return filterNode.evaluate(context, wrappedObject);
    }

    void apply(UpdateNode updateNode, Context context, WrappedObject wrappedObject);
}
