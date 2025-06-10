package com.hero.x.query.object.tree.node;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.exception.BuildTreeException;
import com.hero.x.query.object.tree.exception.TreeEvaluateException;
import com.hero.x.query.object.tree.node.handler.IExprHandler;
import com.hero.x.query.object.tree.node.handler.expr.*;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ExprNode extends AbstractNode<ExprNode, ExprType>
{
    private static final Map<ExprType, IExprHandler> HANDLER_MAP;

    static
    {
        Map<ExprType, IExprHandler> map = new EnumMap<>(ExprType.class);
        map.put(ExprType.MAX, new MAX_ExprHandler());
        map.put(ExprType.MIN, new MIN_ExprHandler());
        map.put(ExprType.MUL, new MUL_ExprHandler());
        map.put(ExprType.INC, new INC_ExprHandler());
        map.put(ExprType.SET, new SET_ExprHandler());
        HANDLER_MAP = Collections.unmodifiableMap(map); // 如果你想要不可变
    }

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
        IExprHandler handler = HANDLER_MAP.get(type);
        if (handler == null)
        {
            throw new UnsupportedOperationException("Unsupported type: " + type);
        }
        handler.apply(this, context, wrappedObject);
    }

    @Override
    public void addChild(ExprNode child)
    {
        throw new BuildTreeException("ExprNode cannot have children");
    }

}
