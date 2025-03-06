
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ContenutoFisicoIdMap complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ContenutoFisicoIdMap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{documentservice.acaris.acta.doqui.it}MappaIdentificazioneDocumento"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="contenutoFisicoId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="documentoFisicoId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="contentStreamId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="tipoContenuto" type="{common.acaris.acta.doqui.it}enumStreamId"/&gt;
 *         &lt;element name="verifyReportList" type="{common.acaris.acta.doqui.it}ObjectIdType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContenutoFisicoIdMap", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "contenutoFisicoId",
    "documentoFisicoId",
    "contentStreamId",
    "tipoContenuto",
    "verifyReportList"
})
public class ContenutoFisicoIdMap
    extends MappaIdentificazioneDocumento
{

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType contenutoFisicoId;
    @XmlElement(namespace = "", required = true)
    protected ObjectIdType documentoFisicoId;
    @XmlElement(namespace = "", required = true)
    protected ObjectIdType contentStreamId;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "string")
    protected EnumStreamId tipoContenuto;
    @XmlElement(namespace = "", required = true)
    protected List<ObjectIdType> verifyReportList;

    /**
     * Recupera il valore della proprietà contenutoFisicoId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getContenutoFisicoId() {
        return contenutoFisicoId;
    }

    /**
     * Imposta il valore della proprietà contenutoFisicoId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setContenutoFisicoId(ObjectIdType value) {
        this.contenutoFisicoId = value;
    }

    /**
     * Recupera il valore della proprietà documentoFisicoId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getDocumentoFisicoId() {
        return documentoFisicoId;
    }

    /**
     * Imposta il valore della proprietà documentoFisicoId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setDocumentoFisicoId(ObjectIdType value) {
        this.documentoFisicoId = value;
    }

    /**
     * Recupera il valore della proprietà contentStreamId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getContentStreamId() {
        return contentStreamId;
    }

    /**
     * Imposta il valore della proprietà contentStreamId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setContentStreamId(ObjectIdType value) {
        this.contentStreamId = value;
    }

    /**
     * Recupera il valore della proprietà tipoContenuto.
     * 
     * @return
     *     possible object is
     *     {@link EnumStreamId }
     *     
     */
    public EnumStreamId getTipoContenuto() {
        return tipoContenuto;
    }

    /**
     * Imposta il valore della proprietà tipoContenuto.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumStreamId }
     *     
     */
    public void setTipoContenuto(EnumStreamId value) {
        this.tipoContenuto = value;
    }

    /**
     * Gets the value of the verifyReportList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verifyReportList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerifyReportList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectIdType }
     * 
     * 
     */
    public List<ObjectIdType> getVerifyReportList() {
        if (verifyReportList == null) {
            verifyReportList = new ArrayList<ObjectIdType>();
        }
        return this.verifyReportList;
    }

}
