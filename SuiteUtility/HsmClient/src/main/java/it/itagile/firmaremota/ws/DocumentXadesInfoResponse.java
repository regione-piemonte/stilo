
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
 *         &lt;element name="documentXadesInfoReturn" type="{http://ws.firmaremota.itagile.it}SignatureDocumentInfo"/&gt;
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
    "documentXadesInfoReturn"
})
@XmlRootElement(name = "documentXadesInfoResponse")
public class DocumentXadesInfoResponse {

    @XmlElement(required = true)
    protected SignatureDocumentInfo documentXadesInfoReturn;

    /**
     * Recupera il valore della proprietà documentXadesInfoReturn.
     * 
     * @return
     *     possible object is
     *     {@link SignatureDocumentInfo }
     *     
     */
    public SignatureDocumentInfo getDocumentXadesInfoReturn() {
        return documentXadesInfoReturn;
    }

    /**
     * Imposta il valore della proprietà documentXadesInfoReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureDocumentInfo }
     *     
     */
    public void setDocumentXadesInfoReturn(SignatureDocumentInfo value) {
        this.documentXadesInfoReturn = value;
    }

}
