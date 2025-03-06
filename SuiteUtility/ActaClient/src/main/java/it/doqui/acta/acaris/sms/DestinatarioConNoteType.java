
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per DestinatarioConNoteType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DestinatarioConNoteType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{sms.acaris.acta.doqui.it}DestinatarioType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="note" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="dataAggiornamento" type="{sms.acaris.acta.doqui.it}DataAggiornamentoType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DestinatarioConNoteType", namespace = "sms.acaris.acta.doqui.it", propOrder = {
    "note",
    "dataAggiornamento"
})
public class DestinatarioConNoteType
    extends DestinatarioType
{

    @XmlElement(namespace = "", required = true)
    protected String note;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAggiornamento;

    /**
     * Recupera il valore della proprietà note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della proprietà note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Recupera il valore della proprietà dataAggiornamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAggiornamento() {
        return dataAggiornamento;
    }

    /**
     * Imposta il valore della proprietà dataAggiornamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAggiornamento(XMLGregorianCalendar value) {
        this.dataAggiornamento = value;
    }

}
