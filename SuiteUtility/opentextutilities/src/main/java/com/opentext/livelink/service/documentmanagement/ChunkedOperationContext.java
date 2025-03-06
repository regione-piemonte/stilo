
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ChunkedOperationContext complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ChunkedOperationContext">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:Core.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="ChunkSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ContextID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Finished" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChunkedOperationContext", namespace = "urn:Core.service.livelink.opentext.com", propOrder = {
    "chunkSize",
    "contextID",
    "finished"
})
public class ChunkedOperationContext
    extends ServiceDataObject2
{

    @XmlElement(name = "ChunkSize", required = true, type = Integer.class, nillable = true)
    protected Integer chunkSize;
    @XmlElement(name = "ContextID", required = true, type = Integer.class, nillable = true)
    protected Integer contextID;
    @XmlElement(name = "Finished")
    protected boolean finished;

    /**
     * Recupera il valore della proprietà chunkSize.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getChunkSize() {
        return chunkSize;
    }

    /**
     * Imposta il valore della proprietà chunkSize.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setChunkSize(Integer value) {
        this.chunkSize = value;
    }

    /**
     * Recupera il valore della proprietà contextID.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getContextID() {
        return contextID;
    }

    /**
     * Imposta il valore della proprietà contextID.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setContextID(Integer value) {
        this.contextID = value;
    }

    /**
     * Recupera il valore della proprietà finished.
     * 
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Imposta il valore della proprietà finished.
     * 
     */
    public void setFinished(boolean value) {
        this.finished = value;
    }

}
