
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DettaglioStrutturaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioStrutturaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idStruttura" type="{common.acaris.acta.doqui.it}IdStrutturaType"/&gt;
 *         &lt;element name="codice" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="denominazione" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="indirizzo" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="citta" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="email" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="emailPEC" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="sitoWeb" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="telefono" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="fax" type="{common.acaris.acta.doqui.it}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioStrutturaType", namespace = "backoffice.acaris.acta.doqui.it", propOrder = {
    "idStruttura",
    "codice",
    "denominazione",
    "indirizzo",
    "citta",
    "email",
    "emailPEC",
    "sitoWeb",
    "telefono",
    "fax"
})
public class DettaglioStrutturaType {

    @XmlElement(required = true)
    protected IdStrutturaType idStruttura;
    @XmlElement(required = true)
    protected String codice;
    @XmlElement(required = true)
    protected String denominazione;
    @XmlElement(required = true)
    protected String indirizzo;
    @XmlElement(required = true)
    protected String citta;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String emailPEC;
    @XmlElement(required = true)
    protected String sitoWeb;
    @XmlElement(required = true)
    protected String telefono;
    @XmlElement(required = true)
    protected String fax;

    /**
     * Recupera il valore della proprietÓ idStruttura.
     * 
     * @return
     *     possible object is
     *     {@link IdStrutturaType }
     *     
     */
    public IdStrutturaType getIdStruttura() {
        return idStruttura;
    }

    /**
     * Imposta il valore della proprietÓ idStruttura.
     * 
     * @param value
     *     allowed object is
     *     {@link IdStrutturaType }
     *     
     */
    public void setIdStruttura(IdStrutturaType value) {
        this.idStruttura = value;
    }

    /**
     * Recupera il valore della proprietÓ codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietÓ codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della proprietÓ denominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Imposta il valore della proprietÓ denominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Recupera il valore della proprietÓ indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprietÓ indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprietÓ citta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitta() {
        return citta;
    }

    /**
     * Imposta il valore della proprietÓ citta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitta(String value) {
        this.citta = value;
    }

    /**
     * Recupera il valore della proprietÓ email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta il valore della proprietÓ email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Recupera il valore della proprietÓ emailPEC.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailPEC() {
        return emailPEC;
    }

    /**
     * Imposta il valore della proprietÓ emailPEC.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailPEC(String value) {
        this.emailPEC = value;
    }

    /**
     * Recupera il valore della proprietÓ sitoWeb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitoWeb() {
        return sitoWeb;
    }

    /**
     * Imposta il valore della proprietÓ sitoWeb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitoWeb(String value) {
        this.sitoWeb = value;
    }

    /**
     * Recupera il valore della proprietÓ telefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il valore della proprietÓ telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Recupera il valore della proprietÓ fax.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Imposta il valore della proprietÓ fax.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

}
