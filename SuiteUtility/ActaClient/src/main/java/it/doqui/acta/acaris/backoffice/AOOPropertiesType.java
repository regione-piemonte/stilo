
package it.doqui.acta.acaris.backoffice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AOOPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AOOPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{backoffice.acaris.acta.doqui.it}OrganizationUnitPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idRaggruppamentoAOO" type="{backoffice.acaris.acta.doqui.it}IdRaggruppamentoAOOType"/&gt;
 *         &lt;element name="idStrutturaList" type="{common.acaris.acta.doqui.it}ObjectIdType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="idUtenteResponsabile" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AOOPropertiesType", namespace = "backoffice.acaris.acta.doqui.it", propOrder = {
    "idRaggruppamentoAOO",
    "idStrutturaList",
    "idUtenteResponsabile"
})
public class AOOPropertiesType
    extends OrganizationUnitPropertiesType
{

    @XmlElement(required = true)
    protected String idRaggruppamentoAOO;
    protected List<ObjectIdType> idStrutturaList;
    @XmlElement(required = true)
    protected ObjectIdType idUtenteResponsabile;

    /**
     * Recupera il valore della propriet� idRaggruppamentoAOO.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRaggruppamentoAOO() {
        return idRaggruppamentoAOO;
    }

    /**
     * Imposta il valore della propriet� idRaggruppamentoAOO.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRaggruppamentoAOO(String value) {
        this.idRaggruppamentoAOO = value;
    }

    /**
     * Gets the value of the idStrutturaList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idStrutturaList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdStrutturaList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectIdType }
     * 
     * 
     */
    public List<ObjectIdType> getIdStrutturaList() {
        if (idStrutturaList == null) {
            idStrutturaList = new ArrayList<ObjectIdType>();
        }
        return this.idStrutturaList;
    }

    /**
     * Recupera il valore della propriet� idUtenteResponsabile.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getIdUtenteResponsabile() {
        return idUtenteResponsabile;
    }

    /**
     * Imposta il valore della propriet� idUtenteResponsabile.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setIdUtenteResponsabile(ObjectIdType value) {
        this.idUtenteResponsabile = value;
    }

}
