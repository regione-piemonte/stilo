/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per sesso.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="sesso"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MASCHIO"/&gt;
 *     &lt;enumeration value="FEMMINA"/&gt;
 *     &lt;enumeration value="NON_DEFINITO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "sesso")
@XmlEnum
public enum Sesso {

    MASCHIO,
    FEMMINA,
    NON_DEFINITO;

    public String value() {
        return name();
    }

    public static Sesso fromValue(String v) {
        return valueOf(v);
    }

}
