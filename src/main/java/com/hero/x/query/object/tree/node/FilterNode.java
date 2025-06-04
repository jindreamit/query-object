package com.hero.x.query.object.tree.node;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.exception.TreeEvaluateException;

import java.math.BigDecimal;
import java.util.List;

public class FilterNode extends AbstractNode<FilterNode, FilterType>
{

    public FilterNode(FilterType type, String path, Object value)
    {
        super(type, path, value);
    }

    public boolean evaluate(Context context, WrappedObject object)
    {
        if (object == null)
        {
            throw new TreeEvaluateException(String.format("object is null,path:%s", path));
        }
        switch (type)
        {
            case EQ ->
            {
                WrappedObject property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                return property.getObject().equals(this.value);
            }
            case NE ->
            {
                WrappedObject property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                return !property.getObject().equals(this.value);
            }
            case GT ->
            {
                WrappedObject property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                BigDecimal bd1 = new BigDecimal(property.getObject().toString());
                BigDecimal bd2 = new BigDecimal(this.value.toString());
                return bd1.compareTo(bd2) > 0;
            }
            case GTE ->
            {
                WrappedObject property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                BigDecimal bd1 = new BigDecimal(property.getObject().toString());
                BigDecimal bd2 = new BigDecimal(this.value.toString());
                return bd1.compareTo(bd2) >= 0;
            }
            case LT ->
            {
                WrappedObject property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                BigDecimal bd1 = new BigDecimal(property.getObject().toString());
                BigDecimal bd2 = new BigDecimal(this.value.toString());
                return bd1.compareTo(bd2) < 0;
            }
            case LTE ->
            {
                WrappedObject property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                BigDecimal bd1 = new BigDecimal(property.getObject().toString());
                BigDecimal bd2 = new BigDecimal(this.value.toString());
                return bd1.compareTo(bd2) <= 0;
            }
            case AND ->
            {
                // AND 节点，假设 this.children 是 List<ExprNode>
                for (FilterNode child : getChildren())
                {
                    if (!child.evaluate(context, object))
                    {
                        return false; // 任意子节点为 false，整个 AND 结果为 false
                    }
                }
                return true; // 所有子节点都为 true
            }
            case OR ->
            {
                for (FilterNode child : getChildren())
                {
                    if (child.evaluate(context, object))
                    {
                        return true; // 任意子节点为 true，整个 OR 结果为 true
                    }
                }
                return false; // 所有子节点都为 false
            }
            case ALL ->
            {
                WrappedObject property = context.getTree().getProperty(object, this.path);
                List<? super Object> list = property.getAs();
                for (Object o : list)
                {
                    WrappedObject element = WrappedObject.wrapListNode(property, property.getParameterizedType(), o);
                    for (FilterNode child : getChildren())
                    {
                        if (!child.evaluate(context, element))
                        {
                            return false;
                        }
                    }
                }
                return true;
            }
            case EXIST ->
            {
                WrappedObject property = context.getTree().getProperty(object, this.path);
                List<? super Object> list = property.getAs();
                for (Object o : list)
                {
                    WrappedObject element = WrappedObject.wrapListNode(property, property.getParameterizedType(), o);
                    for (FilterNode child : getChildren())
                    {
                        if (child.evaluate(context, element))
                        {
                            return true;
                        }
                    }
                }
                return false;
            }
            default -> throw new TreeEvaluateException("Unsupported node type: " + type);
        }
    }
}
