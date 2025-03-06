
package it.eng.hsm.client.pkbox.envelope.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.hsm.client.pkbox.common.generated.SignerEx;


/**
 * <p>Classe Java per VerifyInfoEx complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VerifyInfoEx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="dataLen" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="envelopeCompliance" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="envelopeComplianceCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="envelopeComplianceInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signerCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="signers" type="{http://server.pkbox.it/xsd}SignerEx" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerifyInfoEx", namespace = "http://soap.remote.pkserver.it/xsd", propOrder = {
    "data",
    "dataLen",
    "envelopeCompliance",
    "envelopeComplianceCode",
    "envelopeComplianceInfo",
    "signerCount",
    "signers"
})
public class VerifyInfoEx {

    @XmlElement(required = true, nillable = true)
    protected byte[] data;
    protected long dataLen;
    protected int envelopeCompliance;
    protected int envelopeComplianceCode;
    @XmlElement(required = true, nillable = true)
    protected String envelopeComplianceInfo;
    protected int signerCount;
    @XmlElement(nillable = true)
    protected List<SignerEx> signers;

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
    public long getDataLen() {
        return dataLen;
    }

    /**
     * Imposta il valore della proprietà dataLen.
     * 
     */
    public void setDataLen(long value) {
        this.dataLen = value;
    }

    /**
     * Recupera il valore della proprietà envelopeCompliance.
     * 
     */
    public int getEnvelopeCompliance() {
        return envelopeCompliance;
    }

    /**
     * Imposta il valore della proprietà envelopeCompliance.
     * 
     */
    public void setEnvelopeCompliance(int value) {
        this.envelopeCompliance = value;
    }

    /**
     * Recupera il valore della proprietà envelopeComplianceCode.
     * 
     */
    public int getEnvelopeComplianceCode() {
        return envelopeComplianceCode;
    }

    /**
     * Imposta il valore della proprietà envelopeComplianceCode.
     * 
     */
    public void setEnvelopeComplianceCode(int value) {
        this.envelopeComplianceCode = value;
    }

    /**
     * Recupera il valore della proprietà envelopeComplianceInfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnvelopeComplianceInfo() {
        return envelopeComplianceInfo;
    }

    /**
     * Imposta il valore della proprietà envelopeComplianceInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnvelopeComplianceInfo(String value) {
        this.envelopeComplianceInfo = value;
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

    /**
     * Gets the value of the signers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSigners().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignerEx }
     * 
     * 
     */
    public List<SignerEx> getSigners() {
        if (signers == null) {
            signers = new ArrayList<SignerEx>();
        }
        return this.signers;
    }

}
