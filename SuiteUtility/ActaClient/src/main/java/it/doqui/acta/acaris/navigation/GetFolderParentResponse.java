
package it.doqui.acta.acaris.navigation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="object" type="{common.acaris.acta.doqui.it}ObjectResponseType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "object"
})
@XmlRootElement(name = "getFolderParentResponse")
public class GetFolderParentResponse {

    protected ObjectResponseType object;

    /**
     * Recupera il valore della propriet� object.
     * 
     * @return
     *     possible object is
     *     {@link ObjectResponseType }
     *     
     */
    public ObjectResponseType getObject() {
        return object;
    }

    /**
     * Imposta il valore della propriet� object.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectResponseType }
     *     
     */
    public void setObject(ObjectResponseType value) {
        this.object = value;
    }

}
