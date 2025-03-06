
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per Version complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Version">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FileCreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FileCreator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FileDataSize" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="FileModifyDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Filename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FilePlatform" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FileResSize" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="FileType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Locked" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LockedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="LockedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Metadata" type="{urn:DocMan.service.livelink.opentext.com}Metadata" minOccurs="0"/>
 *         &lt;element name="MimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModifyDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NodeID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Owner" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ProviderID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ProviderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VerMajor" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="VerMinor" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Version", propOrder = {
    "comment",
    "createDate",
    "fileCreateDate",
    "fileCreator",
    "fileDataSize",
    "fileModifyDate",
    "filename",
    "filePlatform",
    "fileResSize",
    "fileType",
    "id",
    "locked",
    "lockedBy",
    "lockedDate",
    "metadata",
    "mimeType",
    "modifyDate",
    "name",
    "nodeID",
    "number",
    "owner",
    "providerID",
    "providerName",
    "type",
    "verMajor",
    "verMinor"
})
public class Version
    extends ServiceDataObject
{

    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "CreateDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    @XmlElement(name = "FileCreateDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fileCreateDate;
    @XmlElement(name = "FileCreator")
    protected String fileCreator;
    @XmlElement(name = "FileDataSize")
    protected long fileDataSize;
    @XmlElement(name = "FileModifyDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fileModifyDate;
    @XmlElement(name = "Filename")
    protected String filename;
    @XmlElement(name = "FilePlatform")
    protected int filePlatform;
    @XmlElement(name = "FileResSize")
    protected long fileResSize;
    @XmlElement(name = "FileType")
    protected String fileType;
    @XmlElement(name = "ID")
    protected long id;
    @XmlElement(name = "Locked")
    protected int locked;
    @XmlElement(name = "LockedBy", required = true, type = Long.class, nillable = true)
    protected Long lockedBy;
    @XmlElement(name = "LockedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lockedDate;
    @XmlElement(name = "Metadata")
    protected Metadata metadata;
    @XmlElement(name = "MimeType")
    protected String mimeType;
    @XmlElement(name = "ModifyDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifyDate;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "NodeID")
    protected long nodeID;
    @XmlElement(name = "Number")
    protected long number;
    @XmlElement(name = "Owner")
    protected long owner;
    @XmlElement(name = "ProviderID")
    protected long providerID;
    @XmlElement(name = "ProviderName")
    protected String providerName;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "VerMajor")
    protected long verMajor;
    @XmlElement(name = "VerMinor")
    protected long verMinor;

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
     * Recupera il valore della proprietà createDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * Imposta il valore della proprietà createDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDate(XMLGregorianCalendar value) {
        this.createDate = value;
    }

    /**
     * Recupera il valore della proprietà fileCreateDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFileCreateDate() {
        return fileCreateDate;
    }

    /**
     * Imposta il valore della proprietà fileCreateDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFileCreateDate(XMLGregorianCalendar value) {
        this.fileCreateDate = value;
    }

    /**
     * Recupera il valore della proprietà fileCreator.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileCreator() {
        return fileCreator;
    }

    /**
     * Imposta il valore della proprietà fileCreator.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileCreator(String value) {
        this.fileCreator = value;
    }

    /**
     * Recupera il valore della proprietà fileDataSize.
     * 
     */
    public long getFileDataSize() {
        return fileDataSize;
    }

    /**
     * Imposta il valore della proprietà fileDataSize.
     * 
     */
    public void setFileDataSize(long value) {
        this.fileDataSize = value;
    }

    /**
     * Recupera il valore della proprietà fileModifyDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFileModifyDate() {
        return fileModifyDate;
    }

    /**
     * Imposta il valore della proprietà fileModifyDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFileModifyDate(XMLGregorianCalendar value) {
        this.fileModifyDate = value;
    }

    /**
     * Recupera il valore della proprietà filename.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Imposta il valore della proprietà filename.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Recupera il valore della proprietà filePlatform.
     * 
     */
    public int getFilePlatform() {
        return filePlatform;
    }

    /**
     * Imposta il valore della proprietà filePlatform.
     * 
     */
    public void setFilePlatform(int value) {
        this.filePlatform = value;
    }

    /**
     * Recupera il valore della proprietà fileResSize.
     * 
     */
    public long getFileResSize() {
        return fileResSize;
    }

    /**
     * Imposta il valore della proprietà fileResSize.
     * 
     */
    public void setFileResSize(long value) {
        this.fileResSize = value;
    }

    /**
     * Recupera il valore della proprietà fileType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Imposta il valore della proprietà fileType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileType(String value) {
        this.fileType = value;
    }

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
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLockedBy() {
        return lockedBy;
    }

    /**
     * Imposta il valore della proprietà lockedBy.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLockedBy(Long value) {
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
     * Recupera il valore della proprietà metadata.
     * 
     * @return
     *     possible object is
     *     {@link Metadata }
     *     
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Imposta il valore della proprietà metadata.
     * 
     * @param value
     *     allowed object is
     *     {@link Metadata }
     *     
     */
    public void setMetadata(Metadata value) {
        this.metadata = value;
    }

    /**
     * Recupera il valore della proprietà mimeType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Imposta il valore della proprietà mimeType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * Recupera il valore della proprietà modifyDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifyDate() {
        return modifyDate;
    }

    /**
     * Imposta il valore della proprietà modifyDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifyDate(XMLGregorianCalendar value) {
        this.modifyDate = value;
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
     * Recupera il valore della proprietà nodeID.
     * 
     */
    public long getNodeID() {
        return nodeID;
    }

    /**
     * Imposta il valore della proprietà nodeID.
     * 
     */
    public void setNodeID(long value) {
        this.nodeID = value;
    }

    /**
     * Recupera il valore della proprietà number.
     * 
     */
    public long getNumber() {
        return number;
    }

    /**
     * Imposta il valore della proprietà number.
     * 
     */
    public void setNumber(long value) {
        this.number = value;
    }

    /**
     * Recupera il valore della proprietà owner.
     * 
     */
    public long getOwner() {
        return owner;
    }

    /**
     * Imposta il valore della proprietà owner.
     * 
     */
    public void setOwner(long value) {
        this.owner = value;
    }

    /**
     * Recupera il valore della proprietà providerID.
     * 
     */
    public long getProviderID() {
        return providerID;
    }

    /**
     * Imposta il valore della proprietà providerID.
     * 
     */
    public void setProviderID(long value) {
        this.providerID = value;
    }

    /**
     * Recupera il valore della proprietà providerName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Imposta il valore della proprietà providerName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderName(String value) {
        this.providerName = value;
    }

    /**
     * Recupera il valore della proprietà type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Imposta il valore della proprietà type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Recupera il valore della proprietà verMajor.
     * 
     */
    public long getVerMajor() {
        return verMajor;
    }

    /**
     * Imposta il valore della proprietà verMajor.
     * 
     */
    public void setVerMajor(long value) {
        this.verMajor = value;
    }

    /**
     * Recupera il valore della proprietà verMinor.
     * 
     */
    public long getVerMinor() {
        return verMinor;
    }

    /**
     * Imposta il valore della proprietà verMinor.
     * 
     */
    public void setVerMinor(long value) {
        this.verMinor = value;
    }

}
