package com.hero.x.query.object.tree.node.handler.expr;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.ExprNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IExprHandler;

import java.math.BigDecimal;

public class INC_ExprHandler implements IExprHandler
{
    @Override
    public void apply(ExprNode exprNode, Context context, WrappedObject wrappedObject)
    {
        Object currentValue = context.getObjectFunction().getProperty(wrappedObject, exprNode.getPath()).getObject();
        Object result = CalculateUtil.calculateAndPreserveType(currentValue, exprNode.getValue(), BigDecimal::add);
        context.getObjectFunction().setProperty(wrappedObject, exprNode.getPath(), result);
    }
}
