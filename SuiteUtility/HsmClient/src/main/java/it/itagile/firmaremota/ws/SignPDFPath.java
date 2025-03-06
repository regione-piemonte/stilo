
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
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pinToSign" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="domain" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dirSource" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dirDest" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="graphic" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
    "userid",
    "password",
    "pinToSign",
    "domain",
    "dirSource",
    "dirDest",
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
    "graphic",
    "text",
    "fontSize"
})
@XmlRootElement(name = "signPDFPath")
public class SignPDFPath {

    @XmlElement(required = true)
    protected String userid;
    @XmlElement(required = true)
    protected String password;
    @XmlElement(required = true)
    protected String pinToSign;
    @XmlElement(required = true)
    protected String domain;
    @XmlElement(required = true)
    protected String dirSource;
    @XmlElement(required = true)
    protected String dirDest;
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
    protected boolean graphic;
    @XmlElement(required = true)
    protected String text;
    protected int fontSize;

    /**
     * Recupera il valore della proprietà userid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Imposta il valore della proprietà userid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserid(String value) {
        this.userid = value;
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
     * Recupera il valore della proprietà pinToSign.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPinToSign() {
        return pinToSign;
    }

    /**
     * Imposta il valore della proprietà pinToSign.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPinToSign(String value) {
        this.pinToSign = value;
    }

    /**
     * Recupera il valore della proprietà domain.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Imposta il valore della proprietà domain.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

    /**
     * Recupera il valore della proprietà dirSource.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirSource() {
        return dirSource;
    }

    /**
     * Imposta il valore della proprietà dirSource.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirSource(String value) {
        this.dirSource = value;
    }

    /**
     * Recupera il valore della proprietà dirDest.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirDest() {
        return dirDest;
    }

    /**
     * Imposta il valore della proprietà dirDest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirDest(String value) {
        this.dirDest = value;
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
     * Recupera il valore della proprietà graphic.
     * 
     */
    public boolean isGraphic() {
        return graphic;
    }

    /**
     * Imposta il valore della proprietà graphic.
     * 
     */
    public void setGraphic(boolean value) {
        this.graphic = value;
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
