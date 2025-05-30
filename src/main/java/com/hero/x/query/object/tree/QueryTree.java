package com.hero.x.query.object.tree;

import com.hero.x.query.object.tree.node.UpdateNode;

import java.util.List;

public class QueryTree implements IObjectFunction
{
    private final UpdateNode root;
    private final IObjectFunction objectFunction;

    public QueryTree(UpdateNode root, IObjectFunction objectFunction)
    {
        this.root = root;
        this.objectFunction = objectFunction;
    }

    public UpdateNode getRoot()
    {
        return root;
    }

    public void apply(Object object)
    {
        Context context = new Context(this, object);
        root.apply(context, object);
    }

    @Override
    public List<? super Object> getList(Object o, String path)
    {
        return objectFunction.getList(o, path);
    }

    @Override
    public Object getProperty(Object o, String path)
    {
        return objectFunction.getProperty(o, path);
    }

    @Override
    public void setProperty(Object o, String path, Object value)
    {
        objectFunction.setProperty(o, path, value);
    }
}
