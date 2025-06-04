package com.hero.x.query.object.tree.node.handler;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.FilterNode;
import com.hero.x.query.object.tree.node.WrappedObject;

public interface IFilterHandler
{
    boolean evaluate(FilterNode filterNode, Context context, WrappedObject wrappedObject);
}
