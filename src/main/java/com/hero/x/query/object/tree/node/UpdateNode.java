package com.hero.x.query.object.tree.node;

import com.hero.x.query.object.tree.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UpdateNode extends AbstractNode<UpdateNode, UpdateType>
{
    private final FilterNode filterNode;
    private final List<ExprNode> exprNodeList;

    public UpdateNode(UpdateType type, String path, Object value, FilterNode filterNode, List<ExprNode> exprNodeList)
    {
        super(type, path, value);
        this.filterNode = filterNode;
        this.exprNodeList = exprNodeList;
    }

    public void apply(Context context, WrappedObject wrappedObject)
    {
        switch (type())
        {
            case UpdateObject:
            {
                if (evaluate(context, wrappedObject))
                {
                    for (ExprNode exprNode : exprNodeList)
                    {
                        exprNode.apply(context, wrappedObject);
                    }
                }
                return;
            }
            case VisitArray:
            {
                WrappedObject wrappedList = context.getTree().getProperty(wrappedObject, this.path);
                List<? super Object> list = (List<? super Object>) wrappedList.getObject();
                List<ExprNode> primitiveExprNodeList = new ArrayList<>();
                List<ExprNode> pullExprNodeList = new ArrayList<>();
                for (ExprNode exprNode : exprNodeList)
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
                    for (Iterator<?> iterator = list.iterator(); iterator.hasNext(); )
                    {
                        Object o = iterator.next();
                        WrappedObject wrappedElement = WrappedObject.wrapNode(wrappedObject, o);
                        if (evaluate(context, wrappedElement))
                        {
                            iterator.remove();
                        }
                    }
                }
                for (Object o : list)
                {
                    WrappedObject wrappedElement = WrappedObject.wrapNode(wrappedObject, o);
                    if (evaluate(context, wrappedElement))
                    {
                        for (ExprNode exprNode : primitiveExprNodeList)
                        {
                            exprNode.apply(context, wrappedElement);
                        }
                        for (UpdateNode updateNode : getChildren())
                        {
                            updateNode.apply(context, wrappedElement);
                        }
                    }
                }
                return;
            }
            case AppendArray:
            {
                WrappedObject wrappedList = context.getTree().getProperty(wrappedObject, this.path);
                if (evaluate(context, wrappedObject))
                {
                    for (ExprNode exprNode : exprNodeList)
                    {
                        context.getTree().push(wrappedList, exprNode.value);
                    }
                }
                return;
            }
        }
    }

    private boolean evaluate(Context context, WrappedObject wrappedObject)
    {
        if (filterNode == null)
        {
            return true;
        }
        return filterNode.evaluate(context, wrappedObject);
    }

}
