
package it.doqui.acta.acaris.object;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per MoveDocumentPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MoveDocumentPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{common.acaris.acta.doqui.it}PropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="offlineMoveRequest" type="{common.acaris.acta.doqui.it}boolean"/&gt;
 *         &lt;element name="idSmistamentoType" type="{common.acaris.acta.doqui.it}IdSmistamentoType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MoveDocumentPropertiesType", propOrder = {
    "offlineMoveRequest",
    "idSmistamentoType"
})
public class MoveDocumentPropertiesType
    extends PropertiesType
{

    protected boolean offlineMoveRequest;
    protected IdSmistamentoType idSmistamentoType;

    /**
     * Recupera il valore della propriet� offlineMoveRequest.
     * 
     */
    public boolean isOfflineMoveRequest() {
        return offlineMoveRequest;
    }

    /**
     * Imposta il valore della propriet� offlineMoveRequest.
     * 
     */
    public void setOfflineMoveRequest(boolean value) {
        this.offlineMoveRequest = value;
    }

    /**
     * Recupera il valore della propriet� idSmistamentoType.
     * 
     * @return
     *     possible object is
     *     {@link IdSmistamentoType }
     *     
     */
    public IdSmistamentoType getIdSmistamentoType() {
        return idSmistamentoType;
    }

    /**
     * Imposta il valore della propriet� idSmistamentoType.
     * 
     * @param value
     *     allowed object is
     *     {@link IdSmistamentoType }
     *     
     */
    public void setIdSmistamentoType(IdSmistamentoType value) {
        this.idSmistamentoType = value;
    }

}
