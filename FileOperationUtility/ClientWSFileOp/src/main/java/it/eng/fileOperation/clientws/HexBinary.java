/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per hexBinary complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="hexBinary"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;hexBinary"&gt;
 *       &lt;attribute ref="{http://www.w3.org/2005/05/xmlmime}contentType"/&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hexBinary", namespace = "http://www.w3.org/2005/05/xmlmime", propOrder = {
    "value"
})
public class HexBinary {

    @XmlValue
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] value;
    @XmlAttribute(name = "contentType", namespace = "http://www.w3.org/2005/05/xmlmime")
    protected String contentType;

    /**
     * Recupera il valore della proprietÓ value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietÓ value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(byte[] value) {
        this.value = value;
    }

    /**
     * Recupera il valore della proprietÓ contentType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Imposta il valore della proprietÓ contentType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

}
