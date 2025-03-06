
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NodeRightUpdateInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodeRightUpdateInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="Context" type="{urn:Core.service.livelink.opentext.com}ChunkedOperationContext" minOccurs="0"/>
 *         &lt;element name="NodeCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SkippedNodeCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalNodeCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeRightUpdateInfo", propOrder = {
    "context",
    "nodeCount",
    "skippedNodeCount",
    "totalNodeCount"
})
public class NodeRightUpdateInfo
    extends ServiceDataObject
{

    @XmlElement(name = "Context")
    protected ChunkedOperationContext context;
    @XmlElement(name = "NodeCount")
    protected int nodeCount;
    @XmlElement(name = "SkippedNodeCount")
    protected int skippedNodeCount;
    @XmlElement(name = "TotalNodeCount")
    protected int totalNodeCount;

    /**
     * Recupera il valore della proprietà context.
     * 
     * @return
     *     possible object is
     *     {@link ChunkedOperationContext }
     *     
     */
    public ChunkedOperationContext getContext() {
        return context;
    }

    /**
     * Imposta il valore della proprietà context.
     * 
     * @param value
     *     allowed object is
     *     {@link ChunkedOperationContext }
     *     
     */
    public void setContext(ChunkedOperationContext value) {
        this.context = value;
    }

    /**
     * Recupera il valore della proprietà nodeCount.
     * 
     */
    public int getNodeCount() {
        return nodeCount;
    }

    /**
     * Imposta il valore della proprietà nodeCount.
     * 
     */
    public void setNodeCount(int value) {
        this.nodeCount = value;
    }

    /**
     * Recupera il valore della proprietà skippedNodeCount.
     * 
     */
    public int getSkippedNodeCount() {
        return skippedNodeCount;
    }

    /**
     * Imposta il valore della proprietà skippedNodeCount.
     * 
     */
    public void setSkippedNodeCount(int value) {
        this.skippedNodeCount = value;
    }

    /**
     * Recupera il valore della proprietà totalNodeCount.
     * 
     */
    public int getTotalNodeCount() {
        return totalNodeCount;
    }

    /**
     * Imposta il valore della proprietà totalNodeCount.
     * 
     */
    public void setTotalNodeCount(int value) {
        this.totalNodeCount = value;
    }

}
