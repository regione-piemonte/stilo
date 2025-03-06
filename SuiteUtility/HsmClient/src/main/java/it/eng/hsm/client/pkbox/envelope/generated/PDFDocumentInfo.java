
package it.eng.hsm.client.pkbox.envelope.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PDFDocumentInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PDFDocumentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="blankSignatures" type="{http://server.pkbox.it/xsd}PDFSignatureInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="blankSignaturesCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="certificationLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="encrypted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pdfVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="permissions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="signatures" type="{http://server.pkbox.it/xsd}PDFSignatureInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="signaturesCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalPages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalRevisions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PDFDocumentInfo", namespace = "http://server.pkbox.it/xsd", propOrder = {
    "blankSignatures",
    "blankSignaturesCount",
    "certificationLevel",
    "encrypted",
    "pdfVersion",
    "permissions",
    "signatures",
    "signaturesCount",
    "totalPages",
    "totalRevisions"
})
public class PDFDocumentInfo {

    @XmlElement(nillable = true)
    protected List<PDFSignatureInfo> blankSignatures;
    protected int blankSignaturesCount;
    protected int certificationLevel;
    protected boolean encrypted;
    protected int pdfVersion;
    protected int permissions;
    @XmlElement(nillable = true)
    protected List<PDFSignatureInfo> signatures;
    protected int signaturesCount;
    protected int totalPages;
    protected int totalRevisions;

    /**
     * Gets the value of the blankSignatures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the blankSignatures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBlankSignatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PDFSignatureInfo }
     * 
     * 
     */
    public List<PDFSignatureInfo> getBlankSignatures() {
        if (blankSignatures == null) {
            blankSignatures = new ArrayList<PDFSignatureInfo>();
        }
        return this.blankSignatures;
    }

    /**
     * Recupera il valore della proprietà blankSignaturesCount.
     * 
     */
    public int getBlankSignaturesCount() {
        return blankSignaturesCount;
    }

    /**
     * Imposta il valore della proprietà blankSignaturesCount.
     * 
     */
    public void setBlankSignaturesCount(int value) {
        this.blankSignaturesCount = value;
    }

    /**
     * Recupera il valore della proprietà certificationLevel.
     * 
     */
    public int getCertificationLevel() {
        return certificationLevel;
    }

    /**
     * Imposta il valore della proprietà certificationLevel.
     * 
     */
    public void setCertificationLevel(int value) {
        this.certificationLevel = value;
    }

    /**
     * Recupera il valore della proprietà encrypted.
     * 
     */
    public boolean isEncrypted() {
        return encrypted;
    }

    /**
     * Imposta il valore della proprietà encrypted.
     * 
     */
    public void setEncrypted(boolean value) {
        this.encrypted = value;
    }

    /**
     * Recupera il valore della proprietà pdfVersion.
     * 
     */
    public int getPdfVersion() {
        return pdfVersion;
    }

    /**
     * Imposta il valore della proprietà pdfVersion.
     * 
     */
    public void setPdfVersion(int value) {
        this.pdfVersion = value;
    }

    /**
     * Recupera il valore della proprietà permissions.
     * 
     */
    public int getPermissions() {
        return permissions;
    }

    /**
     * Imposta il valore della proprietà permissions.
     * 
     */
    public void setPermissions(int value) {
        this.permissions = value;
    }

    /**
     * Gets the value of the signatures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signatures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PDFSignatureInfo }
     * 
     * 
     */
    public List<PDFSignatureInfo> getSignatures() {
        if (signatures == null) {
            signatures = new ArrayList<PDFSignatureInfo>();
        }
        return this.signatures;
    }

    /**
     * Recupera il valore della proprietà signaturesCount.
     * 
     */
    public int getSignaturesCount() {
        return signaturesCount;
    }

    /**
     * Imposta il valore della proprietà signaturesCount.
     * 
     */
    public void setSignaturesCount(int value) {
        this.signaturesCount = value;
    }

    /**
     * Recupera il valore della proprietà totalPages.
     * 
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Imposta il valore della proprietà totalPages.
     * 
     */
    public void setTotalPages(int value) {
        this.totalPages = value;
    }

    /**
     * Recupera il valore della proprietà totalRevisions.
     * 
     */
    public int getTotalRevisions() {
        return totalRevisions;
    }

    /**
     * Imposta il valore della proprietà totalRevisions.
     * 
     */
    public void setTotalRevisions(int value) {
        this.totalRevisions = value;
    }

}
