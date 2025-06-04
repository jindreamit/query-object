package com.hero.x.query.object.tree.node;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.exception.TreeEvaluateException;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public class ExprNode extends AbstractNode<ExprNode, ExprType>
{
    public ExprNode(ExprType type, String path, Object value)
    {
        super(type, path, value);
    }

    public void apply(Context context, WrappedObject wrappedObject)
    {
        if (wrappedObject == null)
        {
            throw new TreeEvaluateException("object is null, path: " + path);
        }
        switch (type)
        {
            case SET:
            {
                context.getObjectFunction().setProperty(wrappedObject, this.path, this.value);
                break;
            }
            case INC:
            {
                Object currentValue = context.getObjectFunction().getProperty(wrappedObject, this.path).getObject();
                Object result = calculateAndPreserveType(currentValue, value, BigDecimal::add);
                context.getObjectFunction().setProperty(wrappedObject, this.path, result);
                break;
            }
            case MUL:
            {
                Object currentValue = context.getObjectFunction().getProperty(wrappedObject, this.path).getObject();
                Object result = calculateAndPreserveType(currentValue, value, BigDecimal::multiply);
                context.getObjectFunction().setProperty(wrappedObject, this.path, result);
                break;
            }
            case MIN:
            {
                Object currentValue = context.getObjectFunction().getProperty(wrappedObject, this.path).getObject();
                if (currentValue == null)
                {
                    context.getObjectFunction().setProperty(wrappedObject, this.path, value);
                } else
                {
                    BigDecimal current = new BigDecimal(currentValue.toString());
                    BigDecimal compare = new BigDecimal(value.toString());
                    if (compare.compareTo(current) < 0)
                    {
                        // 取更小值，保持类型
                        Object result = calculateAndPreserveType(currentValue, value, (a, b) -> b);
                        context.getObjectFunction().setProperty(wrappedObject, this.path, result);
                    }
                }
                break;
            }
            case MAX:
            {
                Object currentValue = context.getObjectFunction().getProperty(wrappedObject, this.path).getObject();
                if (currentValue == null)
                {
                    context.getObjectFunction().setProperty(wrappedObject, this.path, value);
                } else
                {
                    BigDecimal current = new BigDecimal(currentValue.toString());
                    BigDecimal compare = new BigDecimal(value.toString());
                    if (compare.compareTo(current) > 0)
                    {
                        // 取更大值，保持类型
                        Object result = calculateAndPreserveType(currentValue, value, (a, b) -> b);
                        context.getObjectFunction().setProperty(wrappedObject, this.path, result);
                    }
                }
                break;
            }
            default:
            {
                throw new UnsupportedOperationException("Unsupported type: " + type);
            }
        }
    }

    @Override
    public void addChild(ExprNode child)
    {
        throw new UnsupportedOperationException("ExprNode cannot have children");
    }

    private Object calculateAndPreserveType(Object currentValue, Object operand,
                                            BiFunction<BigDecimal, BigDecimal, BigDecimal> operation)
    {
        if (currentValue == null)
        {
            currentValue = 0;
        }
        BigDecimal current = new BigDecimal(currentValue.toString());
        BigDecimal op = new BigDecimal(operand.toString());
        BigDecimal result = operation.apply(current, op);
        if (currentValue instanceof Integer)
        {
            return result.intValue();
        } else if (currentValue instanceof Long)
        {
            return result.longValue();
        } else if (currentValue instanceof Double)
        {
            return result.doubleValue();
        } else if (currentValue instanceof Float)
        {
            return result.floatValue();
        } else if (currentValue instanceof Short)
        {
            return result.shortValue();
        } else if (currentValue instanceof Byte)
        {
            return result.byteValue();
        } else
        {
            return result;
        }
    }

}
