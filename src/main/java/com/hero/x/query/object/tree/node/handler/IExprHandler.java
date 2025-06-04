package com.hero.x.query.object.tree.node.handler;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.ExprNode;
import com.hero.x.query.object.tree.node.WrappedObject;

public interface IExprHandler
{
    void apply(ExprNode exprNode, Context context, WrappedObject wrappedObject);
}
