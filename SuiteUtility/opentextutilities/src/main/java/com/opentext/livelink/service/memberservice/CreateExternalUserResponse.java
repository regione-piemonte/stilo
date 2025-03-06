
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
 *         &lt;element name="CreateExternalUserResult" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "createExternalUserResult"
})
@XmlRootElement(name = "CreateExternalUserResponse")
public class CreateExternalUserResponse {

    @XmlElement(name = "CreateExternalUserResult")
    protected long createExternalUserResult;

    /**
     * Recupera il valore della proprietà createExternalUserResult.
     * 
     */
    public long getCreateExternalUserResult() {
        return createExternalUserResult;
    }

    /**
     * Imposta il valore della proprietà createExternalUserResult.
     * 
     */
    public void setCreateExternalUserResult(long value) {
        this.createExternalUserResult = value;
    }

}
