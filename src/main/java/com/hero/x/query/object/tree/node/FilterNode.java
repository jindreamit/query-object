package com.hero.x.query.object.tree.node;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.exception.TreeEvaluateException;

import java.math.BigDecimal;

public class FilterNode extends AbstractNode<FilterNode, FilterType>
{

    public FilterNode(FilterType type, String path, Object value)
    {
        super(type, path, value);
    }
    public boolean evaluate(Context context, Object object)
    {
        if (object == null)
        {
            throw new TreeEvaluateException(String.format("object is null,path:%s", path));
        }
        switch (type)
        {
            case EQ ->
            {
                Object property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                return property.equals(this.value);
            }
            case NE ->
            {
                Object property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                return !property.equals(this.value);
            }
            case GT ->
            {
                Object property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                BigDecimal bd1 = new BigDecimal(object.toString());
                BigDecimal bd2 = new BigDecimal(property.toString());
                return bd1.compareTo(bd2) > 0;
            }
            case GTE ->
            {
                Object property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                BigDecimal bd1 = new BigDecimal(property.toString());
                BigDecimal bd2 = new BigDecimal(this.value.toString());
                return bd1.compareTo(bd2) >= 0;
            }
            case LT ->
            {
                Object property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                BigDecimal bd1 = new BigDecimal(property.toString());
                BigDecimal bd2 = new BigDecimal(this.value.toString());
                return bd1.compareTo(bd2) < 0;
            }
            case LTE ->
            {
                Object property = context.getTree().getProperty(object, this.path);
                if (property == null)
                {
                    throw new TreeEvaluateException(String.format("property is null,path:%s", path));
                }
                BigDecimal bd1 = new BigDecimal(property.toString());
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
            default -> throw new TreeEvaluateException("Unsupported node type: " + type);
        }
    }
}
