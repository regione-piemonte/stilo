
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per CompoundDocRelease complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CompoundDocRelease">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CompoundDocID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Locked" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LockedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="LockedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Major" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Minor" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReleaseID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompoundDocRelease", propOrder = {
    "comment",
    "compoundDocID",
    "locked",
    "lockedBy",
    "lockedDate",
    "major",
    "minor",
    "name",
    "releaseID"
})
public class CompoundDocRelease
    extends ServiceDataObject
{

    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "CompoundDocID")
    protected long compoundDocID;
    @XmlElement(name = "Locked")
    protected int locked;
    @XmlElement(name = "LockedBy")
    protected long lockedBy;
    @XmlElement(name = "LockedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lockedDate;
    @XmlElement(name = "Major")
    protected long major;
    @XmlElement(name = "Minor")
    protected long minor;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "ReleaseID")
    protected long releaseID;

    /**
     * Recupera il valore della proprietà comment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Imposta il valore della proprietà comment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Recupera il valore della proprietà compoundDocID.
     * 
     */
    public long getCompoundDocID() {
        return compoundDocID;
    }

    /**
     * Imposta il valore della proprietà compoundDocID.
     * 
     */
    public void setCompoundDocID(long value) {
        this.compoundDocID = value;
    }

    /**
     * Recupera il valore della proprietà locked.
     * 
     */
    public int getLocked() {
        return locked;
    }

    /**
     * Imposta il valore della proprietà locked.
     * 
     */
    public void setLocked(int value) {
        this.locked = value;
    }

    /**
     * Recupera il valore della proprietà lockedBy.
     * 
     */
    public long getLockedBy() {
        return lockedBy;
    }

    /**
     * Imposta il valore della proprietà lockedBy.
     * 
     */
    public void setLockedBy(long value) {
        this.lockedBy = value;
    }

    /**
     * Recupera il valore della proprietà lockedDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLockedDate() {
        return lockedDate;
    }

    /**
     * Imposta il valore della proprietà lockedDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLockedDate(XMLGregorianCalendar value) {
        this.lockedDate = value;
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

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà releaseID.
     * 
     */
    public long getReleaseID() {
        return releaseID;
    }

    /**
     * Imposta il valore della proprietà releaseID.
     * 
     */
    public void setReleaseID(long value) {
        this.releaseID = value;
    }

}
