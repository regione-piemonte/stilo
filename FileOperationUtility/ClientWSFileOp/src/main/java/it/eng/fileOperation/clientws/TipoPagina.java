/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tipoPagina.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoPagina"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="prima"/&gt;
 *     &lt;enumeration value="ultima"/&gt;
 *     &lt;enumeration value="tutte"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tipoPagina", namespace = "it.eng.fileoperation.ws.timbro")
@XmlEnum
public enum TipoPagina {

    @XmlEnumValue("prima")
    PRIMA("prima"),
    @XmlEnumValue("ultima")
    ULTIMA("ultima"),
    @XmlEnumValue("tutte")
    TUTTE("tutte");
    private final String value;

    TipoPagina(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoPagina fromValue(String v) {
        for (TipoPagina c: TipoPagina.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
