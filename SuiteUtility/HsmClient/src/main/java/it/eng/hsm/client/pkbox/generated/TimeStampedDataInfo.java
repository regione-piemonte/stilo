
package it.eng.hsm.client.pkbox.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.hsm.client.pkbox.common.generated.TimeStamp;


/**
 * <p>Classe Java per TimeStampedDataInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TimeStampedDataInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="dataLen" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dataURI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hashProtected" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="mediaType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timeStamps" type="{http://server.pkbox.it/xsd}TimeStamp" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeStampedDataInfo", namespace = "http://server.pkbox.it/xsd", propOrder = {
    "data",
    "dataLen",
    "dataURI",
    "fileName",
    "hashProtected",
    "mediaType",
    "timeStamps"
})
public class TimeStampedDataInfo {

    @XmlElement(required = true, nillable = true)
    protected byte[] data;
    protected long dataLen;
    @XmlElement(required = true, nillable = true)
    protected String dataURI;
    @XmlElement(required = true, nillable = true)
    protected String fileName;
    protected boolean hashProtected;
    @XmlElement(required = true, nillable = true)
    protected String mediaType;
    @XmlElement(nillable = true)
    protected List<TimeStamp> timeStamps;

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
     * Recupera il valore della proprietà dataURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataURI() {
        return dataURI;
    }

    /**
     * Imposta il valore della proprietà dataURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataURI(String value) {
        this.dataURI = value;
    }

    /**
     * Recupera il valore della proprietà fileName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Imposta il valore della proprietà fileName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Recupera il valore della proprietà hashProtected.
     * 
     */
    public boolean isHashProtected() {
        return hashProtected;
    }

    /**
     * Imposta il valore della proprietà hashProtected.
     * 
     */
    public void setHashProtected(boolean value) {
        this.hashProtected = value;
    }

    /**
     * Recupera il valore della proprietà mediaType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Imposta il valore della proprietà mediaType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaType(String value) {
        this.mediaType = value;
    }

    /**
     * Gets the value of the timeStamps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timeStamps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTimeStamps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeStamp }
     * 
     * 
     */
    public List<TimeStamp> getTimeStamps() {
        if (timeStamps == null) {
            timeStamps = new ArrayList<TimeStamp>();
        }
        return this.timeStamps;
    }

}
