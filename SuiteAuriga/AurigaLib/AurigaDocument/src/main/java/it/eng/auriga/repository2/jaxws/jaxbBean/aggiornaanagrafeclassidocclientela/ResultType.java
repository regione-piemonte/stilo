/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ResultType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ResultType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="KO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ResultType")
@XmlEnum
public enum ResultType {


    /**
     * Esito della richiesta: OK
     * 
     */
    OK,

    /**
     * Esito della richiesta: KO
     * 
     */
    KO;

    public String value() {
        return name();
    }

    public static ResultType fromValue(String v) {
        return valueOf(v);
    }

}
