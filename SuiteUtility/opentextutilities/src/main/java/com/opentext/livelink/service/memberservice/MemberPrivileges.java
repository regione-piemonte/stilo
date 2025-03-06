
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per MemberPrivileges complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MemberPrivileges">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:MemberService.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="CanAdministerSystem" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CanAdministerUsers" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CreateUpdateGroups" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CreateUpdateUsers" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LoginEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PublicAccessEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MemberPrivileges", propOrder = {
    "canAdministerSystem",
    "canAdministerUsers",
    "createUpdateGroups",
    "createUpdateUsers",
    "loginEnabled",
    "publicAccessEnabled"
})
public class MemberPrivileges
    extends ServiceDataObject
{

    @XmlElement(name = "CanAdministerSystem")
    protected boolean canAdministerSystem;
    @XmlElement(name = "CanAdministerUsers")
    protected boolean canAdministerUsers;
    @XmlElement(name = "CreateUpdateGroups")
    protected boolean createUpdateGroups;
    @XmlElement(name = "CreateUpdateUsers")
    protected boolean createUpdateUsers;
    @XmlElement(name = "LoginEnabled")
    protected boolean loginEnabled;
    @XmlElement(name = "PublicAccessEnabled")
    protected boolean publicAccessEnabled;

    /**
     * Recupera il valore della proprietà canAdministerSystem.
     * 
     */
    public boolean isCanAdministerSystem() {
        return canAdministerSystem;
    }

    /**
     * Imposta il valore della proprietà canAdministerSystem.
     * 
     */
    public void setCanAdministerSystem(boolean value) {
        this.canAdministerSystem = value;
    }

    /**
     * Recupera il valore della proprietà canAdministerUsers.
     * 
     */
    public boolean isCanAdministerUsers() {
        return canAdministerUsers;
    }

    /**
     * Imposta il valore della proprietà canAdministerUsers.
     * 
     */
    public void setCanAdministerUsers(boolean value) {
        this.canAdministerUsers = value;
    }

    /**
     * Recupera il valore della proprietà createUpdateGroups.
     * 
     */
    public boolean isCreateUpdateGroups() {
        return createUpdateGroups;
    }

    /**
     * Imposta il valore della proprietà createUpdateGroups.
     * 
     */
    public void setCreateUpdateGroups(boolean value) {
        this.createUpdateGroups = value;
    }

    /**
     * Recupera il valore della proprietà createUpdateUsers.
     * 
     */
    public boolean isCreateUpdateUsers() {
        return createUpdateUsers;
    }

    /**
     * Imposta il valore della proprietà createUpdateUsers.
     * 
     */
    public void setCreateUpdateUsers(boolean value) {
        this.createUpdateUsers = value;
    }

    /**
     * Recupera il valore della proprietà loginEnabled.
     * 
     */
    public boolean isLoginEnabled() {
        return loginEnabled;
    }

    /**
     * Imposta il valore della proprietà loginEnabled.
     * 
     */
    public void setLoginEnabled(boolean value) {
        this.loginEnabled = value;
    }

    /**
     * Recupera il valore della proprietà publicAccessEnabled.
     * 
     */
    public boolean isPublicAccessEnabled() {
        return publicAccessEnabled;
    }

    /**
     * Imposta il valore della proprietà publicAccessEnabled.
     * 
     */
    public void setPublicAccessEnabled(boolean value) {
        this.publicAccessEnabled = value;
    }

}
