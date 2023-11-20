/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ResponseUnpackMultipartType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponseUnpackMultipartType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="multipartMimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="multipartContents"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="multipartContent" type="{it.eng.fileoperation.ws}MultipartContentType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseUnpackMultipartType", propOrder = {
    "multipartMimeType",
    "multipartContents"
})
public class ResponseUnpackMultipartType
    extends AbstractResponseOperationType
{

    protected String multipartMimeType;
    @XmlElement(required = true)
    protected ResponseUnpackMultipartType.MultipartContents multipartContents;

    /**
     * Recupera il valore della proprietà multipartMimeType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMultipartMimeType() {
        return multipartMimeType;
    }

    /**
     * Imposta il valore della proprietà multipartMimeType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMultipartMimeType(String value) {
        this.multipartMimeType = value;
    }

    /**
     * Recupera il valore della proprietà multipartContents.
     * 
     * @return
     *     possible object is
     *     {@link ResponseUnpackMultipartType.MultipartContents }
     *     
     */
    public ResponseUnpackMultipartType.MultipartContents getMultipartContents() {
        return multipartContents;
    }

    /**
     * Imposta il valore della proprietà multipartContents.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseUnpackMultipartType.MultipartContents }
     *     
     */
    public void setMultipartContents(ResponseUnpackMultipartType.MultipartContents value) {
        this.multipartContents = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="multipartContent" type="{it.eng.fileoperation.ws}MultipartContentType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "multipartContent"
    })
    public static class MultipartContents {

        @XmlElement(nillable = true)
        protected List<MultipartContentType> multipartContent;

        /**
         * Gets the value of the multipartContent property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the multipartContent property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMultipartContent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultipartContentType }
         * 
         * 
         */
        public List<MultipartContentType> getMultipartContent() {
            if (multipartContent == null) {
                multipartContent = new ArrayList<MultipartContentType>();
            }
            return this.multipartContent;
        }

    }

}
