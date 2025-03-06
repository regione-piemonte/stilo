
package it.eng.hsm.client.medas.utils.generated;

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
 *         &lt;element name="GetUserInfo3Req" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeGetUserInfo3Req"/>
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
    "getUserInfo3Req"
})
@XmlRootElement(name = "GetUserInfo3Req")
public class GetUserInfo3Req {

    @XmlElement(name = "GetUserInfo3Req", required = true)
    protected TypeGetUserInfo3Req getUserInfo3Req;

    /**
     * Gets the value of the getUserInfo3Req property.
     * 
     * @return
     *     possible object is
     *     {@link TypeGetUserInfo3Req }
     *     
     */
    public TypeGetUserInfo3Req getGetUserInfo3Req() {
        return getUserInfo3Req;
    }

    /**
     * Sets the value of the getUserInfo3Req property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeGetUserInfo3Req }
     *     
     */
    public void setGetUserInfo3Req(TypeGetUserInfo3Req value) {
        this.getUserInfo3Req = value;
    }

}
