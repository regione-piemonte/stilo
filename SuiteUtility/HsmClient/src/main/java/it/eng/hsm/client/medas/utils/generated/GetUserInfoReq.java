
package it.eng.hsm.client.medas.utils.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeGetUserInfoReq;

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
 *         &lt;element name="GetUserInfoReq" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeGetUserInfoReq"/>
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
    "getUserInfoReq"
})
@XmlRootElement(name = "GetUserInfoReq")
public class GetUserInfoReq {

    @XmlElement(name = "GetUserInfoReq", required = true)
    protected TypeGetUserInfoReq getUserInfoReq;

    /**
     * Gets the value of the getUserInfoReq property.
     * 
     * @return
     *     possible object is
     *     {@link TypeGetUserInfoReq }
     *     
     */
    public TypeGetUserInfoReq getGetUserInfoReq() {
        return getUserInfoReq;
    }

    /**
     * Sets the value of the getUserInfoReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeGetUserInfoReq }
     *     
     */
    public void setGetUserInfoReq(TypeGetUserInfoReq value) {
        this.getUserInfoReq = value;
    }

}
