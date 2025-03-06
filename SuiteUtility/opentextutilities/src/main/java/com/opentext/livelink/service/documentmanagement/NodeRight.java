
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NodeRight complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodeRight">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="Permissions" type="{urn:DocMan.service.livelink.opentext.com}NodePermissions" minOccurs="0"/>
 *         &lt;element name="RightID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeRight", propOrder = {
    "permissions",
    "rightID",
    "type"
})
public class NodeRight
    extends ServiceDataObject
{

    @XmlElement(name = "Permissions")
    protected NodePermissions permissions;
    @XmlElement(name = "RightID")
    protected long rightID;
    @XmlElement(name = "Type")
    protected String type;

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
     * Recupera il valore della proprietà rightID.
     * 
     */
    public long getRightID() {
        return rightID;
    }

    /**
     * Imposta il valore della proprietà rightID.
     * 
     */
    public void setRightID(long value) {
        this.rightID = value;
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

}
