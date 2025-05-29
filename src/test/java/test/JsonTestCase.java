package test;

import com.alibaba.fastjson2.JSONObject;
import com.hero.x.tree.IObjectFunction;
import com.hero.x.tree.QueryTree;
import com.hero.x.tree.factory.TreeFactory;
import com.hero.x.tree.function.JsonObjectFunction;

import java.nio.file.Files;
import java.nio.file.Path;

public class JsonTestCase
{
    private final Path dataRoot;

    public JsonTestCase(Path dataRoot)
    {
        this.dataRoot = dataRoot;
    }

    public IObjectFunction supplyObjectFunction()
    {
        return new JsonObjectFunction();
    }

    public String queryString() throws Exception
    {
        return Files.readString(Path.of(dataRoot.toString() + "/query.json"));
    }

    public String originData() throws Exception
    {
        return Files.readString(Path.of(dataRoot.toString() + "/origin.json"));
    }

    public String resultData() throws Exception
    {
        return Files.readString(Path.of(dataRoot.toString() + "/result.json"));
    }

    public boolean run()
    {
        try
        {
            TreeFactory treeFactory = new TreeFactory();
            QueryTree queryTree = treeFactory.buildFromJson(queryString(), supplyObjectFunction());
            JSONObject originData = JSONObject.parseObject(originData());
            queryTree.apply(originData);
            if (originData.equals(JSONObject.parseObject(resultData())))
            {
                return true;
            }
            System.out.println("Test case failed");
            System.out.println("====================Modified JSON================");
            System.out.println(originData.toJSONString());
            System.out.println("====================Expect JSON================");
            System.out.println(resultData());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
