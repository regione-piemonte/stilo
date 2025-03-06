
package it.eng.hsm.client.medas.syncsign.generated;

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
 *         &lt;element name="CertListResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeCertListResp"/>
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
    "certListResp"
})
@XmlRootElement(name = "CertListResp")
public class CertListResp {

    @XmlElement(name = "CertListResp", required = true)
    protected TypeCertListResp certListResp;

    /**
     * Gets the value of the certListResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCertListResp }
     *     
     */
    public TypeCertListResp getCertListResp() {
        return certListResp;
    }

    /**
     * Sets the value of the certListResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCertListResp }
     *     
     */
    public void setCertListResp(TypeCertListResp value) {
        this.certListResp = value;
    }

}
