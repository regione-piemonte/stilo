
package it.eng.hsm.client.pkbox.envelope.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.hsm.client.pkbox.common.generated.Signer;


/**
 * <p>Classe Java per VerifyInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VerifyInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="dataLen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="invalidSignCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="signer" type="{http://server.pkbox.it/xsd}Signer" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="signerCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerifyInfo", namespace = "http://soap.remote.pkserver.it/xsd", propOrder = {
    "data",
    "dataLen",
    "invalidSignCount",
    "signer",
    "signerCount"
})
public class VerifyInfo {

    @XmlElement(required = true, nillable = true)
    protected byte[] data;
    protected int dataLen;
    protected int invalidSignCount;
    @XmlElement(nillable = true)
    protected List<Signer> signer;
    protected int signerCount;

    /**
     * Recupera il valore della proprietà data.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Imposta il valore della proprietà data.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setData(byte[] value) {
        this.data = value;
    }

    /**
     * Recupera il valore della proprietà dataLen.
     * 
     */
    public int getDataLen() {
        return dataLen;
    }

    /**
     * Imposta il valore della proprietà dataLen.
     * 
     */
    public void setDataLen(int value) {
        this.dataLen = value;
    }

    /**
     * Recupera il valore della proprietà invalidSignCount.
     * 
     */
    public int getInvalidSignCount() {
        return invalidSignCount;
    }

    /**
     * Imposta il valore della proprietà invalidSignCount.
     * 
     */
    public void setInvalidSignCount(int value) {
        this.invalidSignCount = value;
    }

    /**
     * Gets the value of the signer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSigner().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Signer }
     * 
     * 
     */
    public List<Signer> getSigner() {
        if (signer == null) {
            signer = new ArrayList<Signer>();
        }
        return this.signer;
    }

    /**
     * Recupera il valore della proprietà signerCount.
     * 
     */
    public int getSignerCount() {
        return signerCount;
    }

    /**
     * Imposta il valore della proprietà signerCount.
     * 
     */
    public void setSignerCount(int value) {
        this.signerCount = value;
    }

}
