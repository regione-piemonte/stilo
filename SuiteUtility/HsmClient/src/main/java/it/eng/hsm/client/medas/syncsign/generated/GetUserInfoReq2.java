
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
 *         &lt;element name="GetUserInfoReq2" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeGetUserInfoReq2"/>
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
    "getUserInfoReq2"
})
@XmlRootElement(name = "GetUserInfoReq2")
public class GetUserInfoReq2 {

    @XmlElement(name = "GetUserInfoReq2", required = true)
    protected TypeGetUserInfoReq2 getUserInfoReq2;

    /**
     * Gets the value of the getUserInfoReq2 property.
     * 
     * @return
     *     possible object is
     *     {@link TypeGetUserInfoReq2 }
     *     
     */
    public TypeGetUserInfoReq2 getGetUserInfoReq2() {
        return getUserInfoReq2;
    }

    /**
     * Sets the value of the getUserInfoReq2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeGetUserInfoReq2 }
     *     
     */
    public void setGetUserInfoReq2(TypeGetUserInfoReq2 value) {
        this.getUserInfoReq2 = value;
    }

}
