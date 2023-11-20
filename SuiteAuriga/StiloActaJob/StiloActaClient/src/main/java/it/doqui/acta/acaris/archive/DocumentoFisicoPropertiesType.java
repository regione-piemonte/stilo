/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per DocumentoFisicoPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentoFisicoPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}FolderPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="descrizione" type="{archive.acaris.acta.doqui.it}DescrizioneType"/&gt;
 *         &lt;element name="progressivoPerDocumento" type="{archive.acaris.acta.doqui.it}ProgressivoPerDocumentoType"/&gt;
 *         &lt;element name="dataMemorizzazione" type="{archive.acaris.acta.doqui.it}DataMemorizzazioneType"/&gt;
 *         &lt;element name="docMimeTypes" type="{archive.acaris.acta.doqui.it}DocMimeTypesXMLType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoFisicoPropertiesType", propOrder = {
    "descrizione",
    "progressivoPerDocumento",
    "dataMemorizzazione",
    "docMimeTypes"
})
public class DocumentoFisicoPropertiesType
    extends FolderPropertiesType
{

    @XmlElement(required = true)
    protected String descrizione;
    protected int progressivoPerDocumento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataMemorizzazione;
    @XmlElement(required = true)
    protected String docMimeTypes;

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà progressivoPerDocumento.
     * 
     */
    public int getProgressivoPerDocumento() {
        return progressivoPerDocumento;
    }

    /**
     * Imposta il valore della proprietà progressivoPerDocumento.
     * 
     */
    public void setProgressivoPerDocumento(int value) {
        this.progressivoPerDocumento = value;
    }

    /**
     * Recupera il valore della proprietà dataMemorizzazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataMemorizzazione() {
        return dataMemorizzazione;
    }

    /**
     * Imposta il valore della proprietà dataMemorizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataMemorizzazione(XMLGregorianCalendar value) {
        this.dataMemorizzazione = value;
    }

    /**
     * Recupera il valore della proprietà docMimeTypes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocMimeTypes() {
        return docMimeTypes;
    }

    /**
     * Imposta il valore della proprietà docMimeTypes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocMimeTypes(String value) {
        this.docMimeTypes = value;
    }

}
