
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InfoRichiestaTrasformazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InfoRichiestaTrasformazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tipoDocFisicoId" type="{common.acaris.acta.doqui.it}integer"/&gt;
 *         &lt;element name="composizioneId" type="{common.acaris.acta.doqui.it}integer"/&gt;
 *         &lt;element name="multiplo" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="diventaElettronico" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="statoDiEfficaciaId" type="{common.acaris.acta.doqui.it}integer"/&gt;
 *         &lt;element name="rimandareOperazioneSbustamento" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoRichiestaTrasformazione", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "tipoDocFisicoId",
    "composizioneId",
    "multiplo",
    "diventaElettronico",
    "statoDiEfficaciaId",
    "rimandareOperazioneSbustamento"
})
public class InfoRichiestaTrasformazione {

    @XmlElement(namespace = "")
    protected int tipoDocFisicoId;
    @XmlElement(namespace = "")
    protected int composizioneId;
    @XmlElement(namespace = "")
    protected boolean multiplo;
    @XmlElement(namespace = "")
    protected boolean diventaElettronico;
    @XmlElement(namespace = "")
    protected int statoDiEfficaciaId;
    @XmlElement(namespace = "")
    protected boolean rimandareOperazioneSbustamento;

    /**
     * Recupera il valore della proprietà tipoDocFisicoId.
     * 
     */
    public int getTipoDocFisicoId() {
        return tipoDocFisicoId;
    }

    /**
     * Imposta il valore della proprietà tipoDocFisicoId.
     * 
     */
    public void setTipoDocFisicoId(int value) {
        this.tipoDocFisicoId = value;
    }

    /**
     * Recupera il valore della proprietà composizioneId.
     * 
     */
    public int getComposizioneId() {
        return composizioneId;
    }

    /**
     * Imposta il valore della proprietà composizioneId.
     * 
     */
    public void setComposizioneId(int value) {
        this.composizioneId = value;
    }

    /**
     * Recupera il valore della proprietà multiplo.
     * 
     */
    public boolean isMultiplo() {
        return multiplo;
    }

    /**
     * Imposta il valore della proprietà multiplo.
     * 
     */
    public void setMultiplo(boolean value) {
        this.multiplo = value;
    }

    /**
     * Recupera il valore della proprietà diventaElettronico.
     * 
     */
    public boolean isDiventaElettronico() {
        return diventaElettronico;
    }

    /**
     * Imposta il valore della proprietà diventaElettronico.
     * 
     */
    public void setDiventaElettronico(boolean value) {
        this.diventaElettronico = value;
    }

    /**
     * Recupera il valore della proprietà statoDiEfficaciaId.
     * 
     */
    public int getStatoDiEfficaciaId() {
        return statoDiEfficaciaId;
    }

    /**
     * Imposta il valore della proprietà statoDiEfficaciaId.
     * 
     */
    public void setStatoDiEfficaciaId(int value) {
        this.statoDiEfficaciaId = value;
    }

    /**
     * Recupera il valore della proprietà rimandareOperazioneSbustamento.
     * 
     */
    public boolean isRimandareOperazioneSbustamento() {
        return rimandareOperazioneSbustamento;
    }

    /**
     * Imposta il valore della proprietà rimandareOperazioneSbustamento.
     * 
     */
    public void setRimandareOperazioneSbustamento(boolean value) {
        this.rimandareOperazioneSbustamento = value;
    }

}
