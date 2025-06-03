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

    public void apply(Context context, Object object)
    {
        switch (type())
        {
            case UpdateObject:
            {
                if (evaluate(context, object))
                {
                    for (ExprNode exprNode : exprNodeList)
                    {
                        exprNode.apply(context, object);
                    }
                }
                return;
            }
            case UpdateArray:
            {
                List<? super Object> list =(List<? super Object>) context.getTree().getProperty(object, this.path);
                List<ExprNode> primitiveExprNodeList = new ArrayList<>();
                List<ExprNode> notPrimitiveExprNodeList = new ArrayList<>();
                for (ExprNode exprNode : exprNodeList)
                {
                    if (exprNode.type().isPrimitive())
                    {
                        primitiveExprNodeList.add(exprNode);
                    } else
                    {
                        notPrimitiveExprNodeList.add(exprNode);
                    }
                }
                for (ExprNode exprNode : notPrimitiveExprNodeList)
                {
                    if (exprNode.type() == ExprType.PUSH)
                    {
                        list.add(exprNode.value);
                    }
                }
                for (Iterator<?> iterator = list.iterator(); iterator.hasNext(); )
                {
                    Object o = iterator.next();
                    if (evaluate(context, o))
                    {
                        for (ExprNode exprNode : notPrimitiveExprNodeList)
                        {
                            if (exprNode.type() == ExprType.PULL)
                            {
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
                for (Object o : list)
                {
                    if (evaluate(context, o))
                    {
                        for (ExprNode exprNode : primitiveExprNodeList)
                        {
                            exprNode.apply(context, o);
                        }
                        for (UpdateNode updateNode : getChildren())
                        {
                            updateNode.apply(context, o);
                        }
                    }
                }
            }
        }
    }

    private boolean evaluate(Context context, Object object)
    {
        if (filterNode == null)
        {
            return true;
        }
        return filterNode.evaluate(context, object);
    }

}
