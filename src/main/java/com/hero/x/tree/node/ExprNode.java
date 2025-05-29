package com.hero.x.tree.node;

import com.hero.x.tree.Context;
import com.hero.x.tree.exception.TreeEvaluateException;

import java.math.BigDecimal;

public class ExprNode extends AbstractNode<ExprNode, ExprType>
{
    public ExprNode(ExprType type, String path, Object value)
    {
        super(type, path, value);
    }

    public void apply(Context context, Object object)
    {
        if (object == null)
        {
            throw new TreeEvaluateException("object is null, path: " + path);
        }


        switch (type)
        {
            case SET ->
            {
                context.getTree().setProperty(object, this.path, this.value);
            }

            case INC ->
            {
                Object currentValue = context.getTree().getProperty(object, this.path);
                if (currentValue == null)
                {
                    currentValue = 0;
                }
                BigDecimal current = new BigDecimal(currentValue.toString());
                BigDecimal increment = new BigDecimal(value.toString());
                context.getTree().setProperty(object, this.path, current.add(increment));
            }

            case MUL ->
            {
                Object currentValue = context.getTree().getProperty(object, this.path);
                if (currentValue == null)
                {
                    currentValue = 0;
                }
                BigDecimal current = new BigDecimal(currentValue.toString());
                BigDecimal multiplier = new BigDecimal(value.toString());
                context.getTree().setProperty(object, this.path, current.multiply(multiplier));
            }

            case MIN ->
            {
                Object currentValue = context.getTree().getProperty(object, this.path);
                if (currentValue == null)
                {
                    context.getTree().setProperty(object, this.path, value);
                } else
                {
                    BigDecimal current = new BigDecimal(currentValue.toString());
                    BigDecimal compare = new BigDecimal(value.toString());
                    if (compare.compareTo(current) < 0)
                    {
                        context.getTree().setProperty(object, this.path, value);
                    }
                }
            }

            case MAX ->
            {
                Object currentValue = context.getTree().getProperty(object, this.path);
                if (currentValue == null)
                {
                    context.getTree().setProperty(object, this.path, value);
                } else
                {
                    BigDecimal current = new BigDecimal(currentValue.toString());
                    BigDecimal compare = new BigDecimal(value.toString());
                    if (compare.compareTo(current) > 0)
                    {
                        context.getTree().setProperty(object, this.path, value);
                    }
                }
            }

            default -> throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

    @Override
    public void addChild(ExprNode child)
    {
        throw new UnsupportedOperationException("ExprNode cannot have children");
    }
}
