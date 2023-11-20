/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per posizioneRispettoAlTimbro.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="posizioneRispettoAlTimbro"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="sopra"/&gt;
 *     &lt;enumeration value="sotto"/&gt;
 *     &lt;enumeration value="inlinea"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "posizioneRispettoAlTimbro", namespace = "it.eng.fileoperation.ws.timbro")
@XmlEnum
public enum PosizioneRispettoAlTimbro {

    @XmlEnumValue("sopra")
    SOPRA("sopra"),
    @XmlEnumValue("sotto")
    SOTTO("sotto"),
    @XmlEnumValue("inlinea")
    INLINEA("inlinea");
    private final String value;

    PosizioneRispettoAlTimbro(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PosizioneRispettoAlTimbro fromValue(String v) {
        for (PosizioneRispettoAlTimbro c: PosizioneRispettoAlTimbro.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
