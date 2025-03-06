
package com.opentext.livelink.service.documentmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="GetMetadataLanguagesResult" type="{urn:DocMan.service.livelink.opentext.com}MetadataLanguage" maxOccurs="unbounded" minOccurs="0"/>
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
    "getMetadataLanguagesResult"
})
@XmlRootElement(name = "GetMetadataLanguagesResponse")
public class GetMetadataLanguagesResponse {

    @XmlElement(name = "GetMetadataLanguagesResult", nillable = true)
    protected List<MetadataLanguage> getMetadataLanguagesResult;

    /**
     * Gets the value of the getMetadataLanguagesResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the getMetadataLanguagesResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGetMetadataLanguagesResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetadataLanguage }
     * 
     * 
     */
    public List<MetadataLanguage> getGetMetadataLanguagesResult() {
        if (getMetadataLanguagesResult == null) {
            getMetadataLanguagesResult = new ArrayList<MetadataLanguage>();
        }
        return this.getMetadataLanguagesResult;
    }

}
