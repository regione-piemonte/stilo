
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DocumentoArchivisticoIdMap complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentoArchivisticoIdMap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{documentservice.acaris.acta.doqui.it}MappaIdentificazioneDocumento"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="documentoArchivisticoId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="collocazioni" type="{documentservice.acaris.acta.doqui.it}CollocazioneDocumento" maxOccurs="unbounded"/&gt;
 *         &lt;element name="documentiFisiciIdMap" type="{documentservice.acaris.acta.doqui.it}DocumentoFisicoIdMap" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoArchivisticoIdMap", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "documentoArchivisticoId",
    "collocazioni",
    "documentiFisiciIdMap"
})
public class DocumentoArchivisticoIdMap
    extends MappaIdentificazioneDocumento
{

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType documentoArchivisticoId;
    @XmlElement(namespace = "", required = true)
    protected List<CollocazioneDocumento> collocazioni;
    @XmlElement(namespace = "")
    protected List<DocumentoFisicoIdMap> documentiFisiciIdMap;

    /**
     * Recupera il valore della proprietà documentoArchivisticoId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getDocumentoArchivisticoId() {
        return documentoArchivisticoId;
    }

    /**
     * Imposta il valore della proprietà documentoArchivisticoId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setDocumentoArchivisticoId(ObjectIdType value) {
        this.documentoArchivisticoId = value;
    }

    /**
     * Gets the value of the collocazioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the collocazioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCollocazioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CollocazioneDocumento }
     * 
     * 
     */
    public List<CollocazioneDocumento> getCollocazioni() {
        if (collocazioni == null) {
            collocazioni = new ArrayList<CollocazioneDocumento>();
        }
        return this.collocazioni;
    }

    /**
     * Gets the value of the documentiFisiciIdMap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentiFisiciIdMap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentiFisiciIdMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentoFisicoIdMap }
     * 
     * 
     */
    public List<DocumentoFisicoIdMap> getDocumentiFisiciIdMap() {
        if (documentiFisiciIdMap == null) {
            documentiFisiciIdMap = new ArrayList<DocumentoFisicoIdMap>();
        }
        return this.documentiFisiciIdMap;
    }

}
