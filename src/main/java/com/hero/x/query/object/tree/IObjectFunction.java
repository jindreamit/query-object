package com.hero.x.query.object.tree;

import com.hero.x.query.object.tree.node.WrappedObject;

import java.util.Arrays;
import java.util.List;

public interface IObjectFunction
{
    WrappedObject getProperty(WrappedObject wrappedObject, String path);
    void setProperty(WrappedObject o, String path, Object value);
    void push(WrappedObject wrappedObject,Object value);
    default List<String> splitPath(String path){
        return Arrays.stream(path.split("\\.")).toList();
    }
}
