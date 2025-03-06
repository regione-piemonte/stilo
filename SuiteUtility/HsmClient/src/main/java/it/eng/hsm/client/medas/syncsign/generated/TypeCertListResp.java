
package it.eng.hsm.client.medas.syncsign.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeCertListResp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeCertListResp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rawCertificates" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeRawCertificate" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeMessage"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeCertListResp", propOrder = {
    "rawCertificates",
    "message"
})
public class TypeCertListResp {

    @XmlElement(nillable = true)
    protected List<TypeRawCertificate> rawCertificates;
    @XmlElement(required = true)
    protected TypeMessage message;

    /**
     * Gets the value of the rawCertificates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rawCertificates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRawCertificates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeRawCertificate }
     * 
     * 
     */
    public List<TypeRawCertificate> getRawCertificates() {
        if (rawCertificates == null) {
            rawCertificates = new ArrayList<TypeRawCertificate>();
        }
        return this.rawCertificates;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link TypeMessage }
     *     
     */
    public TypeMessage getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeMessage }
     *     
     */
    public void setMessage(TypeMessage value) {
        this.message = value;
    }

}
