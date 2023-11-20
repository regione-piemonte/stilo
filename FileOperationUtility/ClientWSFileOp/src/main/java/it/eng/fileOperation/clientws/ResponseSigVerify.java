/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ResponseSigVerify complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponseSigVerify"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sigVerifyResult" type="{it.eng.fileoperation.ws}SigVerifyResultType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseSigVerify", propOrder = {
    "sigVerifyResult"
})
public class ResponseSigVerify
    extends AbstractResponseOperationType
{

    @XmlElement(required = true)
    protected SigVerifyResultType sigVerifyResult;

    /**
     * Recupera il valore della proprietà sigVerifyResult.
     * 
     * @return
     *     possible object is
     *     {@link SigVerifyResultType }
     *     
     */
    public SigVerifyResultType getSigVerifyResult() {
        return sigVerifyResult;
    }

    /**
     * Imposta il valore della proprietà sigVerifyResult.
     * 
     * @param value
     *     allowed object is
     *     {@link SigVerifyResultType }
     *     
     */
    public void setSigVerifyResult(SigVerifyResultType value) {
        this.sigVerifyResult = value;
    }

}
