/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.documenti.svc._1.ElaboraDocumentoResponse;
import it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStiloResponse;


/**
 * <p>Classe Java per baseResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="baseResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ente" type="{http://siac.csi.it/integ/data/1.0}ente" minOccurs="0"/&gt;
 *         &lt;element name="errori" type="{http://siac.csi.it/integ/data/1.0}errore" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="esito" type="{http://siac.csi.it/integ/data/1.0}esito" minOccurs="0"/&gt;
 *         &lt;element name="messaggi" type="{http://siac.csi.it/integ/data/1.0}messaggio" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseResponse", propOrder = {
    "ente",
    "errori",
    "esito",
    "messaggi"
})
@XmlSeeAlso({
    RicercaMovimentoGestioneStiloResponse.class,
    BaseAsyncResponse.class,
    ElaboraDocumentoResponse.class
})
public abstract class BaseResponse {

    protected Ente ente;
    @XmlElement(nillable = true)
    protected List<Errore> errori;
    @XmlSchemaType(name = "string")
    protected Esito esito;
    @XmlElement(nillable = true)
    protected List<Messaggio> messaggi;

    /**
     * Recupera il valore della proprietà ente.
     * 
     * @return
     *     possible object is
     *     {@link Ente }
     *     
     */
    public Ente getEnte() {
        return ente;
    }

    /**
     * Imposta il valore della proprietà ente.
     * 
     * @param value
     *     allowed object is
     *     {@link Ente }
     *     
     */
    public void setEnte(Ente value) {
        this.ente = value;
    }

    /**
     * Gets the value of the errori property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errori property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrori().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Errore }
     * 
     * 
     */
    public List<Errore> getErrori() {
        if (errori == null) {
            errori = new ArrayList<Errore>();
        }
        return this.errori;
    }

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link Esito }
     *     
     */
    public Esito getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link Esito }
     *     
     */
    public void setEsito(Esito value) {
        this.esito = value;
    }

    /**
     * Gets the value of the messaggi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messaggi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessaggi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Messaggio }
     * 
     * 
     */
    public List<Messaggio> getMessaggi() {
        if (messaggi == null) {
            messaggi = new ArrayList<Messaggio>();
        }
        return this.messaggi;
    }

}
