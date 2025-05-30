package test;

import com.alibaba.fastjson2.JSONObject;
import com.hero.x.query.object.tree.factory.TreeFactory;
import com.hero.x.query.object.tree.function.JsonObjectFunction;

public class ManuallyTest
{
    public static void main(String[] args)
    {
        String data= """
            {
            "name":"hhh"
            }
            """;
        String query = """
            {
                         "type": "UpdateObject",
                         "filter": {},
                         "update": [
                           {
                             "type": "expr",
                             "operator": "SET",
                             "key": "name",
                             "value": "蔡明"
                           }
                         ]
                       } 
            """;
        JSONObject jsonObject = JSONObject.parseObject(data);
        new TreeFactory().buildFromJson(query,new JsonObjectFunction()).apply(jsonObject);
        System.out.println(jsonObject);

    }
}
