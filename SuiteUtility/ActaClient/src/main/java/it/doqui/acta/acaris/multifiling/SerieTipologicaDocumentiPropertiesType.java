
package it.doqui.acta.acaris.multifiling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SerieTipologicaDocumentiPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SerieTipologicaDocumentiPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}SeriePropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="modalitaCalcoloProgDoc" type="{archive.acaris.acta.doqui.it}enumModalitaCalcoloProgDocType"/&gt;
 *         &lt;element name="parteFissa" type="{archive.acaris.acta.doqui.it}ParteFissaType"/&gt;
 *         &lt;element name="registri" type="{archive.acaris.acta.doqui.it}RegistriType"/&gt;
 *         &lt;element name="docAltraClassificazione" type="{archive.acaris.acta.doqui.it}DocAltraClassificazioneType"/&gt;
 *         &lt;element name="stato" type="{archive.acaris.acta.doqui.it}enumSerieTipologicaDocumentiStatoType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SerieTipologicaDocumentiPropertiesType", propOrder = {
    "modalitaCalcoloProgDoc",
    "parteFissa",
    "registri",
    "docAltraClassificazione",
    "stato"
})
public class SerieTipologicaDocumentiPropertiesType
    extends SeriePropertiesType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumModalitaCalcoloProgDocType modalitaCalcoloProgDoc;
    @XmlElement(required = true)
    protected String parteFissa;
    protected boolean registri;
    protected boolean docAltraClassificazione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumSerieTipologicaDocumentiStatoType stato;

    /**
     * Recupera il valore della proprietÓ modalitaCalcoloProgDoc.
     * 
     * @return
     *     possible object is
     *     {@link EnumModalitaCalcoloProgDocType }
     *     
     */
    public EnumModalitaCalcoloProgDocType getModalitaCalcoloProgDoc() {
        return modalitaCalcoloProgDoc;
    }

    /**
     * Imposta il valore della proprietÓ modalitaCalcoloProgDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumModalitaCalcoloProgDocType }
     *     
     */
    public void setModalitaCalcoloProgDoc(EnumModalitaCalcoloProgDocType value) {
        this.modalitaCalcoloProgDoc = value;
    }

    /**
     * Recupera il valore della proprietÓ parteFissa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParteFissa() {
        return parteFissa;
    }

    /**
     * Imposta il valore della proprietÓ parteFissa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParteFissa(String value) {
        this.parteFissa = value;
    }

    /**
     * Recupera il valore della proprietÓ registri.
     * 
     */
    public boolean isRegistri() {
        return registri;
    }

    /**
     * Imposta il valore della proprietÓ registri.
     * 
     */
    public void setRegistri(boolean value) {
        this.registri = value;
    }

    /**
     * Recupera il valore della proprietÓ docAltraClassificazione.
     * 
     */
    public boolean isDocAltraClassificazione() {
        return docAltraClassificazione;
    }

    /**
     * Imposta il valore della proprietÓ docAltraClassificazione.
     * 
     */
    public void setDocAltraClassificazione(boolean value) {
        this.docAltraClassificazione = value;
    }

    /**
     * Recupera il valore della proprietÓ stato.
     * 
     * @return
     *     possible object is
     *     {@link EnumSerieTipologicaDocumentiStatoType }
     *     
     */
    public EnumSerieTipologicaDocumentiStatoType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietÓ stato.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumSerieTipologicaDocumentiStatoType }
     *     
     */
    public void setStato(EnumSerieTipologicaDocumentiStatoType value) {
        this.stato = value;
    }

}
