
package it.eng.hsm.client.pkbox.common.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ByteArray complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ByteArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="buffer" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ByteArray", namespace = "http://soap.remote.pkserver.it/xsd", propOrder = {
    "buffer"
})
public class ByteArray {

    @XmlElement(required = true, nillable = true)
    protected byte[] buffer;

    /**
     * Recupera il valore della proprietà buffer.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * Imposta il valore della proprietà buffer.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBuffer(byte[] value) {
        this.buffer = value;
    }

}
