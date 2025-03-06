
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
 *         &lt;element name="groupID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="memberID" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "groupID",
    "memberID"
})
@XmlRootElement(name = "RemoveMemberFromGroup")
public class RemoveMemberFromGroup {

    protected long groupID;
    protected long memberID;

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
     * Recupera il valore della proprietà memberID.
     * 
     */
    public long getMemberID() {
        return memberID;
    }

    /**
     * Imposta il valore della proprietà memberID.
     * 
     */
    public void setMemberID(long value) {
        this.memberID = value;
    }

}
