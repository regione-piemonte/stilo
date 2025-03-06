
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ContenutoFisicoIRC complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ContenutoFisicoIRC"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{documentservice.acaris.acta.doqui.it}InfoRichiestaCreazione"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="documentoFisico" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="dataUltimoAggiornamentoDocumento" type="{common.acaris.acta.doqui.it}ChangeTokenType"/&gt;
 *         &lt;element name="propertiesContenutoFisico" type="{common.acaris.acta.doqui.it}PropertiesType"/&gt;
 *         &lt;element name="tipo" type="{common.acaris.acta.doqui.it}enumStreamId"/&gt;
 *         &lt;element name="stream" type="{common.acaris.acta.doqui.it}acarisContentStreamType"/&gt;
 *         &lt;element name="azioniVerificaFirma" type="{documentservice.acaris.acta.doqui.it}StepErrorAction" maxOccurs="7" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContenutoFisicoIRC", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "documentoFisico",
    "dataUltimoAggiornamentoDocumento",
    "propertiesContenutoFisico",
    "tipo",
    "stream",
    "azioniVerificaFirma"
})
public class ContenutoFisicoIRC
    extends InfoRichiestaCreazione
{

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType documentoFisico;
    @XmlElement(namespace = "", required = true)
    protected ChangeTokenType dataUltimoAggiornamentoDocumento;
    @XmlElement(namespace = "", required = true)
    protected PropertiesType propertiesContenutoFisico;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "string")
    protected EnumStreamId tipo;
    @XmlElement(namespace = "", required = true)
    protected AcarisContentStreamType stream;
    @XmlElement(namespace = "")
    protected List<StepErrorAction> azioniVerificaFirma;

    /**
     * Recupera il valore della propriet� documentoFisico.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getDocumentoFisico() {
        return documentoFisico;
    }

    /**
     * Imposta il valore della propriet� documentoFisico.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setDocumentoFisico(ObjectIdType value) {
        this.documentoFisico = value;
    }

    /**
     * Recupera il valore della propriet� dataUltimoAggiornamentoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link ChangeTokenType }
     *     
     */
    public ChangeTokenType getDataUltimoAggiornamentoDocumento() {
        return dataUltimoAggiornamentoDocumento;
    }

    /**
     * Imposta il valore della propriet� dataUltimoAggiornamentoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeTokenType }
     *     
     */
    public void setDataUltimoAggiornamentoDocumento(ChangeTokenType value) {
        this.dataUltimoAggiornamentoDocumento = value;
    }

    /**
     * Recupera il valore della propriet� propertiesContenutoFisico.
     * 
     * @return
     *     possible object is
     *     {@link PropertiesType }
     *     
     */
    public PropertiesType getPropertiesContenutoFisico() {
        return propertiesContenutoFisico;
    }

    /**
     * Imposta il valore della propriet� propertiesContenutoFisico.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertiesType }
     *     
     */
    public void setPropertiesContenutoFisico(PropertiesType value) {
        this.propertiesContenutoFisico = value;
    }

    /**
     * Recupera il valore della propriet� tipo.
     * 
     * @return
     *     possible object is
     *     {@link EnumStreamId }
     *     
     */
    public EnumStreamId getTipo() {
        return tipo;
    }

    /**
     * Imposta il valore della propriet� tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumStreamId }
     *     
     */
    public void setTipo(EnumStreamId value) {
        this.tipo = value;
    }

    /**
     * Recupera il valore della propriet� stream.
     * 
     * @return
     *     possible object is
     *     {@link AcarisContentStreamType }
     *     
     */
    public AcarisContentStreamType getStream() {
        return stream;
    }

    /**
     * Imposta il valore della propriet� stream.
     * 
     * @param value
     *     allowed object is
     *     {@link AcarisContentStreamType }
     *     
     */
    public void setStream(AcarisContentStreamType value) {
        this.stream = value;
    }

    /**
     * Gets the value of the azioniVerificaFirma property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the azioniVerificaFirma property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAzioniVerificaFirma().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StepErrorAction }
     * 
     * 
     */
    public List<StepErrorAction> getAzioniVerificaFirma() {
        if (azioniVerificaFirma == null) {
            azioniVerificaFirma = new ArrayList<StepErrorAction>();
        }
        return this.azioniVerificaFirma;
    }

}
