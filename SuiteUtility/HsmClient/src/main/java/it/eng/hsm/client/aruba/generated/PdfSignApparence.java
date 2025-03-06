
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pdfSignApparence complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdfSignApparence">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="leftx" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lefty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rightx" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="righty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="testo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pdfSignApparence", propOrder = {
    "image",
    "leftx",
    "lefty",
    "location",
    "page",
    "reason",
    "rightx",
    "righty",
    "testo"
})
public class PdfSignApparence {

    protected String image;
    protected int leftx;
    protected int lefty;
    protected String location;
    protected int page;
    protected String reason;
    protected int rightx;
    protected int righty;
    protected String testo;

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Gets the value of the leftx property.
     * 
     */
    public int getLeftx() {
        return leftx;
    }

    /**
     * Sets the value of the leftx property.
     * 
     */
    public void setLeftx(int value) {
        this.leftx = value;
    }

    /**
     * Gets the value of the lefty property.
     * 
     */
    public int getLefty() {
        return lefty;
    }

    /**
     * Sets the value of the lefty property.
     * 
     */
    public void setLefty(int value) {
        this.lefty = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the page property.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the rightx property.
     * 
     */
    public int getRightx() {
        return rightx;
    }

    /**
     * Sets the value of the rightx property.
     * 
     */
    public void setRightx(int value) {
        this.rightx = value;
    }

    /**
     * Gets the value of the righty property.
     * 
     */
    public int getRighty() {
        return righty;
    }

    /**
     * Sets the value of the righty property.
     * 
     */
    public void setRighty(int value) {
        this.righty = value;
    }

    /**
     * Gets the value of the testo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Sets the value of the testo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTesto(String value) {
        this.testo = value;
    }

}
