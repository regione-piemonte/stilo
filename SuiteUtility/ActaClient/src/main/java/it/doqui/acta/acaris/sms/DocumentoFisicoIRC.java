
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DocumentoFisicoIRC complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentoFisicoIRC"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{documentservice.acaris.acta.doqui.it}InfoRichiestaCreazione"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="documentoArchivistico" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="documentRootFolderId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="propertiesDocumentoFisico" type="{common.acaris.acta.doqui.it}PropertiesType"/&gt;
 *         &lt;element name="contenutiFisici" type="{documentservice.acaris.acta.doqui.it}ContenutoFisicoIRC" maxOccurs="unbounded"/&gt;
 *         &lt;element name="azioniVerificaFirma" type="{documentservice.acaris.acta.doqui.it}StepErrorAction" maxOccurs="7" minOccurs="7"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoFisicoIRC", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "documentoArchivistico",
    "documentRootFolderId",
    "propertiesDocumentoFisico",
    "contenutiFisici",
    "azioniVerificaFirma"
})
public class DocumentoFisicoIRC
    extends InfoRichiestaCreazione
{

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType documentoArchivistico;
    @XmlElement(namespace = "", required = true)
    protected ObjectIdType documentRootFolderId;
    @XmlElement(namespace = "", required = true)
    protected PropertiesType propertiesDocumentoFisico;
    @XmlElement(namespace = "", required = true)
    protected List<ContenutoFisicoIRC> contenutiFisici;
    @XmlElement(namespace = "", required = true)
    protected List<StepErrorAction> azioniVerificaFirma;

    /**
     * Recupera il valore della proprietà documentoArchivistico.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getDocumentoArchivistico() {
        return documentoArchivistico;
    }

    /**
     * Imposta il valore della proprietà documentoArchivistico.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setDocumentoArchivistico(ObjectIdType value) {
        this.documentoArchivistico = value;
    }

    /**
     * Recupera il valore della proprietà documentRootFolderId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getDocumentRootFolderId() {
        return documentRootFolderId;
    }

    /**
     * Imposta il valore della proprietà documentRootFolderId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setDocumentRootFolderId(ObjectIdType value) {
        this.documentRootFolderId = value;
    }

    /**
     * Recupera il valore della proprietà propertiesDocumentoFisico.
     * 
     * @return
     *     possible object is
     *     {@link PropertiesType }
     *     
     */
    public PropertiesType getPropertiesDocumentoFisico() {
        return propertiesDocumentoFisico;
    }

    /**
     * Imposta il valore della proprietà propertiesDocumentoFisico.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertiesType }
     *     
     */
    public void setPropertiesDocumentoFisico(PropertiesType value) {
        this.propertiesDocumentoFisico = value;
    }

    /**
     * Gets the value of the contenutiFisici property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contenutiFisici property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContenutiFisici().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContenutoFisicoIRC }
     * 
     * 
     */
    public List<ContenutoFisicoIRC> getContenutiFisici() {
        if (contenutiFisici == null) {
            contenutiFisici = new ArrayList<ContenutoFisicoIRC>();
        }
        return this.contenutiFisici;
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
