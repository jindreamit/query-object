package com.hero.x.query.object.tree;

import com.hero.x.query.object.tree.node.UpdateNode;
import com.hero.x.query.object.tree.node.WrappedObject;

public class QueryTree
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
        WrappedObject wrappedObject = WrappedObject.wrapRoot(object);
        apply(wrappedObject);
    }

    public void apply(WrappedObject wrappedObject)
    {
        Context context = new Context(this, wrappedObject);
        root.apply(context, wrappedObject);
    }

    public IObjectFunction getObjectFunction()
    {
        return objectFunction;
    }
}
