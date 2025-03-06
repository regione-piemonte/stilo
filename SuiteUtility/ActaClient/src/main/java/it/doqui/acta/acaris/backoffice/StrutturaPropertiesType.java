
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StrutturaPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="StrutturaPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{backoffice.acaris.acta.doqui.it}OrganizationUnitPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tipo" type="{backoffice.acaris.acta.doqui.it}enumTipoStrutturaType"/&gt;
 *         &lt;element name="livello" type="{backoffice.acaris.acta.doqui.it}LivelloType"/&gt;
 *         &lt;element name="descrizioneLivello" type="{backoffice.acaris.acta.doqui.it}DescrizioneLivelloType"/&gt;
 *         &lt;element name="idAOO" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrutturaPropertiesType", namespace = "backoffice.acaris.acta.doqui.it", propOrder = {
    "tipo",
    "livello",
    "descrizioneLivello",
    "idAOO"
})
public class StrutturaPropertiesType
    extends OrganizationUnitPropertiesType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipoStrutturaType tipo;
    protected int livello;
    @XmlElement(required = true)
    protected String descrizioneLivello;
    @XmlElement(required = true)
    protected ObjectIdType idAOO;

    /**
     * Recupera il valore della proprietà tipo.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipoStrutturaType }
     *     
     */
    public EnumTipoStrutturaType getTipo() {
        return tipo;
    }

    /**
     * Imposta il valore della proprietà tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipoStrutturaType }
     *     
     */
    public void setTipo(EnumTipoStrutturaType value) {
        this.tipo = value;
    }

    /**
     * Recupera il valore della proprietà livello.
     * 
     */
    public int getLivello() {
        return livello;
    }

    /**
     * Imposta il valore della proprietà livello.
     * 
     */
    public void setLivello(int value) {
        this.livello = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneLivello.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneLivello() {
        return descrizioneLivello;
    }

    /**
     * Imposta il valore della proprietà descrizioneLivello.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneLivello(String value) {
        this.descrizioneLivello = value;
    }

    /**
     * Recupera il valore della proprietà idAOO.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getIdAOO() {
        return idAOO;
    }

    /**
     * Imposta il valore della proprietà idAOO.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setIdAOO(ObjectIdType value) {
        this.idAOO = value;
    }

}
