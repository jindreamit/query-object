package com.hero.x.query.object.tree;

import com.hero.x.query.object.tree.node.UpdateNode;

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
