package com.hero.x.tree;

public class Context
{
    private final QueryTree tree;
    private final Object object;

    public Context(QueryTree tree, Object object)
    {
        this.tree = tree;
        this.object = object;
    }

    public QueryTree getTree()
    {
        return tree;
    }

    public Object getObject()
    {
        return object;
    }
}
