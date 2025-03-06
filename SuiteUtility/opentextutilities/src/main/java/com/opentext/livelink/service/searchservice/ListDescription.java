
package com.opentext.livelink.service.searchservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ListDescription complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ListDescription">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:SearchServices.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="ActualCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IncludeCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ListHead" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListDescription", propOrder = {
    "actualCount",
    "includeCount",
    "listHead"
})
public class ListDescription
    extends ServiceDataObject
{

    @XmlElement(name = "ActualCount")
    protected int actualCount;
    @XmlElement(name = "IncludeCount")
    protected int includeCount;
    @XmlElement(name = "ListHead")
    protected int listHead;

    /**
     * Recupera il valore della proprietà actualCount.
     * 
     */
    public int getActualCount() {
        return actualCount;
    }

    /**
     * Imposta il valore della proprietà actualCount.
     * 
     */
    public void setActualCount(int value) {
        this.actualCount = value;
    }

    /**
     * Recupera il valore della proprietà includeCount.
     * 
     */
    public int getIncludeCount() {
        return includeCount;
    }

    /**
     * Imposta il valore della proprietà includeCount.
     * 
     */
    public void setIncludeCount(int value) {
        this.includeCount = value;
    }

    /**
     * Recupera il valore della proprietà listHead.
     * 
     */
    public int getListHead() {
        return listHead;
    }

    /**
     * Imposta il valore della proprietà listHead.
     * 
     */
    public void setListHead(int value) {
        this.listHead = value;
    }

}
