
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ProfiloPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ProfiloPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idProfilo" type="{common.acaris.acta.doqui.it}IdProfiloType"/&gt;
 *         &lt;element name="codice" type="{common.acaris.acta.doqui.it}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfiloPropertiesType", namespace = "backoffice.acaris.acta.doqui.it", propOrder = {
    "idProfilo",
    "codice"
})
public class ProfiloPropertiesType {

    @XmlElement(required = true)
    protected IdProfiloType idProfilo;
    @XmlElement(required = true)
    protected String codice;

    /**
     * Recupera il valore della propriet� idProfilo.
     * 
     * @return
     *     possible object is
     *     {@link IdProfiloType }
     *     
     */
    public IdProfiloType getIdProfilo() {
        return idProfilo;
    }

    /**
     * Imposta il valore della propriet� idProfilo.
     * 
     * @param value
     *     allowed object is
     *     {@link IdProfiloType }
     *     
     */
    public void setIdProfilo(IdProfiloType value) {
        this.idProfilo = value;
    }

    /**
     * Recupera il valore della propriet� codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della propriet� codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

}
