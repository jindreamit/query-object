package com.hero.x.query.object.tree.node;

public enum ExprType
{
    SET(true),INC(true),MUL(true),MIN(true),MAX(true),
    PUSH(false),ADD_TO_SET(false),PULL(false);
    private final boolean primitive;

    ExprType(boolean primitive)
    {
        this.primitive = primitive;
    }

    public boolean isPrimitive()
    {
        return primitive;
    }
}
