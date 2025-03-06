
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per MemberSearchOptions complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MemberSearchOptions">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:MemberService.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="Column" type="{urn:MemberService.service.livelink.opentext.com}SearchColumn"/>
 *         &lt;element name="DomainID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Filter" type="{urn:MemberService.service.livelink.opentext.com}SearchFilter"/>
 *         &lt;element name="GroupID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Matching" type="{urn:MemberService.service.livelink.opentext.com}SearchMatching"/>
 *         &lt;element name="PageSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Scope" type="{urn:MemberService.service.livelink.opentext.com}SearchScope"/>
 *         &lt;element name="Search" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MemberSearchOptions", propOrder = {
    "column",
    "domainID",
    "filter",
    "groupID",
    "matching",
    "pageSize",
    "scope",
    "search"
})
public class MemberSearchOptions
    extends ServiceDataObject
{

    @XmlElement(name = "Column", required = true)
    @XmlSchemaType(name = "string")
    protected SearchColumn column;
    @XmlElement(name = "DomainID")
    protected long domainID;
    @XmlElement(name = "Filter", required = true)
    @XmlSchemaType(name = "string")
    protected SearchFilter filter;
    @XmlElement(name = "GroupID")
    protected long groupID;
    @XmlElement(name = "Matching", required = true)
    @XmlSchemaType(name = "string")
    protected SearchMatching matching;
    @XmlElement(name = "PageSize")
    protected int pageSize;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected SearchScope scope;
    @XmlElement(name = "Search")
    protected String search;

    /**
     * Recupera il valore della proprietà column.
     * 
     * @return
     *     possible object is
     *     {@link SearchColumn }
     *     
     */
    public SearchColumn getColumn() {
        return column;
    }

    /**
     * Imposta il valore della proprietà column.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchColumn }
     *     
     */
    public void setColumn(SearchColumn value) {
        this.column = value;
    }

    /**
     * Recupera il valore della proprietà domainID.
     * 
     */
    public long getDomainID() {
        return domainID;
    }

    /**
     * Imposta il valore della proprietà domainID.
     * 
     */
    public void setDomainID(long value) {
        this.domainID = value;
    }

    /**
     * Recupera il valore della proprietà filter.
     * 
     * @return
     *     possible object is
     *     {@link SearchFilter }
     *     
     */
    public SearchFilter getFilter() {
        return filter;
    }

    /**
     * Imposta il valore della proprietà filter.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchFilter }
     *     
     */
    public void setFilter(SearchFilter value) {
        this.filter = value;
    }

    /**
     * Recupera il valore della proprietà groupID.
     * 
     */
    public long getGroupID() {
        return groupID;
    }

    /**
     * Imposta il valore della proprietà groupID.
     * 
     */
    public void setGroupID(long value) {
        this.groupID = value;
    }

    /**
     * Recupera il valore della proprietà matching.
     * 
     * @return
     *     possible object is
     *     {@link SearchMatching }
     *     
     */
    public SearchMatching getMatching() {
        return matching;
    }

    /**
     * Imposta il valore della proprietà matching.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchMatching }
     *     
     */
    public void setMatching(SearchMatching value) {
        this.matching = value;
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

    /**
     * Recupera il valore della proprietà scope.
     * 
     * @return
     *     possible object is
     *     {@link SearchScope }
     *     
     */
    public SearchScope getScope() {
        return scope;
    }

    /**
     * Imposta il valore della proprietà scope.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchScope }
     *     
     */
    public void setScope(SearchScope value) {
        this.scope = value;
    }

    /**
     * Recupera il valore della proprietà search.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearch() {
        return search;
    }

    /**
     * Imposta il valore della proprietà search.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearch(String value) {
        this.search = value;
    }

}
