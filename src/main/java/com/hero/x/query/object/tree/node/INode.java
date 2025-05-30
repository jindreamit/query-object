package com.hero.x.query.object.tree.node;

import java.util.List;

public interface INode<T,E>
{
    E type();

    List<T> getChildren();

    void addChild(T child);
}
