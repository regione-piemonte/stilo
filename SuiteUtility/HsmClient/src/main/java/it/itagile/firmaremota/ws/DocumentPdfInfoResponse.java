
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
 *         &lt;element name="documentPdfInfoReturn" type="{http://ws.firmaremota.itagile.it}SignatureDocumentInfo"/&gt;
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
    "documentPdfInfoReturn"
})
@XmlRootElement(name = "documentPdfInfoResponse")
public class DocumentPdfInfoResponse {

    @XmlElement(required = true)
    protected SignatureDocumentInfo documentPdfInfoReturn;

    /**
     * Recupera il valore della proprietà documentPdfInfoReturn.
     * 
     * @return
     *     possible object is
     *     {@link SignatureDocumentInfo }
     *     
     */
    public SignatureDocumentInfo getDocumentPdfInfoReturn() {
        return documentPdfInfoReturn;
    }

    /**
     * Imposta il valore della proprietà documentPdfInfoReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureDocumentInfo }
     *     
     */
    public void setDocumentPdfInfoReturn(SignatureDocumentInfo value) {
        this.documentPdfInfoReturn = value;
    }

}
