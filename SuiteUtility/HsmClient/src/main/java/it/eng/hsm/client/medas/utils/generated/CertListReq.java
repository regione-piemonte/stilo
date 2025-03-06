
package it.eng.hsm.client.medas.utils.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeCertListReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CertListReq" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeCertListReq"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "certListReq"
})
@XmlRootElement(name = "CertListReq")
public class CertListReq {

    @XmlElement(name = "CertListReq", required = true)
    protected TypeCertListReq certListReq;

    /**
     * Gets the value of the certListReq property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCertListReq }
     *     
     */
    public TypeCertListReq getCertListReq() {
        return certListReq;
    }

    /**
     * Sets the value of the certListReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCertListReq }
     *     
     */
    public void setCertListReq(TypeCertListReq value) {
        this.certListReq = value;
    }

}
