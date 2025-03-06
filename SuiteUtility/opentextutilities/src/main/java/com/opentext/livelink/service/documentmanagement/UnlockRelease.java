
package com.opentext.livelink.service.documentmanagement;

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
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="major" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="minor" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "id",
    "major",
    "minor"
})
@XmlRootElement(name = "UnlockRelease")
public class UnlockRelease {

    @XmlElement(name = "ID")
    protected long id;
    protected long major;
    protected long minor;

    /**
     * Recupera il valore della proprietà id.
     * 
     */
    public long getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     */
    public void setID(long value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà major.
     * 
     */
    public long getMajor() {
        return major;
    }

    /**
     * Imposta il valore della proprietà major.
     * 
     */
    public void setMajor(long value) {
        this.major = value;
    }

    /**
     * Recupera il valore della proprietà minor.
     * 
     */
    public long getMinor() {
        return minor;
    }

    /**
     * Imposta il valore della proprietà minor.
     * 
     */
    public void setMinor(long value) {
        this.minor = value;
    }

}
