
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
 *         &lt;element name="GetUserInfo3Resp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeGetUserInfo3Resp"/>
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
    "getUserInfo3Resp"
})
@XmlRootElement(name = "GetUserInfo3Resp")
public class GetUserInfo3Resp {

    @XmlElement(name = "GetUserInfo3Resp", required = true)
    protected TypeGetUserInfo3Resp getUserInfo3Resp;

    /**
     * Gets the value of the getUserInfo3Resp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeGetUserInfo3Resp }
     *     
     */
    public TypeGetUserInfo3Resp getGetUserInfo3Resp() {
        return getUserInfo3Resp;
    }

    /**
     * Sets the value of the getUserInfo3Resp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeGetUserInfo3Resp }
     *     
     */
    public void setGetUserInfo3Resp(TypeGetUserInfo3Resp value) {
        this.getUserInfo3Resp = value;
    }

}
