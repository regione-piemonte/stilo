
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
 *         &lt;element name="GetUserInfoResp2" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeGetUserInfoResp2"/>
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
    "getUserInfoResp2"
})
@XmlRootElement(name = "GetUserInfoResp2")
public class GetUserInfoResp2 {

    @XmlElement(name = "GetUserInfoResp2", required = true)
    protected TypeGetUserInfoResp2 getUserInfoResp2;

    /**
     * Gets the value of the getUserInfoResp2 property.
     * 
     * @return
     *     possible object is
     *     {@link TypeGetUserInfoResp2 }
     *     
     */
    public TypeGetUserInfoResp2 getGetUserInfoResp2() {
        return getUserInfoResp2;
    }

    /**
     * Sets the value of the getUserInfoResp2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeGetUserInfoResp2 }
     *     
     */
    public void setGetUserInfoResp2(TypeGetUserInfoResp2 value) {
        this.getUserInfoResp2 = value;
    }

}
