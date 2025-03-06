
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
 *         &lt;element name="CreateExternalGroupResult" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "createExternalGroupResult"
})
@XmlRootElement(name = "CreateExternalGroupResponse")
public class CreateExternalGroupResponse {

    @XmlElement(name = "CreateExternalGroupResult")
    protected long createExternalGroupResult;

    /**
     * Recupera il valore della proprietà createExternalGroupResult.
     * 
     */
    public long getCreateExternalGroupResult() {
        return createExternalGroupResult;
    }

    /**
     * Imposta il valore della proprietà createExternalGroupResult.
     * 
     */
    public void setCreateExternalGroupResult(long value) {
        this.createExternalGroupResult = value;
    }

}
