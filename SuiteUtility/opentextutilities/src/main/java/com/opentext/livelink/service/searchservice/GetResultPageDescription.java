
package com.opentext.livelink.service.searchservice;

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
 *         &lt;element name="iso639LanguageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="queryLanguage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "iso639LanguageCode",
    "queryLanguage"
})
@XmlRootElement(name = "GetResultPageDescription")
public class GetResultPageDescription {

    protected String iso639LanguageCode;
    protected String queryLanguage;

    /**
     * Recupera il valore della proprietà iso639LanguageCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIso639LanguageCode() {
        return iso639LanguageCode;
    }

    /**
     * Imposta il valore della proprietà iso639LanguageCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIso639LanguageCode(String value) {
        this.iso639LanguageCode = value;
    }

    /**
     * Recupera il valore della proprietà queryLanguage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryLanguage() {
        return queryLanguage;
    }

    /**
     * Imposta il valore della proprietà queryLanguage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryLanguage(String value) {
        this.queryLanguage = value;
    }

}
