
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per GetNodesInContainerOptions complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GetNodesInContainerOptions">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="MaxDepth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxResults" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetNodesInContainerOptions", propOrder = {
    "maxDepth",
    "maxResults"
})
public class GetNodesInContainerOptions
    extends ServiceDataObject
{

    @XmlElement(name = "MaxDepth")
    protected int maxDepth;
    @XmlElement(name = "MaxResults")
    protected int maxResults;

    /**
     * Recupera il valore della proprietà maxDepth.
     * 
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Imposta il valore della proprietà maxDepth.
     * 
     */
    public void setMaxDepth(int value) {
        this.maxDepth = value;
    }

    /**
     * Recupera il valore della proprietà maxResults.
     * 
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * Imposta il valore della proprietà maxResults.
     * 
     */
    public void setMaxResults(int value) {
        this.maxResults = value;
    }

}
