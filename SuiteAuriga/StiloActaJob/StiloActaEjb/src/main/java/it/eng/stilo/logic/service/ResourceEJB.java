/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.stilo.logic.type.EActaProperty;
import it.eng.stilo.util.file.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Singleton
public class ResourceEJB {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<String, Properties> propertiesMap = new HashMap<>();

    @PostConstruct
    private void init() {
        logger.info(getClass().getSimpleName() + "-init");
        // call here addProperties method for each resource you want to read and keep in memory
    }

    private void addProperties(final String resource) {
        logger.info(getClass().getSimpleName() + "-addProperties[" + resource + "]");
        propertiesMap.put(resource, FileHandler.getInstance().getProperties(resource, getClass()));
    }

    public String getPropertyValue(final String resource, final EActaProperty property) {
        logger.info(getClass().getSimpleName() + "-getPropertyValue[" + resource + "][" + property.getCode() + "]");
        return propertiesMap.get(resource).getProperty(property.getCode());
    }

    public Object setPropertyValue(final String resource, final EActaProperty property, final String propertyValue) {
        logger.info(getClass().getSimpleName() + "-setPropertyValue[" + resource + "][" + property.getCode() + ":" + propertyValue + "]");
        return propertiesMap.get(resource).setProperty(property.getCode(), propertyValue);
    }

}
