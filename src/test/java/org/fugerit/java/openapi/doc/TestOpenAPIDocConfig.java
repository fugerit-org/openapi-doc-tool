package org.fugerit.java.openapi.doc;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOpenAPIDocConfig {

    private final static Logger logger = LoggerFactory.getLogger(TestOpenAPIDocConfig.class);

    private static final String FILE_ENCODING = "utf-8";

    private static final String CONFIG_FILE = "src/test/resources/yaml-doc-config.xml";

    private static final String ID_CATALOG = "sample";

    private static final String ID_CATALOG_IT = "sample_it";

    public boolean testWorkerSample(String encoding, String idCatalog) {
        return SafeFunction.get(() -> {
            boolean ok = false;
            System.setProperty("file.encoding", FILE_ENCODING);
            Properties props = new Properties();
            props.setProperty(OpenAPIDocMain.ARG_MODE, OpenAPIDocMain.ARG_MODE_CONFIG);
            props.setProperty(OpenAPIDocMain.ARG_CONFIG_PATH, CONFIG_FILE);
            props.setProperty(OpenAPIDocMain.ARG_ID_CATALOG, idCatalog);
            OpenAPIDocMain.worker(props);
            logger.info("Generation complete! {}", CONFIG_FILE);
            ok = true;
            return ok;
        });
    }

    @Test
    public void testSample() {
        boolean ok = this.testWorkerSample(FILE_ENCODING, ID_CATALOG);
        Assert.assertTrue(ok);
    }

    @Test
    public void testSampleIt() {
        boolean ok = this.testWorkerSample(FILE_ENCODING, ID_CATALOG_IT);
        Assert.assertTrue(ok);
    }

    @Test
    public void testSampleFail1() {
        Assert.assertThrows(NullPointerException.class, () -> this.testWorkerSample(FILE_ENCODING, null));
    }

    @Test
    public void testSampleFail2() {
        Properties props = new Properties();
        props.setProperty(OpenAPIDocMain.ARG_MODE, OpenAPIDocMain.ARG_MODE_SINGLE);
        Assert.assertThrows(ConfigException.class, () -> OpenAPIDocMain.worker(props));
    }

    @Test
    public void testSampleFail3() {
        Properties props = new Properties();
        props.setProperty(OpenAPIDocMain.ARG_MODE, OpenAPIDocMain.ARG_MODE_SINGLE);
        props.setProperty(OpenAPIDocMain.ARG_INPUT_YAML, "src/test/resources/sample/sample_01.yaml");
        Assert.assertThrows(ConfigException.class, () -> OpenAPIDocMain.worker(props));
    }

}
