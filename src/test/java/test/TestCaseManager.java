package test;

import java.nio.file.Files;
import java.nio.file.Path;

public class TestCaseManager
{
    public static void main(String[] args) throws Exception
    {
        Files.list(Path.of(TestCaseManager.class.getResource("/testcase").toURI()))
            .forEach(s ->
            {
                JsonTestCase testCase = new JsonTestCase(s);
                System.out.printf("[RUN][%s][PASS]:%s\n", s, testCase.run());
            });
    }
}
