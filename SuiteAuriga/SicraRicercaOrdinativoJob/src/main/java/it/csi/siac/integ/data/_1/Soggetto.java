/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3._2001.xmlschema.Adapter1;


/**
 * <p>Classe Java per soggetto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="soggetto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codiceFiscaleEstero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codiciSoggettiCollegatiPrecedenti" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="codiciSoggettiCollegatiSuccessivi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="contatti" type="{http://siac.csi.it/integ/data/1.0}contatti" minOccurs="0"/&gt;
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="elencoModalitaPagamento" type="{http://siac.csi.it/integ/data/1.0}modalitaPagamento" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="elencoSedi" type="{http://siac.csi.it/integ/data/1.0}sede" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="indirizzoPrincipale" type="{http://siac.csi.it/integ/data/1.0}recapito" minOccurs="0"/&gt;
 *         &lt;element name="naturaGiuridica" type="{http://siac.csi.it/integ/data/1.0}naturaGiuridica" minOccurs="0"/&gt;
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="partitaIva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ragioneSociale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sesso" type="{http://siac.csi.it/integ/data/1.0}sesso" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soggetto", propOrder = {
    "codiceFiscale",
    "codiceFiscaleEstero",
    "codiciSoggettiCollegatiPrecedenti",
    "codiciSoggettiCollegatiSuccessivi",
    "cognome",
    "contatti",
    "dataNascita",
    "elencoModalitaPagamento",
    "elencoSedi",
    "indirizzoPrincipale",
    "naturaGiuridica",
    "nome",
    "partitaIva",
    "ragioneSociale",
    "sesso"
})
public class Soggetto
    extends EntitaBase
{

    protected String codiceFiscale;
    protected String codiceFiscaleEstero;
    @XmlElement(nillable = true)
    protected List<String> codiciSoggettiCollegatiPrecedenti;
    @XmlElement(nillable = true)
    protected List<String> codiciSoggettiCollegatiSuccessivi;
    protected String cognome;
    protected Contatti contatti;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date dataNascita;
    @XmlElement(nillable = true)
    protected List<ModalitaPagamento> elencoModalitaPagamento;
    @XmlElement(nillable = true)
    protected List<Sede> elencoSedi;
    protected Recapito indirizzoPrincipale;
    protected NaturaGiuridica naturaGiuridica;
    protected String nome;
    protected String partitaIva;
    protected String ragioneSociale;
    @XmlSchemaType(name = "string")
    protected Sesso sesso;

    /**
     * Recupera il valore della proprietà codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietà codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscaleEstero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleEstero() {
        return codiceFiscaleEstero;
    }

    /**
     * Imposta il valore della proprietà codiceFiscaleEstero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleEstero(String value) {
        this.codiceFiscaleEstero = value;
    }

    /**
     * Gets the value of the codiciSoggettiCollegatiPrecedenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codiciSoggettiCollegatiPrecedenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodiciSoggettiCollegatiPrecedenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCodiciSoggettiCollegatiPrecedenti() {
        if (codiciSoggettiCollegatiPrecedenti == null) {
            codiciSoggettiCollegatiPrecedenti = new ArrayList<String>();
        }
        return this.codiciSoggettiCollegatiPrecedenti;
    }

    /**
     * Gets the value of the codiciSoggettiCollegatiSuccessivi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codiciSoggettiCollegatiSuccessivi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodiciSoggettiCollegatiSuccessivi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCodiciSoggettiCollegatiSuccessivi() {
        if (codiciSoggettiCollegatiSuccessivi == null) {
            codiciSoggettiCollegatiSuccessivi = new ArrayList<String>();
        }
        return this.codiciSoggettiCollegatiSuccessivi;
    }

    /**
     * Recupera il valore della proprietà cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprietà cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprietà contatti.
     * 
     * @return
     *     possible object is
     *     {@link Contatti }
     *     
     */
    public Contatti getContatti() {
        return contatti;
    }

    /**
     * Imposta il valore della proprietà contatti.
     * 
     * @param value
     *     allowed object is
     *     {@link Contatti }
     *     
     */
    public void setContatti(Contatti value) {
        this.contatti = value;
    }

    /**
     * Recupera il valore della proprietà dataNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * Imposta il valore della proprietà dataNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataNascita(Date value) {
        this.dataNascita = value;
    }

    /**
     * Gets the value of the elencoModalitaPagamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elencoModalitaPagamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElencoModalitaPagamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModalitaPagamento }
     * 
     * 
     */
    public List<ModalitaPagamento> getElencoModalitaPagamento() {
        if (elencoModalitaPagamento == null) {
            elencoModalitaPagamento = new ArrayList<ModalitaPagamento>();
        }
        return this.elencoModalitaPagamento;
    }

    /**
     * Gets the value of the elencoSedi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elencoSedi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElencoSedi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sede }
     * 
     * 
     */
    public List<Sede> getElencoSedi() {
        if (elencoSedi == null) {
            elencoSedi = new ArrayList<Sede>();
        }
        return this.elencoSedi;
    }

    /**
     * Recupera il valore della proprietà indirizzoPrincipale.
     * 
     * @return
     *     possible object is
     *     {@link Recapito }
     *     
     */
    public Recapito getIndirizzoPrincipale() {
        return indirizzoPrincipale;
    }

    /**
     * Imposta il valore della proprietà indirizzoPrincipale.
     * 
     * @param value
     *     allowed object is
     *     {@link Recapito }
     *     
     */
    public void setIndirizzoPrincipale(Recapito value) {
        this.indirizzoPrincipale = value;
    }

    /**
     * Recupera il valore della proprietà naturaGiuridica.
     * 
     * @return
     *     possible object is
     *     {@link NaturaGiuridica }
     *     
     */
    public NaturaGiuridica getNaturaGiuridica() {
        return naturaGiuridica;
    }

    /**
     * Imposta il valore della proprietà naturaGiuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link NaturaGiuridica }
     *     
     */
    public void setNaturaGiuridica(NaturaGiuridica value) {
        this.naturaGiuridica = value;
    }

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietà partitaIva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Imposta il valore della proprietà partitaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIva(String value) {
        this.partitaIva = value;
    }

    /**
     * Recupera il valore della proprietà ragioneSociale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    /**
     * Imposta il valore della proprietà ragioneSociale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSociale(String value) {
        this.ragioneSociale = value;
    }

    /**
     * Recupera il valore della proprietà sesso.
     * 
     * @return
     *     possible object is
     *     {@link Sesso }
     *     
     */
    public Sesso getSesso() {
        return sesso;
    }

    /**
     * Imposta il valore della proprietà sesso.
     * 
     * @param value
     *     allowed object is
     *     {@link Sesso }
     *     
     */
    public void setSesso(Sesso value) {
        this.sesso = value;
    }

}
