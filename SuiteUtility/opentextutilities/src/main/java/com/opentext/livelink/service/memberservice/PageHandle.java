
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PageHandle complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PageHandle">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:Core.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="FinalPage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="NumberOfPages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PageHandleID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PageSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageHandle", namespace = "urn:Core.service.livelink.opentext.com", propOrder = {
    "finalPage",
    "numberOfPages",
    "pageHandleID",
    "pageNumber",
    "pageSize"
})
public class PageHandle
    extends ServiceDataObject2
{

    @XmlElement(name = "FinalPage")
    protected boolean finalPage;
    @XmlElement(name = "NumberOfPages", required = true, type = Integer.class, nillable = true)
    protected Integer numberOfPages;
    @XmlElement(name = "PageHandleID", required = true, type = Integer.class, nillable = true)
    protected Integer pageHandleID;
    @XmlElement(name = "PageNumber", required = true, type = Integer.class, nillable = true)
    protected Integer pageNumber;
    @XmlElement(name = "PageSize")
    protected int pageSize;

    /**
     * Recupera il valore della proprietà finalPage.
     * 
     */
    public boolean isFinalPage() {
        return finalPage;
    }

    /**
     * Imposta il valore della proprietà finalPage.
     * 
     */
    public void setFinalPage(boolean value) {
        this.finalPage = value;
    }

    /**
     * Recupera il valore della proprietà numberOfPages.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Imposta il valore della proprietà numberOfPages.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfPages(Integer value) {
        this.numberOfPages = value;
    }

    /**
     * Recupera il valore della proprietà pageHandleID.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageHandleID() {
        return pageHandleID;
    }

    /**
     * Imposta il valore della proprietà pageHandleID.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageHandleID(Integer value) {
        this.pageHandleID = value;
    }

    /**
     * Recupera il valore della proprietà pageNumber.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * Imposta il valore della proprietà pageNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageNumber(Integer value) {
        this.pageNumber = value;
    }

    /**
     * Recupera il valore della proprietà pageSize.
     * 
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Imposta il valore della proprietà pageSize.
     * 
     */
    public void setPageSize(int value) {
        this.pageSize = value;
    }

}
