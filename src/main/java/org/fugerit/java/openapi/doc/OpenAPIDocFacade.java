package org.fugerit.java.openapi.doc;

import java.io.OutputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.core.util.result.Result;
import org.fugerit.java.doc.base.process.DocProcessContext;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfig;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfigFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class OpenAPIDocFacade {

    private static final Logger logger = LoggerFactory.getLogger(OpenAPIDocFacade.class);

    private static final String BUNDLE_LABEL_PATH = "lang.label";

    public static final String CHAIN_ID_YAML_DOC_TEMPLATE = "openapi-doc-template";

    private static final FreemarkerDocProcessConfig INSTANCE = FreemarkerDocProcessConfigFacade
            .loadConfigSafe("cl://doc-facade/fm-process-config-yaml.xml");

    private static final boolean VALIDATE_DOC_XML = false;

    @SuppressWarnings("unchecked")
    public int handle(Reader inputYaml, OutputStream outputData, OpenAPIDocConfig config) throws Exception {
        int result = Result.RESULT_CODE_OK;
        // yaml parsing
        Yaml yaml = new Yaml();
        Map<String, Object> fullYaml = yaml.load(inputYaml);
        Map<String, Object> paths = (Map<String, Object>) fullYaml.get("paths");
        Map<String, Object> components = (Map<String, Object>) fullYaml.get("components");
        Map<String, Object> schemas = (Map<String, Object>) components.get("schemas");
        Map<String, Object> info = (Map<String, Object>) fullYaml.get("info");
        // bundle labels
        ResourceBundle labelsBundle = ResourceBundle.getBundle(BUNDLE_LABEL_PATH, config.getLocale());
        Properties labels = PropsIO.loadFromBundle(labelsBundle);
        if (config.isUseOpenapiTitle() && info.containsKey("title")) {
            labels.setProperty("doc.def.title", (String) info.get("title"));
        }
        labels.putAll(config.getLabelsOverride());
        // build model
        OpenAPIModel openAPIModel = new OpenAPIModel();
        openAPIModel.setPaths(paths);
        openAPIModel.setSchemas(schemas);
        openAPIModel.setLabels(labels);
        openAPIModel.setConfig(config);
        // generation
        logger.info("docFacade -> {} (validate) {}", INSTANCE, VALIDATE_DOC_XML);
        DocProcessContext context = DocProcessContext.newContext(OpenAPIModel.ATT_NAME, openAPIModel);
        INSTANCE.process(CHAIN_ID_YAML_DOC_TEMPLATE, config.getOutputFormat(), context, outputData, VALIDATE_DOC_XML);
        return result;
    }

}
