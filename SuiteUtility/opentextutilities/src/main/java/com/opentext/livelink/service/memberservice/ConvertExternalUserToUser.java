
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="groupID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="memberPrivileges" type="{urn:MemberService.service.livelink.opentext.com}MemberPrivileges" minOccurs="0"/>
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
    "userID",
    "groupID",
    "memberPrivileges"
})
@XmlRootElement(name = "ConvertExternalUserToUser")
public class ConvertExternalUserToUser {

    protected long userID;
    protected long groupID;
    protected MemberPrivileges memberPrivileges;

    /**
     * Recupera il valore della proprietà userID.
     * 
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Imposta il valore della proprietà userID.
     * 
     */
    public void setUserID(long value) {
        this.userID = value;
    }

    /**
     * Recupera il valore della proprietà groupID.
     * 
     */
    public long getGroupID() {
        return groupID;
    }

    /**
     * Imposta il valore della proprietà groupID.
     * 
     */
    public void setGroupID(long value) {
        this.groupID = value;
    }

    /**
     * Recupera il valore della proprietà memberPrivileges.
     * 
     * @return
     *     possible object is
     *     {@link MemberPrivileges }
     *     
     */
    public MemberPrivileges getMemberPrivileges() {
        return memberPrivileges;
    }

    /**
     * Imposta il valore della proprietà memberPrivileges.
     * 
     * @param value
     *     allowed object is
     *     {@link MemberPrivileges }
     *     
     */
    public void setMemberPrivileges(MemberPrivileges value) {
        this.memberPrivileges = value;
    }

}
