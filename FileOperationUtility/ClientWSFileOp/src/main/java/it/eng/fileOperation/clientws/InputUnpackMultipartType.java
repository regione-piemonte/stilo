/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InputUnpackMultipartType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputUnpackMultipartType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="recursive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="abilitaSbustamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="unpackPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputUnpackMultipartType", propOrder = {
    "recursive",
    "abilitaSbustamento",
    "unpackPath"
})
public class InputUnpackMultipartType
    extends AbstractInputOperationType
{

    @XmlElement(defaultValue = "true")
    protected Boolean recursive;
    @XmlElement(defaultValue = "true")
    protected Boolean abilitaSbustamento;
    protected String unpackPath;

    /**
     * Recupera il valore della proprietà recursive.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRecursive() {
        return recursive;
    }

    /**
     * Imposta il valore della proprietà recursive.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRecursive(Boolean value) {
        this.recursive = value;
    }

    /**
     * Recupera il valore della proprietà abilitaSbustamento.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAbilitaSbustamento() {
        return abilitaSbustamento;
    }

    /**
     * Imposta il valore della proprietà abilitaSbustamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAbilitaSbustamento(Boolean value) {
        this.abilitaSbustamento = value;
    }

    /**
     * Recupera il valore della proprietà unpackPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnpackPath() {
        return unpackPath;
    }

    /**
     * Imposta il valore della proprietà unpackPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnpackPath(String value) {
        this.unpackPath = value;
    }

}
