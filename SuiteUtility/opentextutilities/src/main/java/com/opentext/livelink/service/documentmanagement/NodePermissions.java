
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NodePermissions complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodePermissions">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="AddItemsPermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DeletePermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DeleteVersionsPermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EditAttributesPermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EditPermissionsPermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ModifyPermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ReservePermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SeeContentsPermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SeePermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodePermissions", propOrder = {
    "addItemsPermission",
    "deletePermission",
    "deleteVersionsPermission",
    "editAttributesPermission",
    "editPermissionsPermission",
    "modifyPermission",
    "reservePermission",
    "seeContentsPermission",
    "seePermission"
})
public class NodePermissions
    extends ServiceDataObject
{

    @XmlElement(name = "AddItemsPermission")
    protected boolean addItemsPermission;
    @XmlElement(name = "DeletePermission")
    protected boolean deletePermission;
    @XmlElement(name = "DeleteVersionsPermission")
    protected boolean deleteVersionsPermission;
    @XmlElement(name = "EditAttributesPermission")
    protected boolean editAttributesPermission;
    @XmlElement(name = "EditPermissionsPermission")
    protected boolean editPermissionsPermission;
    @XmlElement(name = "ModifyPermission")
    protected boolean modifyPermission;
    @XmlElement(name = "ReservePermission")
    protected boolean reservePermission;
    @XmlElement(name = "SeeContentsPermission")
    protected boolean seeContentsPermission;
    @XmlElement(name = "SeePermission")
    protected boolean seePermission;

    /**
     * Recupera il valore della proprietà addItemsPermission.
     * 
     */
    public boolean isAddItemsPermission() {
        return addItemsPermission;
    }

    /**
     * Imposta il valore della proprietà addItemsPermission.
     * 
     */
    public void setAddItemsPermission(boolean value) {
        this.addItemsPermission = value;
    }

    /**
     * Recupera il valore della proprietà deletePermission.
     * 
     */
    public boolean isDeletePermission() {
        return deletePermission;
    }

    /**
     * Imposta il valore della proprietà deletePermission.
     * 
     */
    public void setDeletePermission(boolean value) {
        this.deletePermission = value;
    }

    /**
     * Recupera il valore della proprietà deleteVersionsPermission.
     * 
     */
    public boolean isDeleteVersionsPermission() {
        return deleteVersionsPermission;
    }

    /**
     * Imposta il valore della proprietà deleteVersionsPermission.
     * 
     */
    public void setDeleteVersionsPermission(boolean value) {
        this.deleteVersionsPermission = value;
    }

    /**
     * Recupera il valore della proprietà editAttributesPermission.
     * 
     */
    public boolean isEditAttributesPermission() {
        return editAttributesPermission;
    }

    /**
     * Imposta il valore della proprietà editAttributesPermission.
     * 
     */
    public void setEditAttributesPermission(boolean value) {
        this.editAttributesPermission = value;
    }

    /**
     * Recupera il valore della proprietà editPermissionsPermission.
     * 
     */
    public boolean isEditPermissionsPermission() {
        return editPermissionsPermission;
    }

    /**
     * Imposta il valore della proprietà editPermissionsPermission.
     * 
     */
    public void setEditPermissionsPermission(boolean value) {
        this.editPermissionsPermission = value;
    }

    /**
     * Recupera il valore della proprietà modifyPermission.
     * 
     */
    public boolean isModifyPermission() {
        return modifyPermission;
    }

    /**
     * Imposta il valore della proprietà modifyPermission.
     * 
     */
    public void setModifyPermission(boolean value) {
        this.modifyPermission = value;
    }

    /**
     * Recupera il valore della proprietà reservePermission.
     * 
     */
    public boolean isReservePermission() {
        return reservePermission;
    }

    /**
     * Imposta il valore della proprietà reservePermission.
     * 
     */
    public void setReservePermission(boolean value) {
        this.reservePermission = value;
    }

    /**
     * Recupera il valore della proprietà seeContentsPermission.
     * 
     */
    public boolean isSeeContentsPermission() {
        return seeContentsPermission;
    }

    /**
     * Imposta il valore della proprietà seeContentsPermission.
     * 
     */
    public void setSeeContentsPermission(boolean value) {
        this.seeContentsPermission = value;
    }

    /**
     * Recupera il valore della proprietà seePermission.
     * 
     */
    public boolean isSeePermission() {
        return seePermission;
    }

    /**
     * Imposta il valore della proprietà seePermission.
     * 
     */
    public void setSeePermission(boolean value) {
        this.seePermission = value;
    }

}
