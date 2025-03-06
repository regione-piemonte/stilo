
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
 *         &lt;element name="verifyCertificateAtTimeReturn" type="{http://ws.firmaremota.itagile.it}CertificateStatus"/&gt;
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
    "verifyCertificateAtTimeReturn"
})
@XmlRootElement(name = "verifyCertificateAtTimeResponse")
public class VerifyCertificateAtTimeResponse {

    @XmlElement(required = true)
    protected CertificateStatus verifyCertificateAtTimeReturn;

    /**
     * Recupera il valore della proprietà verifyCertificateAtTimeReturn.
     * 
     * @return
     *     possible object is
     *     {@link CertificateStatus }
     *     
     */
    public CertificateStatus getVerifyCertificateAtTimeReturn() {
        return verifyCertificateAtTimeReturn;
    }

    /**
     * Imposta il valore della proprietà verifyCertificateAtTimeReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateStatus }
     *     
     */
    public void setVerifyCertificateAtTimeReturn(CertificateStatus value) {
        this.verifyCertificateAtTimeReturn = value;
    }

}
