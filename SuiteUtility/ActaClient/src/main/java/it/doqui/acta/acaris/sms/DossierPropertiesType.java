
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DossierPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DossierPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}AggregazionePropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="creazioneFascicoli" type="{archive.acaris.acta.doqui.it}CreazioneFascicoliType"/&gt;
 *         &lt;element name="riclassificazioneFascicoli" type="{archive.acaris.acta.doqui.it}RiclassificazioneFascicoliType"/&gt;
 *         &lt;element name="inserimentoDocumenti" type="{archive.acaris.acta.doqui.it}InserimentoDocumentiType"/&gt;
 *         &lt;element name="aggiuntaOriClassificazioneDocumenti" type="{archive.acaris.acta.doqui.it}AggiuntaOriClassificazioneDocumentiType"/&gt;
 *         &lt;element name="stato" type="{archive.acaris.acta.doqui.it}enumDossierStatoType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DossierPropertiesType", propOrder = {
    "creazioneFascicoli",
    "riclassificazioneFascicoli",
    "inserimentoDocumenti",
    "aggiuntaOriClassificazioneDocumenti",
    "stato"
})
public class DossierPropertiesType
    extends AggregazionePropertiesType
{

    protected boolean creazioneFascicoli;
    protected boolean riclassificazioneFascicoli;
    protected boolean inserimentoDocumenti;
    protected boolean aggiuntaOriClassificazioneDocumenti;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumDossierStatoType stato;

    /**
     * Recupera il valore della proprietà creazioneFascicoli.
     * 
     */
    public boolean isCreazioneFascicoli() {
        return creazioneFascicoli;
    }

    /**
     * Imposta il valore della proprietà creazioneFascicoli.
     * 
     */
    public void setCreazioneFascicoli(boolean value) {
        this.creazioneFascicoli = value;
    }

    /**
     * Recupera il valore della proprietà riclassificazioneFascicoli.
     * 
     */
    public boolean isRiclassificazioneFascicoli() {
        return riclassificazioneFascicoli;
    }

    /**
     * Imposta il valore della proprietà riclassificazioneFascicoli.
     * 
     */
    public void setRiclassificazioneFascicoli(boolean value) {
        this.riclassificazioneFascicoli = value;
    }

    /**
     * Recupera il valore della proprietà inserimentoDocumenti.
     * 
     */
    public boolean isInserimentoDocumenti() {
        return inserimentoDocumenti;
    }

    /**
     * Imposta il valore della proprietà inserimentoDocumenti.
     * 
     */
    public void setInserimentoDocumenti(boolean value) {
        this.inserimentoDocumenti = value;
    }

    /**
     * Recupera il valore della proprietà aggiuntaOriClassificazioneDocumenti.
     * 
     */
    public boolean isAggiuntaOriClassificazioneDocumenti() {
        return aggiuntaOriClassificazioneDocumenti;
    }

    /**
     * Imposta il valore della proprietà aggiuntaOriClassificazioneDocumenti.
     * 
     */
    public void setAggiuntaOriClassificazioneDocumenti(boolean value) {
        this.aggiuntaOriClassificazioneDocumenti = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link EnumDossierStatoType }
     *     
     */
    public EnumDossierStatoType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumDossierStatoType }
     *     
     */
    public void setStato(EnumDossierStatoType value) {
        this.stato = value;
    }

}
