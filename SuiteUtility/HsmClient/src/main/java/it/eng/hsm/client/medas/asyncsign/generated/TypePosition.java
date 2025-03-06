
package it.eng.hsm.client.medas.asyncsign.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typePosition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typePosition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="leftx" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="lefty" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="rightx" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="righty" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typePosition", propOrder = {
    "page",
    "leftx",
    "lefty",
    "rightx",
    "righty"
})
public class TypePosition {

    @XmlElement(required = true)
    protected BigInteger page;
    @XmlElement(required = true)
    protected BigInteger leftx;
    @XmlElement(required = true)
    protected BigInteger lefty;
    @XmlElement(required = true)
    protected BigInteger rightx;
    @XmlElement(required = true)
    protected BigInteger righty;

    /**
     * Gets the value of the page property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPage(BigInteger value) {
        this.page = value;
    }

    /**
     * Gets the value of the leftx property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLeftx() {
        return leftx;
    }

    /**
     * Sets the value of the leftx property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLeftx(BigInteger value) {
        this.leftx = value;
    }

    /**
     * Gets the value of the lefty property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLefty() {
        return lefty;
    }

    /**
     * Sets the value of the lefty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLefty(BigInteger value) {
        this.lefty = value;
    }

    /**
     * Gets the value of the rightx property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRightx() {
        return rightx;
    }

    /**
     * Sets the value of the rightx property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRightx(BigInteger value) {
        this.rightx = value;
    }

    /**
     * Gets the value of the righty property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRighty() {
        return righty;
    }

    /**
     * Sets the value of the righty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRighty(BigInteger value) {
        this.righty = value;
    }

}
