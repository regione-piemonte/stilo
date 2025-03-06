
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdentificatoreDocumento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdentificatoreDocumento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="objectIdDocumento" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="objectIdClassificazione" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="tipoDocumento" type="{documentservice.acaris.acta.doqui.it}enumTipoDocumentoCreazione"/&gt;
 *         &lt;element name="dataUltimoAggiornamento" type="{common.acaris.acta.doqui.it}ChangeTokenType"/&gt;
 *         &lt;element name="failedStepsInfo" type="{documentservice.acaris.acta.doqui.it}FailedStepsInfo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificatoreDocumento", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "objectIdDocumento",
    "objectIdClassificazione",
    "tipoDocumento",
    "dataUltimoAggiornamento",
    "failedStepsInfo"
})
public class IdentificatoreDocumento {

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType objectIdDocumento;
    @XmlElement(namespace = "", required = true)
    protected ObjectIdType objectIdClassificazione;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipoDocumentoCreazione tipoDocumento;
    @XmlElement(namespace = "", required = true)
    protected ChangeTokenType dataUltimoAggiornamento;
    @XmlElement(namespace = "")
    protected List<FailedStepsInfo> failedStepsInfo;

    /**
     * Recupera il valore della propriet� objectIdDocumento.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getObjectIdDocumento() {
        return objectIdDocumento;
    }

    /**
     * Imposta il valore della propriet� objectIdDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setObjectIdDocumento(ObjectIdType value) {
        this.objectIdDocumento = value;
    }

    /**
     * Recupera il valore della propriet� objectIdClassificazione.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getObjectIdClassificazione() {
        return objectIdClassificazione;
    }

    /**
     * Imposta il valore della propriet� objectIdClassificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setObjectIdClassificazione(ObjectIdType value) {
        this.objectIdClassificazione = value;
    }

    /**
     * Recupera il valore della propriet� tipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipoDocumentoCreazione }
     *     
     */
    public EnumTipoDocumentoCreazione getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Imposta il valore della propriet� tipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipoDocumentoCreazione }
     *     
     */
    public void setTipoDocumento(EnumTipoDocumentoCreazione value) {
        this.tipoDocumento = value;
    }

    /**
     * Recupera il valore della propriet� dataUltimoAggiornamento.
     * 
     * @return
     *     possible object is
     *     {@link ChangeTokenType }
     *     
     */
    public ChangeTokenType getDataUltimoAggiornamento() {
        return dataUltimoAggiornamento;
    }

    /**
     * Imposta il valore della propriet� dataUltimoAggiornamento.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeTokenType }
     *     
     */
    public void setDataUltimoAggiornamento(ChangeTokenType value) {
        this.dataUltimoAggiornamento = value;
    }

    /**
     * Gets the value of the failedStepsInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the failedStepsInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFailedStepsInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FailedStepsInfo }
     * 
     * 
     */
    public List<FailedStepsInfo> getFailedStepsInfo() {
        if (failedStepsInfo == null) {
            failedStepsInfo = new ArrayList<FailedStepsInfo>();
        }
        return this.failedStepsInfo;
    }

}
