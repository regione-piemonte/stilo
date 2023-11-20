/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per posizioneTimbroNellaPagina.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="posizioneTimbroNellaPagina"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="altoDx"/&gt;
 *     &lt;enumeration value="altoSn"/&gt;
 *     &lt;enumeration value="bassoDx"/&gt;
 *     &lt;enumeration value="bassoSn"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "posizioneTimbroNellaPagina", namespace = "it.eng.fileoperation.ws.timbro")
@XmlEnum
public enum PosizioneTimbroNellaPagina {

    @XmlEnumValue("altoDx")
    ALTO_DX("altoDx"),
    @XmlEnumValue("altoSn")
    ALTO_SN("altoSn"),
    @XmlEnumValue("bassoDx")
    BASSO_DX("bassoDx"),
    @XmlEnumValue("bassoSn")
    BASSO_SN("bassoSn");
    private final String value;

    PosizioneTimbroNellaPagina(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PosizioneTimbroNellaPagina fromValue(String v) {
        for (PosizioneTimbroNellaPagina c: PosizioneTimbroNellaPagina.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
