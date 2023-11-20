/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ResponseRapportoVerificaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponseRapportoVerificaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nomeFileRapportoVerifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseRapportoVerificaType", propOrder = {
    "nomeFileRapportoVerifica"
})
public class ResponseRapportoVerificaType
    extends AbstractResponseOperationType
{

    protected String nomeFileRapportoVerifica;

    /**
     * Recupera il valore della proprietà nomeFileRapportoVerifica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFileRapportoVerifica() {
        return nomeFileRapportoVerifica;
    }

    /**
     * Imposta il valore della proprietà nomeFileRapportoVerifica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFileRapportoVerifica(String value) {
        this.nomeFileRapportoVerifica = value;
    }

}
