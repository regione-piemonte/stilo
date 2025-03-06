
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
 *         &lt;element name="parentID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="pageSpec" type="{urn:DocMan.service.livelink.opentext.com}NodePageSpecification" minOccurs="0"/>
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
    "parentID",
    "pageSpec"
})
@XmlRootElement(name = "ListNodesByPage")
public class ListNodesByPage {

    protected long parentID;
    protected NodePageSpecification pageSpec;

    /**
     * Recupera il valore della proprietà parentID.
     * 
     */
    public long getParentID() {
        return parentID;
    }

    /**
     * Imposta il valore della proprietà parentID.
     * 
     */
    public void setParentID(long value) {
        this.parentID = value;
    }

    /**
     * Recupera il valore della proprietà pageSpec.
     * 
     * @return
     *     possible object is
     *     {@link NodePageSpecification }
     *     
     */
    public NodePageSpecification getPageSpec() {
        return pageSpec;
    }

    /**
     * Imposta il valore della proprietà pageSpec.
     * 
     * @param value
     *     allowed object is
     *     {@link NodePageSpecification }
     *     
     */
    public void setPageSpec(NodePageSpecification value) {
        this.pageSpec = value;
    }

}
