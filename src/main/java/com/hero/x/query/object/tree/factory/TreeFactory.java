package com.hero.x.query.object.tree.factory;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hero.x.query.object.tree.exception.BuildTreeException;
import com.hero.x.query.object.tree.IObjectFunction;
import com.hero.x.query.object.tree.QueryTree;
import com.hero.x.query.object.tree.node.FilterNode;
import com.hero.x.query.object.tree.node.ExprNode;
import com.hero.x.query.object.tree.node.ExprType;
import com.hero.x.query.object.tree.node.FilterType;
import com.hero.x.query.object.tree.node.UpdateNode;
import com.hero.x.query.object.tree.node.UpdateType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TreeFactory
{
    public QueryTree buildFromJson(String json, IObjectFunction objectFunction)
    {
        JSONObject jsonObject = JSON.parseObject(json);
        return new QueryTree(buildUpdateNodeFromJson(jsonObject), objectFunction);
    }

    private UpdateNode buildUpdateNodeFromJson(JSONObject jsonObject)
    {
        String type = jsonObject.getString("type");
        UpdateType updateType = UpdateType.valueOf(type);
        UpdateNode updateNode = buildUpdateNodeInternal(jsonObject, updateType);
        switch (updateType)
        {
            case UpdateObject:
            case AppendArray:
            {
                return updateNode;
            }
            case VisitArray:
            {

                JSONArray children = jsonObject.getJSONArray("children");
                if (children != null)
                {
                    for (int i = 0; i < children.size(); i++)
                    {
                        JSONObject childJson = children.getJSONObject(i);
                        updateNode.addChild(buildUpdateNodeFromJson(childJson));
                    }
                }
                return updateNode;
            }
        }

        throw new BuildTreeException("unhandled update type :" + type);
    }

    private UpdateNode buildUpdateNodeInternal(JSONObject jsonObject, UpdateType updateType)
    {
        FilterNode filterNode = buildFilterNodeFromJson(jsonObject.getJSONObject("filter"));
        JSONArray array = jsonObject.getJSONArray("update");
        List<ExprNode> exprNodeArrayList = Collections.emptyList();
        if (array != null && !array.isEmpty())
        {
            exprNodeArrayList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++)
            {
                JSONObject updateJson = array.getJSONObject(i);
                ExprNode exprNode = buildExprNodeFromJson(updateJson);
                exprNodeArrayList.add(exprNode);
            }
        }
        return new UpdateNode(updateType, jsonObject.getString("key"), jsonObject.get("value"), filterNode, exprNodeArrayList);
    }

    private ExprNode buildExprNodeFromJson(JSONObject jsonObject)
    {
        String operator = jsonObject.getString("operator");
        ExprType exprType = ExprType.valueOf(operator);
        ExprNode exprNode = new ExprNode(exprType, jsonObject.getString("key"), jsonObject.get("value"));
        switch (exprType)
        {
            case INC:
            case SET:
            case MIN:
            case MAX:
            case MUL:
            case PULL:
            case PUSH:
            {
                return exprNode;
            }
        }
        throw new BuildTreeException("unhandled expr type :" + operator);
    }

    private FilterNode buildFilterNodeFromJson(JSONObject jsonObject)
    {
        if (jsonObject.isEmpty())
        {
            return null;
        }
        String operator = jsonObject.getString("operator");
        FilterType filterType = FilterType.valueOf(operator);
        FilterNode filterNode = new FilterNode(filterType, jsonObject.getString("key"), jsonObject.get("value"));
        switch (filterType)
        {
            case NE:
            case GT:
            case GTE:
            case LT:
            case LTE:
            case EQ:
            {
                return filterNode;
            }
            case ALL:
            case EXIST:
            {
                JSONObject filterValueJson = jsonObject.getJSONObject("value");
                filterNode.addChild(buildFilterNodeFromJson(filterValueJson));
                return filterNode;
            }
            case OR:
            case AND:
            {
                JSONArray array = jsonObject.getJSONArray("value");
                for (int i = 0; i < array.size(); i++)
                {
                    JSONObject subObj = array.getJSONObject(i);
                    filterNode.addChild(buildFilterNodeFromJson(subObj));
                }
                return filterNode;
            }
        }
        throw new BuildTreeException("unhandled compare operator type :" + operator);
    }
}
