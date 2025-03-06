
package it.doqui.acta.acaris.navigation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per FascicoloRealeLegislaturaPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FascicoloRealeLegislaturaPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}FascicoloRealePropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="legislatura" type="{archive.acaris.acta.doqui.it}LegislaturaType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FascicoloRealeLegislaturaPropertiesType", propOrder = {
    "legislatura"
})
public class FascicoloRealeLegislaturaPropertiesType
    extends FascicoloRealePropertiesType
{

    @XmlElement(required = true)
    protected String legislatura;

    /**
     * Recupera il valore della proprietà legislatura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegislatura() {
        return legislatura;
    }

    /**
     * Imposta il valore della proprietà legislatura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegislatura(String value) {
        this.legislatura = value;
    }

}
