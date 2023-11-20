/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumDocumentObjectType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumDocumentObjectType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="ContenutoFisicoPropertiesType"/&gt;
 *     &lt;enumeration value="DocumentoDBPropertiesType"/&gt;
 *     &lt;enumeration value="DocumentoRegistroPropertiesType"/&gt;
 *     &lt;enumeration value="DocumentoSemplicePropertiesType"/&gt;
 *     &lt;enumeration value="ClipsMetallicaPropertiesType"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumDocumentObjectType")
@XmlEnum
public enum EnumDocumentObjectType {

    @XmlEnumValue("ContenutoFisicoPropertiesType")
    CONTENUTO_FISICO_PROPERTIES_TYPE("ContenutoFisicoPropertiesType"),
    @XmlEnumValue("DocumentoDBPropertiesType")
    DOCUMENTO_DB_PROPERTIES_TYPE("DocumentoDBPropertiesType"),
    @XmlEnumValue("DocumentoRegistroPropertiesType")
    DOCUMENTO_REGISTRO_PROPERTIES_TYPE("DocumentoRegistroPropertiesType"),
    @XmlEnumValue("DocumentoSemplicePropertiesType")
    DOCUMENTO_SEMPLICE_PROPERTIES_TYPE("DocumentoSemplicePropertiesType"),
    @XmlEnumValue("ClipsMetallicaPropertiesType")
    CLIPS_METALLICA_PROPERTIES_TYPE("ClipsMetallicaPropertiesType");
    private final String value;

    EnumDocumentObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumDocumentObjectType fromValue(String v) {
        for (EnumDocumentObjectType c: EnumDocumentObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
