
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="documentP7mInfoReturn" type="{http://ws.firmaremota.itagile.it}SignatureDocumentInfo"/&gt;
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
    "documentP7MInfoReturn"
})
@XmlRootElement(name = "documentP7mInfoResponse")
public class DocumentP7MInfoResponse {

    @XmlElement(name = "documentP7mInfoReturn", required = true)
    protected SignatureDocumentInfo documentP7MInfoReturn;

    /**
     * Recupera il valore della proprietà documentP7MInfoReturn.
     * 
     * @return
     *     possible object is
     *     {@link SignatureDocumentInfo }
     *     
     */
    public SignatureDocumentInfo getDocumentP7MInfoReturn() {
        return documentP7MInfoReturn;
    }

    /**
     * Imposta il valore della proprietà documentP7MInfoReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureDocumentInfo }
     *     
     */
    public void setDocumentP7MInfoReturn(SignatureDocumentInfo value) {
        this.documentP7MInfoReturn = value;
    }

}
