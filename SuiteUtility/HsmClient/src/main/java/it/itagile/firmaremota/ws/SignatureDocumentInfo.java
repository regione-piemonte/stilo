
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SignatureDocumentInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SignatureDocumentInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="signatures" type="{http://ws.firmaremota.itagile.it}Signatures"/&gt;
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureDocumentInfo", propOrder = {
    "signatures",
    "content"
})
public class SignatureDocumentInfo {

    @XmlElement(required = true, nillable = true)
    protected Signatures signatures;
    @XmlElement(required = true, nillable = true)
    protected byte[] content;

    /**
     * Recupera il valore della proprietà signatures.
     * 
     * @return
     *     possible object is
     *     {@link Signatures }
     *     
     */
    public Signatures getSignatures() {
        return signatures;
    }

    /**
     * Imposta il valore della proprietà signatures.
     * 
     * @param value
     *     allowed object is
     *     {@link Signatures }
     *     
     */
    public void setSignatures(Signatures value) {
        this.signatures = value;
    }

    /**
     * Recupera il valore della proprietà content.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Imposta il valore della proprietà content.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContent(byte[] value) {
        this.content = value;
    }

}
