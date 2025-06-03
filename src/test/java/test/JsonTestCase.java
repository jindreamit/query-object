package test;

import com.alibaba.fastjson2.JSONObject;
import com.hero.x.query.object.tree.IObjectFunction;
import com.hero.x.query.object.tree.QueryTree;
import com.hero.x.query.object.tree.factory.TreeFactory;
import com.hero.x.query.object.tree.function.JsonObjectFunction;
import com.hero.x.query.object.tree.node.WrappedObject;

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
            queryTree.apply(WrappedObject.wrapUnsafe(originData));
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
            throw new RuntimeException(e);
        }
        throw new RuntimeException("test case failed");
    }
}
