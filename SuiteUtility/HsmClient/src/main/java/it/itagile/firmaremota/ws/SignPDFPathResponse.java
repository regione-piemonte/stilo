
package it.itagile.firmaremota.ws;

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
 *         &lt;element name="signPDFPathReturn" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "signPDFPathReturn"
})
@XmlRootElement(name = "signPDFPathResponse")
public class SignPDFPathResponse {

    protected int signPDFPathReturn;

    /**
     * Recupera il valore della proprietà signPDFPathReturn.
     * 
     */
    public int getSignPDFPathReturn() {
        return signPDFPathReturn;
    }

    /**
     * Imposta il valore della proprietà signPDFPathReturn.
     * 
     */
    public void setSignPDFPathReturn(int value) {
        this.signPDFPathReturn = value;
    }

}
