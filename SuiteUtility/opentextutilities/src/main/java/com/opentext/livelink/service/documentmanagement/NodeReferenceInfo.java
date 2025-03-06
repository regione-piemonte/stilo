
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NodeReferenceInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodeReferenceInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="OriginalID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="OriginalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VersionNum" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeReferenceInfo", propOrder = {
    "originalID",
    "originalType",
    "versionNum"
})
public class NodeReferenceInfo
    extends ServiceDataObject
{

    @XmlElement(name = "OriginalID")
    protected long originalID;
    @XmlElement(name = "OriginalType")
    protected String originalType;
    @XmlElement(name = "VersionNum", required = true, type = Long.class, nillable = true)
    protected Long versionNum;

    /**
     * Recupera il valore della proprietà originalID.
     * 
     */
    public long getOriginalID() {
        return originalID;
    }

    /**
     * Imposta il valore della proprietà originalID.
     * 
     */
    public void setOriginalID(long value) {
        this.originalID = value;
    }

    /**
     * Recupera il valore della proprietà originalType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalType() {
        return originalType;
    }

    /**
     * Imposta il valore della proprietà originalType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalType(String value) {
        this.originalType = value;
    }

    /**
     * Recupera il valore della proprietà versionNum.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVersionNum() {
        return versionNum;
    }

    /**
     * Imposta il valore della proprietà versionNum.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVersionNum(Long value) {
        this.versionNum = value;
    }

}
