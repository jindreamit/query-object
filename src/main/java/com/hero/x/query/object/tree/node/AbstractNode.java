package com.hero.x.query.object.tree.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractNode<T,E> implements INode<T,E>
{
    protected final E type;
    protected final String path;
    protected final Object value;
    private List<T> children;

    protected AbstractNode(E type, String path, Object value)
    {
        this.type = type;
        this.path = path;
        this.value = value;
    }

    @Override
    public E type()
    {
        return type;
    }

    public List<T> getChildren()
    {
        return Objects.requireNonNullElse(children, Collections.emptyList());
    }

    public void addChild(T child)
    {
        if (children == null)
        {
            children = new ArrayList<T>();
        }
        children.add(child);
    }

    public String getPath()
    {
        return path;
    }

    public Object getValue()
    {
        return value;
    }
}
