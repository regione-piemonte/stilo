
package it.eng.hsm.client.aruba.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userCertList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userCertList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="app1" type="{http://arubasignservice.arubapec.it/}cert" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="app2" type="{http://arubasignservice.arubapec.it/}cert" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userCertList", propOrder = {
    "app1",
    "app2",
    "description",
    "status"
})
public class UserCertList {

    @XmlElement(nillable = true)
    protected List<Cert> app1;
    @XmlElement(nillable = true)
    protected List<Cert> app2;
    protected String description;
    protected String status;

    /**
     * Gets the value of the app1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the app1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApp1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Cert }
     * 
     * 
     */
    public List<Cert> getApp1() {
        if (app1 == null) {
            app1 = new ArrayList<Cert>();
        }
        return this.app1;
    }

    /**
     * Gets the value of the app2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the app2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApp2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Cert }
     * 
     * 
     */
    public List<Cert> getApp2() {
        if (app2 == null) {
            app2 = new ArrayList<Cert>();
        }
        return this.app2;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
