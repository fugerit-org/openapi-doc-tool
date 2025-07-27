package org.fugerit.java.openapi.doc.config;

import org.fugerit.java.core.cfg.xml.CustomListCatalogConfig;
import org.fugerit.java.core.util.collection.ListMapStringKey;

public class OpenAPIDocCatalog extends CustomListCatalogConfig<OpenapiConfig, ListMapStringKey<OpenapiConfig>> {

    /**
     *
     */
    private static final long serialVersionUID = -4378688201418189400L;

    public OpenAPIDocCatalog() {
        super("openapi-catalog", "openapi");
        this.getGeneralProps().setProperty(ATT_TYPE, OpenapiConfig.class.getName());
    }

}
