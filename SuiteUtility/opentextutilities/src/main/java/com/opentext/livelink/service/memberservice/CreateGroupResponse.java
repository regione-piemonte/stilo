
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="CreateGroupResult" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "createGroupResult"
})
@XmlRootElement(name = "CreateGroupResponse")
public class CreateGroupResponse {

    @XmlElement(name = "CreateGroupResult")
    protected long createGroupResult;

    /**
     * Recupera il valore della proprietà createGroupResult.
     * 
     */
    public long getCreateGroupResult() {
        return createGroupResult;
    }

    /**
     * Imposta il valore della proprietà createGroupResult.
     * 
     */
    public void setCreateGroupResult(long value) {
        this.createGroupResult = value;
    }

}
