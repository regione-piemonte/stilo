
package it.doqui.acta.acaris.navigation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SerieFascicoliPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SerieFascicoliPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}SeriePropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="obbligoFascStand" type="{archive.acaris.acta.doqui.it}ObbligoFascStandType"/&gt;
 *         &lt;element name="tipologiaNumerazione" type="{archive.acaris.acta.doqui.it}enumTipologiaNumerazioneType"/&gt;
 *         &lt;element name="idFascicoloStandard" type="{archive.acaris.acta.doqui.it}IdFascicoloStandardType"/&gt;
 *         &lt;element name="stato" type="{archive.acaris.acta.doqui.it}enumSerieFascicoliStatoType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SerieFascicoliPropertiesType", propOrder = {
    "obbligoFascStand",
    "tipologiaNumerazione",
    "idFascicoloStandard",
    "stato"
})
public class SerieFascicoliPropertiesType
    extends SeriePropertiesType
{

    protected boolean obbligoFascStand;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipologiaNumerazioneType tipologiaNumerazione;
    @XmlElement(required = true)
    protected IdFascicoloStandardType idFascicoloStandard;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumSerieFascicoliStatoType stato;

    /**
     * Recupera il valore della proprietà obbligoFascStand.
     * 
     */
    public boolean isObbligoFascStand() {
        return obbligoFascStand;
    }

    /**
     * Imposta il valore della proprietà obbligoFascStand.
     * 
     */
    public void setObbligoFascStand(boolean value) {
        this.obbligoFascStand = value;
    }

    /**
     * Recupera il valore della proprietà tipologiaNumerazione.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipologiaNumerazioneType }
     *     
     */
    public EnumTipologiaNumerazioneType getTipologiaNumerazione() {
        return tipologiaNumerazione;
    }

    /**
     * Imposta il valore della proprietà tipologiaNumerazione.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipologiaNumerazioneType }
     *     
     */
    public void setTipologiaNumerazione(EnumTipologiaNumerazioneType value) {
        this.tipologiaNumerazione = value;
    }

    /**
     * Recupera il valore della proprietà idFascicoloStandard.
     * 
     * @return
     *     possible object is
     *     {@link IdFascicoloStandardType }
     *     
     */
    public IdFascicoloStandardType getIdFascicoloStandard() {
        return idFascicoloStandard;
    }

    /**
     * Imposta il valore della proprietà idFascicoloStandard.
     * 
     * @param value
     *     allowed object is
     *     {@link IdFascicoloStandardType }
     *     
     */
    public void setIdFascicoloStandard(IdFascicoloStandardType value) {
        this.idFascicoloStandard = value;
    }

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link EnumSerieFascicoliStatoType }
     *     
     */
    public EnumSerieFascicoliStatoType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumSerieFascicoliStatoType }
     *     
     */
    public void setStato(EnumSerieFascicoliStatoType value) {
        this.stato = value;
    }

}
