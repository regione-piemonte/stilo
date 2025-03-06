
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Group complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Group">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:MemberService.service.livelink.opentext.com}Member">
 *       &lt;sequence>
 *         &lt;element name="LeaderID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Group", propOrder = {
    "leaderID"
})
public class Group
    extends Member
{

    @XmlElement(name = "LeaderID", required = true, type = Long.class, nillable = true)
    protected Long leaderID;

    /**
     * Recupera il valore della proprietà leaderID.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLeaderID() {
        return leaderID;
    }

    /**
     * Imposta il valore della proprietà leaderID.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLeaderID(Long value) {
        this.leaderID = value;
    }

}
