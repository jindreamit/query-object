package com.hero.x.query.object.tree.node.handler.update;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.node.ExprNode;
import com.hero.x.query.object.tree.node.ExprType;
import com.hero.x.query.object.tree.node.UpdateNode;
import com.hero.x.query.object.tree.node.WrappedObject;
import com.hero.x.query.object.tree.node.handler.IUpdateHandler;

import java.util.ArrayList;
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
            for (int i = 0; i < list.size(); i++)
            {
                String fieldName = String.valueOf(i);
                WrappedObject wrappedElement = context.getObjectFunction().getProperty(wrappedList, fieldName);
                if (evaluate(updateNode.getFilterNode(), context, wrappedElement))
                {
                    list.remove(wrappedElement.getObject());
                    wrappedList.removeField(fieldName);
                    i--;
                }
            }
        }
        for (int i = 0; i < list.size(); i++)
        {
            WrappedObject wrappedElement = context.getObjectFunction().getProperty(wrappedList, String.valueOf(i));
            if (evaluate(updateNode.getFilterNode(), context, wrappedElement))
            {
                if (!primitiveExprNodeList.isEmpty())
                {
                    for (ExprNode exprNode : primitiveExprNodeList)
                    {
                        exprNode.apply(context, wrappedElement);
                    }
                }
                for (UpdateNode childUpdateNode : updateNode.getChildren())
                {
                    childUpdateNode.apply(context, wrappedElement);
                }
            }
        }
    }
}
