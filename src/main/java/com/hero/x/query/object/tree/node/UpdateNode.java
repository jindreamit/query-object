package com.hero.x.query.object.tree.node;

import com.hero.x.query.object.tree.Context;
import com.hero.x.query.object.tree.exception.TreeEvaluateException;
import com.hero.x.query.object.tree.node.handler.IUpdateHandler;
import com.hero.x.query.object.tree.node.handler.update.*;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class UpdateNode extends AbstractNode<UpdateNode, UpdateType>
{
    private static final Map<UpdateType, IUpdateHandler> HANDLER_MAP;

    static
    {
        EnumMap<UpdateType, IUpdateHandler> map = new EnumMap<>(UpdateType.class);
        map.put(UpdateType.UpdateObject, new UpdateObjectHandler());
        map.put(UpdateType.VisitArray, new VisitArrayHandler());
        map.put(UpdateType.AppendArray, new AppendArrayHandler());
        HANDLER_MAP = Collections.unmodifiableMap(map);
    }

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
        IUpdateHandler handler = HANDLER_MAP.get(type);
        if (handler == null)
        {
            throw new TreeEvaluateException("Unsupported node type: " + type);
        }
        handler.apply(this, context, wrappedObject);
    }

    public FilterNode getFilterNode()
    {
        return filterNode;
    }

    public List<ExprNode> getExprNodeList()
    {
        return exprNodeList;
    }

    @Override
    public void addChild(UpdateNode child)
    {
        if (type() != UpdateType.VisitArray)
        {
            throw new TreeEvaluateException("Only VisitArray node can have children");
        }
        super.addChild(child);
    }
}
