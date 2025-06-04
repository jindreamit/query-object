package com.hero.x.query.object.tree.node.handler.expr;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.ExprNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IExprHandler;

import java.math.BigDecimal;

public class MAX_ExprHandler implements IExprHandler
{
    @Override
    public void apply(ExprNode exprNode, Context context, WrappedObject wrappedObject)
    {
        Object currentValue = context.getObjectFunction().getProperty(wrappedObject, exprNode.getPath()).getObject();
        if (currentValue == null)
        {
            context.getObjectFunction().setProperty(wrappedObject, exprNode.getPath(), exprNode.getValue());
        } else
        {
            BigDecimal current = new BigDecimal(currentValue.toString());
            BigDecimal compare = new BigDecimal(exprNode.getValue().toString());
            if (compare.compareTo(current) > 0)
            {
                // 取更大值，保持类型
                Object result = CalculateUtil.calculateAndPreserveType(currentValue, exprNode.getValue(), (a, b) -> b);
                context.getObjectFunction().setProperty(wrappedObject, exprNode.getPath(), result);
            }
        }
    }
}
