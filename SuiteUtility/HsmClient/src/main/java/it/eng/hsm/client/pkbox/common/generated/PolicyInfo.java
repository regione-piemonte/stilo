
package it.eng.hsm.client.pkbox.common.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PolicyInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PolicyInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cpsURI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="explicitText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="iD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="noticeNumbers" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicyInfo", namespace = "http://server.pkbox.it/xsd", propOrder = {
    "cpsURI",
    "explicitText",
    "id",
    "noticeNumbers"
})
public class PolicyInfo {

    @XmlElement(required = true, nillable = true)
    protected String cpsURI;
    @XmlElement(required = true, nillable = true)
    protected String explicitText;
    @XmlElement(name = "iD", required = true, nillable = true)
    protected String id;
    @XmlElement(required = true, nillable = true)
    protected List<Integer> noticeNumbers;

    /**
     * Recupera il valore della proprietà cpsURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpsURI() {
        return cpsURI;
    }

    /**
     * Imposta il valore della proprietà cpsURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpsURI(String value) {
        this.cpsURI = value;
    }

    /**
     * Recupera il valore della proprietà explicitText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExplicitText() {
        return explicitText;
    }

    /**
     * Imposta il valore della proprietà explicitText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExplicitText(String value) {
        this.explicitText = value;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the noticeNumbers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the noticeNumbers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNoticeNumbers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getNoticeNumbers() {
        if (noticeNumbers == null) {
            noticeNumbers = new ArrayList<Integer>();
        }
        return this.noticeNumbers;
    }

}
