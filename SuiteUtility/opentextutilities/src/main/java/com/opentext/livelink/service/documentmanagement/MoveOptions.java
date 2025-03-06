
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per MoveOptions complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MoveOptions">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="AddVersion" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AttrSourceType" type="{urn:DocMan.service.livelink.opentext.com}AttributeSourceType"/>
 *         &lt;element name="ForceInheritPermissions" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MoveOptions", propOrder = {
    "addVersion",
    "attrSourceType",
    "forceInheritPermissions"
})
public class MoveOptions
    extends ServiceDataObject
{

    @XmlElement(name = "AddVersion")
    protected boolean addVersion;
    @XmlElement(name = "AttrSourceType", required = true)
    @XmlSchemaType(name = "string")
    protected AttributeSourceType attrSourceType;
    @XmlElement(name = "ForceInheritPermissions")
    protected boolean forceInheritPermissions;

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
     * Recupera il valore della proprietà forceInheritPermissions.
     * 
     */
    public boolean isForceInheritPermissions() {
        return forceInheritPermissions;
    }

    /**
     * Imposta il valore della proprietà forceInheritPermissions.
     * 
     */
    public void setForceInheritPermissions(boolean value) {
        this.forceInheritPermissions = value;
    }

}
