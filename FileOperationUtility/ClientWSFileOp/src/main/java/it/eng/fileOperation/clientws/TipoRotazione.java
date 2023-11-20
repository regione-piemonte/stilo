/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tipoRotazione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoRotazione"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="orizzontale"/&gt;
 *     &lt;enumeration value="verticale"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tipoRotazione", namespace = "it.eng.fileoperation.ws.timbro")
@XmlEnum
public enum TipoRotazione {

    @XmlEnumValue("orizzontale")
    ORIZZONTALE("orizzontale"),
    @XmlEnumValue("verticale")
    VERTICALE("verticale");
    private final String value;

    TipoRotazione(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoRotazione fromValue(String v) {
        for (TipoRotazione c: TipoRotazione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
