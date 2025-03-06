
package it.eng.hsm.client.medas.utils.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeGetUserInfoResp;

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
 *         &lt;element name="GetUserInfoResp" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeGetUserInfoResp"/>
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
    "getUserInfoResp"
})
@XmlRootElement(name = "GetUserInfoResp")
public class GetUserInfoResp {

    @XmlElement(name = "GetUserInfoResp", required = true)
    protected TypeGetUserInfoResp getUserInfoResp;

    /**
     * Gets the value of the getUserInfoResp property.
     * 
     * @return
     *     possible object is
     *     {@link TypeGetUserInfoResp }
     *     
     */
    public TypeGetUserInfoResp getGetUserInfoResp() {
        return getUserInfoResp;
    }

    /**
     * Sets the value of the getUserInfoResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeGetUserInfoResp }
     *     
     */
    public void setGetUserInfoResp(TypeGetUserInfoResp value) {
        this.getUserInfoResp = value;
    }

}
