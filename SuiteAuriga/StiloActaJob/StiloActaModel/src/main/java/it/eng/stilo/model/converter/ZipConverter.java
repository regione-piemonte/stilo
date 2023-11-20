/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.util.file.FileHandler;
import it.eng.stilo.util.zip.ZipUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ZipConverter implements AttributeConverter<String, byte[]> {

    private static final String RESOURCE_FILE = "/model.properties";
    private static final String COMPRESSION_PROPERTY = "compression-enabled";

    private static Boolean compressionEnabled;

    private static void setCompressionEnabled() {
        if (compressionEnabled == null) {
            compressionEnabled = Boolean.valueOf(FileHandler.getInstance().getPropertyValue(COMPRESSION_PROPERTY,
                    RESOURCE_FILE).orElse("false"));
        }
    }

    @Override
    public byte[] convertToDatabaseColumn(final String entityContent) {
        setCompressionEnabled();
        return compressionEnabled ? ZipUtil.compress(entityContent) : entityContent.getBytes();
    }

    @Override
    public String convertToEntityAttribute(final byte[] databaseContent) {
        final String uncompressed = ZipUtil.uncompress(databaseContent);
        return uncompressed != null ? uncompressed : new String(databaseContent);
    }

}
