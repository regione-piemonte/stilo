
package com.opentext.livelink.service.documentmanagement;

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
 *         &lt;element name="nodeID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="versionNum" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="renditionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "nodeID",
    "versionNum",
    "renditionType"
})
@XmlRootElement(name = "DeleteRendition")
public class DeleteRendition {

    protected long nodeID;
    protected long versionNum;
    protected String renditionType;

    /**
     * Recupera il valore della proprietà nodeID.
     * 
     */
    public long getNodeID() {
        return nodeID;
    }

    /**
     * Imposta il valore della proprietà nodeID.
     * 
     */
    public void setNodeID(long value) {
        this.nodeID = value;
    }

    /**
     * Recupera il valore della proprietà versionNum.
     * 
     */
    public long getVersionNum() {
        return versionNum;
    }

    /**
     * Imposta il valore della proprietà versionNum.
     * 
     */
    public void setVersionNum(long value) {
        this.versionNum = value;
    }

    /**
     * Recupera il valore della proprietà renditionType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRenditionType() {
        return renditionType;
    }

    /**
     * Imposta il valore della proprietà renditionType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRenditionType(String value) {
        this.renditionType = value;
    }

}
