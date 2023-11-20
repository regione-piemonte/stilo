/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

/**
 * Utility class for file handling.
 */
public class FileHandler {

    private static FileHandler instance;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private FileHandler() {
    }

    /**
     * Get the {@link FileHandler} instance.
     *
     * @return The handler instance.
     */
    public static synchronized FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }

        return instance;
    }

    /**
     * Get the value of specified property searching it into given resource name.
     *
     * @param propertyName The property name.
     * @param resourceName The resource name.
     * @return The property value.
     */
    public Optional<String> getPropertyValue(final String propertyName, final String resourceName) {
        return getPropertyValue(propertyName, resourceName, getClass());
    }

    /**
     * Get the value of specified property searching it into given resource name.
     *
     * @param propertyName The property name.
     * @param resourceName The resource name.
     * @param clazz        The class for resource loading.
     * @return The property value.
     */
    public Optional<String> getPropertyValue(final String propertyName, final String resourceName,
                                             final Class<?> clazz) {
        logger.info(getClass().getSimpleName() + "-getPropertyValue[" + propertyName + "][" + resourceName + "]");

        Optional<String> propertyValue =
                Optional.ofNullable(getProperties(resourceName, clazz).getProperty(propertyName));
        logger.info("ResolvedProperty[" + propertyValue.orElse("{notFound}") + "]");

        return propertyValue;
    }

    /**
     * Get the {@link Properties} for the given resource name.
     *
     * @param resourceName The resource name.
     * @param clazz        The class for resource loading.
     * @return The properties object.
     */
    public Properties getProperties(final String resourceName, final Class<?> clazz) {
        logger.info(getClass().getSimpleName() + "-getProperties[" + resourceName + "]");

        final Properties properties = new Properties();
        try {
            properties.load(clazz.getResourceAsStream(resourceName));
        } catch (IOException e) {
            logger.error("[ErrorLoadingResource]", e);
        }

        return properties;
    }

    /**
     * Get bytes array from the given file path.
     *
     * @param filePath The input file path.
     * @return The bytes array.
     */
    public byte[] getBytesFromFile(final String filePath) {
        logger.info(getClass().getSimpleName() + "-getBytesFromFile[" + filePath + "]");

        final Path path = Paths.get(filePath);
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(path);
            logger.info("BytesLength[" + bytes.length + "]");
        } catch (IOException e) {
            logger.error("[ErrorReadingBytes]", e);
        }

        return bytes;
    }

}
