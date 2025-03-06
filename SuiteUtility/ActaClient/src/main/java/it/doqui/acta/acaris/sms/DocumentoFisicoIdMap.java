
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DocumentoFisicoIdMap complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentoFisicoIdMap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{documentservice.acaris.acta.doqui.it}MappaIdentificazioneDocumento"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="documentoFisicoId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="documentoArchivisticoId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="ContenutiFisiciIdMap" type="{documentservice.acaris.acta.doqui.it}ContenutoFisicoIdMap" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoFisicoIdMap", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "documentoFisicoId",
    "documentoArchivisticoId",
    "contenutiFisiciIdMap"
})
public class DocumentoFisicoIdMap
    extends MappaIdentificazioneDocumento
{

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType documentoFisicoId;
    @XmlElement(namespace = "", required = true)
    protected ObjectIdType documentoArchivisticoId;
    @XmlElement(name = "ContenutiFisiciIdMap", namespace = "", required = true)
    protected List<ContenutoFisicoIdMap> contenutiFisiciIdMap;

    /**
     * Recupera il valore della propriet� documentoFisicoId.
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
     * Imposta il valore della propriet� documentoFisicoId.
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
     * Recupera il valore della propriet� documentoArchivisticoId.
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
     * Imposta il valore della propriet� documentoArchivisticoId.
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
     * Gets the value of the contenutiFisiciIdMap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contenutiFisiciIdMap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContenutiFisiciIdMap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContenutoFisicoIdMap }
     * 
     * 
     */
    public List<ContenutoFisicoIdMap> getContenutiFisiciIdMap() {
        if (contenutiFisiciIdMap == null) {
            contenutiFisiciIdMap = new ArrayList<ContenutoFisicoIdMap>();
        }
        return this.contenutiFisiciIdMap;
    }

}
