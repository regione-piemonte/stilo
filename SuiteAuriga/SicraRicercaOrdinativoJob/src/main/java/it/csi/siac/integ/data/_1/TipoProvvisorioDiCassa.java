/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tipoProvvisorioDiCassa.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoProvvisorioDiCassa"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ENTRATA"/&gt;
 *     &lt;enumeration value="SPESA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tipoProvvisorioDiCassa")
@XmlEnum
public enum TipoProvvisorioDiCassa {

    ENTRATA,
    SPESA;

    public String value() {
        return name();
    }

    public static TipoProvvisorioDiCassa fromValue(String v) {
        return valueOf(v);
    }

}
