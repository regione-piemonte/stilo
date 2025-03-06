
package com.opentext.livelink.service.documentmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per Node complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Node">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="Catalog" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ContainerInfo" type="{urn:DocMan.service.livelink.opentext.com}NodeContainerInfo" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="DisplayType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Features" type="{urn:DocMan.service.livelink.opentext.com}NodeFeature" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IsContainer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsReference" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsReservable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsVersionable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Metadata" type="{urn:DocMan.service.livelink.opentext.com}Metadata" minOccurs="0"/>
 *         &lt;element name="ModifyDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nickname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ParentID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PartialData" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Permissions" type="{urn:DocMan.service.livelink.opentext.com}NodePermissions" minOccurs="0"/>
 *         &lt;element name="Position" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ReferenceInfo" type="{urn:DocMan.service.livelink.opentext.com}NodeReferenceInfo" minOccurs="0"/>
 *         &lt;element name="Released" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ReservationInfo" type="{urn:DocMan.service.livelink.opentext.com}NodeReservationInfo" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VersionInfo" type="{urn:DocMan.service.livelink.opentext.com}NodeVersionInfo" minOccurs="0"/>
 *         &lt;element name="VolumeID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Node", propOrder = {
    "catalog",
    "comment",
    "containerInfo",
    "createDate",
    "createdBy",
    "displayType",
    "features",
    "id",
    "isContainer",
    "isReference",
    "isReservable",
    "isVersionable",
    "metadata",
    "modifyDate",
    "name",
    "nickname",
    "parentID",
    "partialData",
    "permissions",
    "position",
    "referenceInfo",
    "released",
    "reservationInfo",
    "type",
    "versionInfo",
    "volumeID"
})
public class Node
    extends ServiceDataObject
{

    @XmlElement(name = "Catalog", required = true, type = Integer.class, nillable = true)
    protected Integer catalog;
    @XmlElement(name = "Comment", required = true, nillable = true)
    protected String comment;
    @XmlElement(name = "ContainerInfo")
    protected NodeContainerInfo containerInfo;
    @XmlElement(name = "CreateDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    @XmlElement(name = "CreatedBy", required = true, type = Long.class, nillable = true)
    protected Long createdBy;
    @XmlElement(name = "DisplayType")
    protected String displayType;
    @XmlElement(name = "Features")
    protected List<NodeFeature> features;
    @XmlElement(name = "ID")
    protected long id;
    @XmlElement(name = "IsContainer")
    protected boolean isContainer;
    @XmlElement(name = "IsReference")
    protected boolean isReference;
    @XmlElement(name = "IsReservable")
    protected boolean isReservable;
    @XmlElement(name = "IsVersionable")
    protected boolean isVersionable;
    @XmlElement(name = "Metadata")
    protected Metadata metadata;
    @XmlElement(name = "ModifyDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifyDate;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Nickname", required = true, nillable = true)
    protected String nickname;
    @XmlElement(name = "ParentID")
    protected long parentID;
    @XmlElement(name = "PartialData")
    protected boolean partialData;
    @XmlElement(name = "Permissions")
    protected NodePermissions permissions;
    @XmlElement(name = "Position", required = true, type = Long.class, nillable = true)
    protected Long position;
    @XmlElement(name = "ReferenceInfo")
    protected NodeReferenceInfo referenceInfo;
    @XmlElement(name = "Released")
    protected boolean released;
    @XmlElement(name = "ReservationInfo")
    protected NodeReservationInfo reservationInfo;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "VersionInfo")
    protected NodeVersionInfo versionInfo;
    @XmlElement(name = "VolumeID")
    protected long volumeID;

    /**
     * Recupera il valore della proprietà catalog.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCatalog() {
        return catalog;
    }

    /**
     * Imposta il valore della proprietà catalog.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCatalog(Integer value) {
        this.catalog = value;
    }

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
     * Recupera il valore della proprietà containerInfo.
     * 
     * @return
     *     possible object is
     *     {@link NodeContainerInfo }
     *     
     */
    public NodeContainerInfo getContainerInfo() {
        return containerInfo;
    }

    /**
     * Imposta il valore della proprietà containerInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeContainerInfo }
     *     
     */
    public void setContainerInfo(NodeContainerInfo value) {
        this.containerInfo = value;
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
     * Recupera il valore della proprietà createdBy.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * Imposta il valore della proprietà createdBy.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCreatedBy(Long value) {
        this.createdBy = value;
    }

    /**
     * Recupera il valore della proprietà displayType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayType() {
        return displayType;
    }

    /**
     * Imposta il valore della proprietà displayType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayType(String value) {
        this.displayType = value;
    }

    /**
     * Gets the value of the features property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the features property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NodeFeature }
     * 
     * 
     */
    public List<NodeFeature> getFeatures() {
        if (features == null) {
            features = new ArrayList<NodeFeature>();
        }
        return this.features;
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
     * Recupera il valore della proprietà isContainer.
     * 
     */
    public boolean isIsContainer() {
        return isContainer;
    }

    /**
     * Imposta il valore della proprietà isContainer.
     * 
     */
    public void setIsContainer(boolean value) {
        this.isContainer = value;
    }

    /**
     * Recupera il valore della proprietà isReference.
     * 
     */
    public boolean isIsReference() {
        return isReference;
    }

    /**
     * Imposta il valore della proprietà isReference.
     * 
     */
    public void setIsReference(boolean value) {
        this.isReference = value;
    }

    /**
     * Recupera il valore della proprietà isReservable.
     * 
     */
    public boolean isIsReservable() {
        return isReservable;
    }

    /**
     * Imposta il valore della proprietà isReservable.
     * 
     */
    public void setIsReservable(boolean value) {
        this.isReservable = value;
    }

    /**
     * Recupera il valore della proprietà isVersionable.
     * 
     */
    public boolean isIsVersionable() {
        return isVersionable;
    }

    /**
     * Imposta il valore della proprietà isVersionable.
     * 
     */
    public void setIsVersionable(boolean value) {
        this.isVersionable = value;
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
     * Recupera il valore della proprietà nickname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Imposta il valore della proprietà nickname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickname(String value) {
        this.nickname = value;
    }

    /**
     * Recupera il valore della proprietà parentID.
     * 
     */
    public long getParentID() {
        return parentID;
    }

    /**
     * Imposta il valore della proprietà parentID.
     * 
     */
    public void setParentID(long value) {
        this.parentID = value;
    }

    /**
     * Recupera il valore della proprietà partialData.
     * 
     */
    public boolean isPartialData() {
        return partialData;
    }

    /**
     * Imposta il valore della proprietà partialData.
     * 
     */
    public void setPartialData(boolean value) {
        this.partialData = value;
    }

    /**
     * Recupera il valore della proprietà permissions.
     * 
     * @return
     *     possible object is
     *     {@link NodePermissions }
     *     
     */
    public NodePermissions getPermissions() {
        return permissions;
    }

    /**
     * Imposta il valore della proprietà permissions.
     * 
     * @param value
     *     allowed object is
     *     {@link NodePermissions }
     *     
     */
    public void setPermissions(NodePermissions value) {
        this.permissions = value;
    }

    /**
     * Recupera il valore della proprietà position.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPosition() {
        return position;
    }

    /**
     * Imposta il valore della proprietà position.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPosition(Long value) {
        this.position = value;
    }

    /**
     * Recupera il valore della proprietà referenceInfo.
     * 
     * @return
     *     possible object is
     *     {@link NodeReferenceInfo }
     *     
     */
    public NodeReferenceInfo getReferenceInfo() {
        return referenceInfo;
    }

    /**
     * Imposta il valore della proprietà referenceInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeReferenceInfo }
     *     
     */
    public void setReferenceInfo(NodeReferenceInfo value) {
        this.referenceInfo = value;
    }

    /**
     * Recupera il valore della proprietà released.
     * 
     */
    public boolean isReleased() {
        return released;
    }

    /**
     * Imposta il valore della proprietà released.
     * 
     */
    public void setReleased(boolean value) {
        this.released = value;
    }

    /**
     * Recupera il valore della proprietà reservationInfo.
     * 
     * @return
     *     possible object is
     *     {@link NodeReservationInfo }
     *     
     */
    public NodeReservationInfo getReservationInfo() {
        return reservationInfo;
    }

    /**
     * Imposta il valore della proprietà reservationInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeReservationInfo }
     *     
     */
    public void setReservationInfo(NodeReservationInfo value) {
        this.reservationInfo = value;
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
     * Recupera il valore della proprietà versionInfo.
     * 
     * @return
     *     possible object is
     *     {@link NodeVersionInfo }
     *     
     */
    public NodeVersionInfo getVersionInfo() {
        return versionInfo;
    }

    /**
     * Imposta il valore della proprietà versionInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeVersionInfo }
     *     
     */
    public void setVersionInfo(NodeVersionInfo value) {
        this.versionInfo = value;
    }

    /**
     * Recupera il valore della proprietà volumeID.
     * 
     */
    public long getVolumeID() {
        return volumeID;
    }

    /**
     * Imposta il valore della proprietà volumeID.
     * 
     */
    public void setVolumeID(long value) {
        this.volumeID = value;
    }

}
