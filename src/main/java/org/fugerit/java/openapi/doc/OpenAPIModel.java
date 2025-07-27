package org.fugerit.java.openapi.doc;

import java.util.Map;
import java.util.Properties;

public class OpenAPIModel {

    public static final String ATT_NAME = "openapiModel";

    private OpenAPIDocConfig config;

    public OpenAPIDocConfig getConfig() {
        return config;
    }

    public void setConfig(OpenAPIDocConfig config) {
        this.config = config;
    }

    private Properties labels;

    public Properties getLabels() {
        return labels;
    }

    public void setLabels(Properties labels) {
        this.labels = labels;
    }

    private Map<String, Object> paths;

    public Map<String, Object> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, Object> paths) {
        this.paths = paths;
    }

    private Map<String, Object> schemas;

    public Map<String, Object> getSchemas() {
        return schemas;
    }

    public void setSchemas(Map<String, Object> schemas) {
        this.schemas = schemas;
    }

}
