/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per siNoIndifferenteEnum.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="siNoIndifferenteEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SI"/&gt;
 *     &lt;enumeration value="NO"/&gt;
 *     &lt;enumeration value="INDIFFERENTE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "siNoIndifferenteEnum")
@XmlEnum
public enum SiNoIndifferenteEnum {

    SI,
    NO,
    INDIFFERENTE;

    public String value() {
        return name();
    }

    public static SiNoIndifferenteEnum fromValue(String v) {
        return valueOf(v);
    }

}
