
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
 *         &lt;element name="CreateDomainResult" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "createDomainResult"
})
@XmlRootElement(name = "CreateDomainResponse")
public class CreateDomainResponse {

    @XmlElement(name = "CreateDomainResult")
    protected long createDomainResult;

    /**
     * Recupera il valore della proprietà createDomainResult.
     * 
     */
    public long getCreateDomainResult() {
        return createDomainResult;
    }

    /**
     * Imposta il valore della proprietà createDomainResult.
     * 
     */
    public void setCreateDomainResult(long value) {
        this.createDomainResult = value;
    }

}
