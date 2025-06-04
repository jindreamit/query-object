package com.hero.x.query.object.tree.node.handler.expr;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public class CalculateUtil
{
    public static Object calculateAndPreserveType(Object currentValue, Object operand,
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
