package com.hero.x.query.object.tree.node.handler.expr;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.ExprNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IExprHandler;

public class SET_ExprHandler implements IExprHandler
{
    @Override
    public void apply(ExprNode exprNode, Context context, WrappedObject wrappedObject)
    {
        context.getObjectFunction().setProperty(wrappedObject, exprNode.getPath(), exprNode.getValue());
    }
}
