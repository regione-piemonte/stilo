
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CategoryItemsUpgradeInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CategoryItemsUpgradeInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="Context" type="{urn:Core.service.livelink.opentext.com}ChunkedOperationContext" minOccurs="0"/>
 *         &lt;element name="SkippedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UpgradedCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryItemsUpgradeInfo", propOrder = {
    "context",
    "skippedCount",
    "upgradedCount"
})
public class CategoryItemsUpgradeInfo
    extends ServiceDataObject
{

    @XmlElement(name = "Context")
    protected ChunkedOperationContext context;
    @XmlElement(name = "SkippedCount")
    protected int skippedCount;
    @XmlElement(name = "UpgradedCount")
    protected int upgradedCount;

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
     * Recupera il valore della proprietà skippedCount.
     * 
     */
    public int getSkippedCount() {
        return skippedCount;
    }

    /**
     * Imposta il valore della proprietà skippedCount.
     * 
     */
    public void setSkippedCount(int value) {
        this.skippedCount = value;
    }

    /**
     * Recupera il valore della proprietà upgradedCount.
     * 
     */
    public int getUpgradedCount() {
        return upgradedCount;
    }

    /**
     * Imposta il valore della proprietà upgradedCount.
     * 
     */
    public void setUpgradedCount(int value) {
        this.upgradedCount = value;
    }

}
