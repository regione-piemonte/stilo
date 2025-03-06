
package it.doqui.acta.acaris.repository;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per HistoryModificheTecnichePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="HistoryModificheTecnichePropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}RelationshipPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataTrasformazione" type="{archive.acaris.acta.doqui.it}DataTrasformazioneType"/&gt;
 *         &lt;element name="motivazioneTrasformazione" type="{archive.acaris.acta.doqui.it}enumMotivazioneTrasformazioneType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HistoryModificheTecnichePropertiesType", propOrder = {
    "dataTrasformazione",
    "motivazioneTrasformazione"
})
public class HistoryModificheTecnichePropertiesType
    extends RelationshipPropertiesType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataTrasformazione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumMotivazioneTrasformazioneType motivazioneTrasformazione;

    /**
     * Recupera il valore della proprietà dataTrasformazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataTrasformazione() {
        return dataTrasformazione;
    }

    /**
     * Imposta il valore della proprietà dataTrasformazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataTrasformazione(XMLGregorianCalendar value) {
        this.dataTrasformazione = value;
    }

    /**
     * Recupera il valore della proprietà motivazioneTrasformazione.
     * 
     * @return
     *     possible object is
     *     {@link EnumMotivazioneTrasformazioneType }
     *     
     */
    public EnumMotivazioneTrasformazioneType getMotivazioneTrasformazione() {
        return motivazioneTrasformazione;
    }

    /**
     * Imposta il valore della proprietà motivazioneTrasformazione.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumMotivazioneTrasformazioneType }
     *     
     */
    public void setMotivazioneTrasformazione(EnumMotivazioneTrasformazioneType value) {
        this.motivazioneTrasformazione = value;
    }

}
