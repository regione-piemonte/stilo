
package it.doqui.acta.acaris.multifilling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DocumentCompositionPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentCompositionPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}RelationshipPropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bypassControlli" type="{archive.acaris.acta.doqui.it}BypassControlliType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentCompositionPropertiesType", propOrder = {
    "bypassControlli"
})
public class DocumentCompositionPropertiesType
    extends RelationshipPropertiesType
{

    protected boolean bypassControlli;

    /**
     * Recupera il valore della proprietà bypassControlli.
     * 
     */
    public boolean isBypassControlli() {
        return bypassControlli;
    }

    /**
     * Imposta il valore della proprietà bypassControlli.
     * 
     */
    public void setBypassControlli(boolean value) {
        this.bypassControlli = value;
    }

}
