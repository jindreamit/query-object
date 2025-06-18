package test;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class TestCaseManager
{
    @Test
    public void launchTest() throws Exception
    {
        Files.list(Path.of(TestCaseManager.class.getResource("/testcase").toURI()))
            .forEach(s ->
            {
                JsonTestCase testCase = new JsonTestCase(s);
                System.out.printf("[RUN][BEFORE][%s]\n", s);
                System.out.printf("[RUN][%s][PASS]:%s\n", s, testCase.run());
            });
    }
}
