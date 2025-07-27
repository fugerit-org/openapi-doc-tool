package org.fugerit.java.openapi.doc;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.util.result.Result;
import org.fugerit.java.openapi.doc.OpenAPIDocMain;
import org.fugerit.java.openapi.doc.OpenAPIDocCheckModel;
import org.junit.Assert;
import org.junit.Test;

import io.swagger.client.model.SampleResult;

public class TestOpenAPIDocCheckModel {

    @Test
    public void testOk() throws ConfigException {
        Properties params = new Properties();
        params.setProperty(OpenAPIDocMain.ARG_INPUT_OPENAPI, "src/test/resources/sample/sample.yaml");
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_SCHEMA, SampleResult.class.getSimpleName());
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_TYPE, SampleResult.class.getName());
        params.setProperty(OpenAPIDocMain.ARG_OUTPUT_FILE, "target/report.md");
        int res = OpenAPIDocCheckModel.handleModelCheck(params);
        Assert.assertEquals(Result.RESULT_CODE_OK, res);
    }

    @Test
    public void testKo() throws ConfigException {
        Properties params = new Properties();
        params.setProperty(OpenAPIDocMain.ARG_INPUT_OPENAPI, "src/test/resources/sample/sample_check1.yaml");
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_SCHEMA, SampleResult.class.getSimpleName());
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_TYPE, SampleResult.class.getName());
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_ONCE, BooleanUtils.BOOLEAN_TRUE);
        params.setProperty(OpenAPIDocCheckModel.ARG_PRINT_ONLY_KO, BooleanUtils.BOOLEAN_TRUE);
        int res = OpenAPIDocCheckModel.handleModelCheck(params);
        Assert.assertNotEquals(Result.RESULT_CODE_OK, res);
    }

    @Test
    public void testKo2() throws ConfigException {
        Properties params = new Properties();
        params.setProperty(OpenAPIDocMain.ARG_INPUT_OPENAPI, "src/test/resources/sample/sample_check2.yaml");
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_SCHEMA, SampleResult.class.getSimpleName());
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_TYPE, SampleResult.class.getName());
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_ONCE, BooleanUtils.BOOLEAN_TRUE);
        params.setProperty(OpenAPIDocCheckModel.ARG_PRINT_ONLY_KO, BooleanUtils.BOOLEAN_TRUE);
        int res = OpenAPIDocCheckModel.handleModelCheck(params);
        Assert.assertNotEquals(Result.RESULT_CODE_OK, res);
    }

    @Test
    public void testNoParam() {
        Properties params = new Properties();
        Assert.assertThrows(ConfigException.class, () -> OpenAPIDocCheckModel.handleModelCheck(params));
        params.setProperty(OpenAPIDocMain.ARG_INPUT_YAML, "src/test/resources/sample/sample_check1.yaml");
        Assert.assertThrows(ConfigException.class, () -> OpenAPIDocCheckModel.handleModelCheck(params));
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_TYPE, "not found");
        Assert.assertThrows(ConfigException.class, () -> OpenAPIDocCheckModel.handleModelCheck(params));
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_SCHEMA, SampleResult.class.getSimpleName());
        Assert.assertThrows(ConfigException.class, () -> OpenAPIDocCheckModel.handleModelCheck(params));
        params.setProperty(OpenAPIDocCheckModel.ARG_CHECK_SCHEMA, "not found");
        Assert.assertThrows(ConfigException.class, () -> OpenAPIDocCheckModel.handleModelCheck(params));

    }

    @Test
    public void testMain() {
        String[] args = {
                ArgUtils.getArgString(OpenAPIDocMain.ARG_MODE), OpenAPIDocMain.ARG_MODE_CHECK_MODEL,
                ArgUtils.getArgString(OpenAPIDocMain.ARG_INPUT_OPENAPI), "src/test/resources/sample/sample.yaml",
                ArgUtils.getArgString(OpenAPIDocCheckModel.ARG_CHECK_SCHEMA), SampleResult.class.getSimpleName(),
                ArgUtils.getArgString(OpenAPIDocCheckModel.ARG_CHECK_TYPE), SampleResult.class.getName(),
        };
        OpenAPIDocMain.main(args);
        Assert.assertNotNull(args);
    }

}
