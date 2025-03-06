
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SignatureFlags complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SignatureFlags"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="counterSignaturePath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="graphicalSignature" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ocsp" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="cosignCoordinates" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="timestampCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pdfSignatureLayout" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pdfSignatureCertificationLevel" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="cadesDetached" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureFlags", propOrder = {
    "counterSignaturePath",
    "graphicalSignature",
    "ocsp",
    "timestamp",
    "cosignCoordinates",
    "timestampCode",
    "pdfSignatureLayout",
    "password",
    "pdfSignatureCertificationLevel",
    "cadesDetached"
})
public class SignatureFlags {

    @XmlElement(required = true, nillable = true)
    protected String counterSignaturePath;
    protected boolean graphicalSignature;
    protected boolean ocsp;
    protected boolean timestamp;
    protected boolean cosignCoordinates;
    @XmlElement(required = true, nillable = true)
    protected String timestampCode;
    protected int pdfSignatureLayout;
    @XmlElement(required = true)
    protected String password;
    protected int pdfSignatureCertificationLevel;
    protected boolean cadesDetached;

    /**
     * Recupera il valore della proprietà counterSignaturePath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounterSignaturePath() {
        return counterSignaturePath;
    }

    /**
     * Imposta il valore della proprietà counterSignaturePath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounterSignaturePath(String value) {
        this.counterSignaturePath = value;
    }

    /**
     * Recupera il valore della proprietà graphicalSignature.
     * 
     */
    public boolean isGraphicalSignature() {
        return graphicalSignature;
    }

    /**
     * Imposta il valore della proprietà graphicalSignature.
     * 
     */
    public void setGraphicalSignature(boolean value) {
        this.graphicalSignature = value;
    }

    /**
     * Recupera il valore della proprietà ocsp.
     * 
     */
    public boolean isOcsp() {
        return ocsp;
    }

    /**
     * Imposta il valore della proprietà ocsp.
     * 
     */
    public void setOcsp(boolean value) {
        this.ocsp = value;
    }

    /**
     * Recupera il valore della proprietà timestamp.
     * 
     */
    public boolean isTimestamp() {
        return timestamp;
    }

    /**
     * Imposta il valore della proprietà timestamp.
     * 
     */
    public void setTimestamp(boolean value) {
        this.timestamp = value;
    }

    /**
     * Recupera il valore della proprietà cosignCoordinates.
     * 
     */
    public boolean isCosignCoordinates() {
        return cosignCoordinates;
    }

    /**
     * Imposta il valore della proprietà cosignCoordinates.
     * 
     */
    public void setCosignCoordinates(boolean value) {
        this.cosignCoordinates = value;
    }

    /**
     * Recupera il valore della proprietà timestampCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestampCode() {
        return timestampCode;
    }

    /**
     * Imposta il valore della proprietà timestampCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestampCode(String value) {
        this.timestampCode = value;
    }

    /**
     * Recupera il valore della proprietà pdfSignatureLayout.
     * 
     */
    public int getPdfSignatureLayout() {
        return pdfSignatureLayout;
    }

    /**
     * Imposta il valore della proprietà pdfSignatureLayout.
     * 
     */
    public void setPdfSignatureLayout(int value) {
        this.pdfSignatureLayout = value;
    }

    /**
     * Recupera il valore della proprietà password.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta il valore della proprietà password.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Recupera il valore della proprietà pdfSignatureCertificationLevel.
     * 
     */
    public int getPdfSignatureCertificationLevel() {
        return pdfSignatureCertificationLevel;
    }

    /**
     * Imposta il valore della proprietà pdfSignatureCertificationLevel.
     * 
     */
    public void setPdfSignatureCertificationLevel(int value) {
        this.pdfSignatureCertificationLevel = value;
    }

    /**
     * Recupera il valore della proprietà cadesDetached.
     * 
     */
    public boolean isCadesDetached() {
        return cadesDetached;
    }

    /**
     * Imposta il valore della proprietà cadesDetached.
     * 
     */
    public void setCadesDetached(boolean value) {
        this.cadesDetached = value;
    }

}
