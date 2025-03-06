
package it.doqui.acta.acaris.multifiling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="response" type="{common.acaris.acta.doqui.it}PagingResponseType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "response"
})
@XmlRootElement(name = "getChildrenResponse")
public class GetChildrenResponse {

    protected PagingResponseType response;

    /**
     * Recupera il valore della proprietà response.
     * 
     * @return
     *     possible object is
     *     {@link PagingResponseType }
     *     
     */
    public PagingResponseType getResponse() {
        return response;
    }

    /**
     * Imposta il valore della proprietà response.
     * 
     * @param value
     *     allowed object is
     *     {@link PagingResponseType }
     *     
     */
    public void setResponse(PagingResponseType value) {
        this.response = value;
    }

}
