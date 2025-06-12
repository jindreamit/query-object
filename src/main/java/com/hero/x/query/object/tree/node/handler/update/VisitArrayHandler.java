package com.hero.x.query.object.tree.node.handler.update;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.ExprNode;
import com.hero.x.query.object.tree.node.ExprType;
import com.hero.x.query.object.tree.node.UpdateNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IUpdateHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VisitArrayHandler implements IUpdateHandler
{
    @Override
    public void apply(UpdateNode updateNode, Context context, WrappedObject wrappedObject)
    {
        WrappedObject wrappedList = context.getObjectFunction().getProperty(wrappedObject, updateNode.getPath());
        List<? super Object> list = (List<? super Object>) wrappedList.getObject();
        List<ExprNode> primitiveExprNodeList = new ArrayList<>();
        List<ExprNode> pullExprNodeList = new ArrayList<>();
        for (ExprNode exprNode : updateNode.getExprNodeList())
        {
            if (exprNode.type().equals(ExprType.PULL))
            {
                pullExprNodeList.add(exprNode);
            } else
            {
                primitiveExprNodeList.add(exprNode);
            }
        }
        if (!pullExprNodeList.isEmpty())
        {
            int i = 0;
            for (Iterator<?> iterator = list.iterator(); iterator.hasNext(); )
            {
                Object o = iterator.next();
                WrappedObject wrappedElement = WrappedObject.wrapNode(wrappedObject, o, String.valueOf(i));
                if (evaluate(updateNode.getFilterNode(), context, wrappedElement))
                {
                    iterator.remove();
                }
                i++;
            }
        }
        int i = 0;
        for (Object o : list)
        {
            WrappedObject wrappedElement = WrappedObject.wrapNode(wrappedObject, o, String.valueOf(i));
            if (evaluate(updateNode.getFilterNode(), context, wrappedElement))
            {
                for (ExprNode exprNode : primitiveExprNodeList)
                {
                    exprNode.apply(context, wrappedElement);
                }
                for (UpdateNode childUpdateNode : updateNode.getChildren())
                {
                    childUpdateNode.apply(context, wrappedElement);
                }
            }
            i++;
        }
    }
}
