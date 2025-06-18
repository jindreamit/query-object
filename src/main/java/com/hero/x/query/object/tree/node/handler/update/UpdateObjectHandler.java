package com.hero.x.query.object.tree.node.handler.update;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.ExprNode;
import com.hero.x.query.object.tree.node.UpdateNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IUpdateHandler;

public class UpdateObjectHandler implements IUpdateHandler
{
    @Override
    public void apply(UpdateNode updateNode, Context context, WrappedObject wrappedObject)
    {
        if (evaluate(updateNode.getFilterNode(), context, wrappedObject))
        {
            for (ExprNode exprNode : updateNode.getExprNodeList())
            {
                exprNode.apply(context, wrappedObject);
            }
            for (UpdateNode child : updateNode.getChildren())
            {
                child.apply(context, wrappedObject);
            }
        }
    }
}
