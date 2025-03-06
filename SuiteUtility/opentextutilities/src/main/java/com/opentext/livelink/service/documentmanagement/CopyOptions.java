
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CopyOptions complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CopyOptions">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="AddVersion" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AttrSourceType" type="{urn:DocMan.service.livelink.opentext.com}AttributeSourceType"/>
 *         &lt;element name="CopyCurrent" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CurrentUserAsOwner" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="KeepReservedState" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="KeepVersionLocks" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CopyOptions", propOrder = {
    "addVersion",
    "attrSourceType",
    "copyCurrent",
    "currentUserAsOwner",
    "keepReservedState",
    "keepVersionLocks"
})
public class CopyOptions
    extends ServiceDataObject
{

    @XmlElement(name = "AddVersion")
    protected boolean addVersion;
    @XmlElement(name = "AttrSourceType", required = true)
    @XmlSchemaType(name = "string")
    protected AttributeSourceType attrSourceType;
    @XmlElement(name = "CopyCurrent")
    protected boolean copyCurrent;
    @XmlElement(name = "CurrentUserAsOwner")
    protected boolean currentUserAsOwner;
    @XmlElement(name = "KeepReservedState")
    protected boolean keepReservedState;
    @XmlElement(name = "KeepVersionLocks")
    protected boolean keepVersionLocks;

    /**
     * Recupera il valore della proprietà addVersion.
     * 
     */
    public boolean isAddVersion() {
        return addVersion;
    }

    /**
     * Imposta il valore della proprietà addVersion.
     * 
     */
    public void setAddVersion(boolean value) {
        this.addVersion = value;
    }

    /**
     * Recupera il valore della proprietà attrSourceType.
     * 
     * @return
     *     possible object is
     *     {@link AttributeSourceType }
     *     
     */
    public AttributeSourceType getAttrSourceType() {
        return attrSourceType;
    }

    /**
     * Imposta il valore della proprietà attrSourceType.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeSourceType }
     *     
     */
    public void setAttrSourceType(AttributeSourceType value) {
        this.attrSourceType = value;
    }

    /**
     * Recupera il valore della proprietà copyCurrent.
     * 
     */
    public boolean isCopyCurrent() {
        return copyCurrent;
    }

    /**
     * Imposta il valore della proprietà copyCurrent.
     * 
     */
    public void setCopyCurrent(boolean value) {
        this.copyCurrent = value;
    }

    /**
     * Recupera il valore della proprietà currentUserAsOwner.
     * 
     */
    public boolean isCurrentUserAsOwner() {
        return currentUserAsOwner;
    }

    /**
     * Imposta il valore della proprietà currentUserAsOwner.
     * 
     */
    public void setCurrentUserAsOwner(boolean value) {
        this.currentUserAsOwner = value;
    }

    /**
     * Recupera il valore della proprietà keepReservedState.
     * 
     */
    public boolean isKeepReservedState() {
        return keepReservedState;
    }

    /**
     * Imposta il valore della proprietà keepReservedState.
     * 
     */
    public void setKeepReservedState(boolean value) {
        this.keepReservedState = value;
    }

    /**
     * Recupera il valore della proprietà keepVersionLocks.
     * 
     */
    public boolean isKeepVersionLocks() {
        return keepVersionLocks;
    }

    /**
     * Imposta il valore della proprietà keepVersionLocks.
     * 
     */
    public void setKeepVersionLocks(boolean value) {
        this.keepVersionLocks = value;
    }

}
