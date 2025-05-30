package com.hero.x.query.object.tree;

import java.util.Arrays;
import java.util.List;

public interface IObjectFunction
{
    List<? super Object> getList(Object o, String path);
    Object getProperty(Object o, String path);
    void setProperty(Object o, String path, Object value);
    default List<String> splitPath(String path){
        return Arrays.stream(path.split("\\.")).toList();
    }
}
