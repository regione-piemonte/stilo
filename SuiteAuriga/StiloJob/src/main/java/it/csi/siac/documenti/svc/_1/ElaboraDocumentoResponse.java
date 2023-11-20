/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.documenti.svc._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.integ.data._1.BaseResponse;


/**
 * <p>Classe Java per elaboraDocumentoResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="elaboraDocumentoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}baseResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="responseElaborazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "elaboraDocumentoResponse", propOrder = {
    "responseElaborazione"
})
@XmlSeeAlso({
    ElaboraAttiAmministrativiResponse.class
})
public class ElaboraDocumentoResponse
    extends BaseResponse
{

    protected String responseElaborazione;

    /**
     * Recupera il valore della proprietà responseElaborazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseElaborazione() {
        return responseElaborazione;
    }

    /**
     * Imposta il valore della proprietà responseElaborazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseElaborazione(String value) {
        this.responseElaborazione = value;
    }

}
