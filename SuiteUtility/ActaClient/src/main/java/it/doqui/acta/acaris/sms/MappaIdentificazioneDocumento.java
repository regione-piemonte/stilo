
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per MappaIdentificazioneDocumento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MappaIdentificazioneDocumento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rappresentazioneLimitata" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MappaIdentificazioneDocumento", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "rappresentazioneLimitata"
})
@XmlSeeAlso({
    DocumentoArchivisticoIdMap.class,
    DocumentoFisicoIdMap.class,
    ContenutoFisicoIdMap.class
})
public abstract class MappaIdentificazioneDocumento {

    @XmlElement(namespace = "")
    protected boolean rappresentazioneLimitata;

    /**
     * Recupera il valore della proprietà rappresentazioneLimitata.
     * 
     */
    public boolean isRappresentazioneLimitata() {
        return rappresentazioneLimitata;
    }

    /**
     * Imposta il valore della proprietà rappresentazioneLimitata.
     * 
     */
    public void setRappresentazioneLimitata(boolean value) {
        this.rappresentazioneLimitata = value;
    }

}
