
package it.itagile.firmaremota.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="cred" type="{http://ws.firmaremota.itagile.it}RemoteSignatureCredentials"/&gt;
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="contentInAttachments" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="digestType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="X509certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="flags" type="{http://ws.firmaremota.itagile.it}SignatureFlags"/&gt;
 *         &lt;element name="fieldName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="x" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="y" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dateFormat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fontSize" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "cred",
    "content",
    "contentInAttachments",
    "digestType",
    "x509Certificate",
    "flags",
    "fieldName",
    "page",
    "x",
    "y",
    "width",
    "height",
    "userName",
    "reason",
    "location",
    "dateFormat",
    "text",
    "fontSize"
})
@XmlRootElement(name = "signPDF")
public class SignPDF {

    @XmlElement(required = true)
    protected RemoteSignatureCredentials cred;
    @XmlElement(required = true)
    protected byte[] content;
    protected boolean contentInAttachments;
    @XmlElement(required = true)
    protected String digestType;
    @XmlElement(name = "X509certificate", required = true)
    protected byte[] x509Certificate;
    @XmlElement(required = true)
    protected SignatureFlags flags;
    @XmlElement(required = true)
    protected String fieldName;
    protected int page;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    @XmlElement(required = true)
    protected String userName;
    @XmlElement(required = true)
    protected String reason;
    @XmlElement(required = true)
    protected String location;
    @XmlElement(required = true)
    protected String dateFormat;
    @XmlElement(required = true)
    protected String text;
    protected int fontSize;

    /**
     * Recupera il valore della proprietà cred.
     * 
     * @return
     *     possible object is
     *     {@link RemoteSignatureCredentials }
     *     
     */
    public RemoteSignatureCredentials getCred() {
        return cred;
    }

    /**
     * Imposta il valore della proprietà cred.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteSignatureCredentials }
     *     
     */
    public void setCred(RemoteSignatureCredentials value) {
        this.cred = value;
    }

    /**
     * Recupera il valore della proprietà content.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Imposta il valore della proprietà content.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContent(byte[] value) {
        this.content = value;
    }

    /**
     * Recupera il valore della proprietà contentInAttachments.
     * 
     */
    public boolean isContentInAttachments() {
        return contentInAttachments;
    }

    /**
     * Imposta il valore della proprietà contentInAttachments.
     * 
     */
    public void setContentInAttachments(boolean value) {
        this.contentInAttachments = value;
    }

    /**
     * Recupera il valore della proprietà digestType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigestType() {
        return digestType;
    }

    /**
     * Imposta il valore della proprietà digestType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigestType(String value) {
        this.digestType = value;
    }

    /**
     * Recupera il valore della proprietà x509Certificate.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getX509Certificate() {
        return x509Certificate;
    }

    /**
     * Imposta il valore della proprietà x509Certificate.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setX509Certificate(byte[] value) {
        this.x509Certificate = value;
    }

    /**
     * Recupera il valore della proprietà flags.
     * 
     * @return
     *     possible object is
     *     {@link SignatureFlags }
     *     
     */
    public SignatureFlags getFlags() {
        return flags;
    }

    /**
     * Imposta il valore della proprietà flags.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureFlags }
     *     
     */
    public void setFlags(SignatureFlags value) {
        this.flags = value;
    }

    /**
     * Recupera il valore della proprietà fieldName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Imposta il valore della proprietà fieldName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Recupera il valore della proprietà page.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Imposta il valore della proprietà page.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Recupera il valore della proprietà x.
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * Imposta il valore della proprietà x.
     * 
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * Recupera il valore della proprietà y.
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * Imposta il valore della proprietà y.
     * 
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * Recupera il valore della proprietà width.
     * 
     */
    public int getWidth() {
        return width;
    }

    /**
     * Imposta il valore della proprietà width.
     * 
     */
    public void setWidth(int value) {
        this.width = value;
    }

    /**
     * Recupera il valore della proprietà height.
     * 
     */
    public int getHeight() {
        return height;
    }

    /**
     * Imposta il valore della proprietà height.
     * 
     */
    public void setHeight(int value) {
        this.height = value;
    }

    /**
     * Recupera il valore della proprietà userName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Imposta il valore della proprietà userName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Recupera il valore della proprietà reason.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Imposta il valore della proprietà reason.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Recupera il valore della proprietà location.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Imposta il valore della proprietà location.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Recupera il valore della proprietà dateFormat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * Imposta il valore della proprietà dateFormat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateFormat(String value) {
        this.dateFormat = value;
    }

    /**
     * Recupera il valore della proprietà text.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Imposta il valore della proprietà text.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Recupera il valore della proprietà fontSize.
     * 
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Imposta il valore della proprietà fontSize.
     * 
     */
    public void setFontSize(int value) {
        this.fontSize = value;
    }

}
