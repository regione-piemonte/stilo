
package com.opentext.livelink.service.documentmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rootID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="pathElements" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
    "rootID",
    "pathElements"
})
@XmlRootElement(name = "GetNodeByPath")
public class GetNodeByPath {

    protected long rootID;
    @XmlElement(nillable = true)
    protected List<String> pathElements;

    /**
     * Recupera il valore della proprietà rootID.
     * 
     */
    public long getRootID() {
        return rootID;
    }

    /**
     * Imposta il valore della proprietà rootID.
     * 
     */
    public void setRootID(long value) {
        this.rootID = value;
    }

    /**
     * Gets the value of the pathElements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pathElements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPathElements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPathElements() {
        if (pathElements == null) {
            pathElements = new ArrayList<String>();
        }
        return this.pathElements;
    }

}
