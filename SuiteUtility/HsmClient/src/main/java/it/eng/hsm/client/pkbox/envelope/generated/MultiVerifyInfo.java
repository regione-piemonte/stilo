
package it.eng.hsm.client.pkbox.envelope.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per MultiVerifyInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MultiVerifyInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultiVerifyInfo", namespace = "http://server.pkbox.it/xsd", propOrder = {
    "resultSize"
})
public class MultiVerifyInfo {

    protected int resultSize;

    /**
     * Recupera il valore della proprietà resultSize.
     * 
     */
    public int getResultSize() {
        return resultSize;
    }

    /**
     * Imposta il valore della proprietà resultSize.
     * 
     */
    public void setResultSize(int value) {
        this.resultSize = value;
    }

}
