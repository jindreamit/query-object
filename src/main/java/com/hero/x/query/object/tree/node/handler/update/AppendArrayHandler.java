package com.hero.x.query.object.tree.node.handler.update;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.ExprNode;
import com.hero.x.query.object.tree.node.UpdateNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IUpdateHandler;

public class AppendArrayHandler implements IUpdateHandler
{
    @Override
    public void apply(UpdateNode updateNode, Context context, WrappedObject wrappedObject)
    {
        WrappedObject wrappedList = context.getObjectFunction().getProperty(wrappedObject, updateNode.getPath());
        if (evaluate(updateNode.getFilterNode(), context, wrappedObject))
        {
            for (ExprNode exprNode : updateNode.getExprNodeList())
            {
                context.getObjectFunction().push(wrappedList, exprNode.getValue());
            }
        }
    }
}
