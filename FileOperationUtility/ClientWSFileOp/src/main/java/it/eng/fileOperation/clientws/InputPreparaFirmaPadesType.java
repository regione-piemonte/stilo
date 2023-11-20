/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InputPreparaFirmaPadesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputPreparaFirmaPadesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="userCertificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="firmatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="nomeCampoFirma" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/&gt;
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="informazioniFirmaGrafica" type="{it.eng.fileoperation.ws.timbro}InformazioniFirmaGraficaType" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputPreparaFirmaPadesType", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "userCertificate",
    "firmatario",
    "nomeCampoFirma",
    "reason",
    "location",
    "informazioniFirmaGrafica"
})
public class InputPreparaFirmaPadesType
    extends AbstractInputOperationType
{

	@XmlMimeType("application/octet-stream")
	protected DataHandler userCertificate;
    protected String firmatario;
    @XmlElement(required = true)
    protected String nomeCampoFirma;
    protected String reason;
    protected String location;
    protected InformazioniFirmaGraficaType informazioniFirmaGrafica;

    /**
     * Recupera il valore della propriet� userCertificate.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getUserCertificate() {
        return userCertificate;
    }

    /**
     * Imposta il valore della propriet� userCertificate.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setUserCertificate(DataHandler value) {
        this.userCertificate = value;
    }

    /**
     * Recupera il valore della propriet� firmatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmatario() {
        return firmatario;
    }

    /**
     * Imposta il valore della propriet� firmatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmatario(String value) {
        this.firmatario = value;
    }

    /**
     * Recupera il valore della propriet� nomeCampoFirma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeCampoFirma() {
        return nomeCampoFirma;
    }

    /**
     * Imposta il valore della propriet� nomeCampoFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeCampoFirma(String value) {
        this.nomeCampoFirma = value;
    }

    /**
     * Recupera il valore della propriet� reason.
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
     * Imposta il valore della propriet� reason.
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
     * Recupera il valore della propriet� location.
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
     * Imposta il valore della propriet� location.
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
     * Recupera il valore della propriet� informazioniFirmaGrafica.
     * 
     * @return
     *     possible object is
     *     {@link InformazioniFirmaGraficaType }
     *     
     */
    public InformazioniFirmaGraficaType getInformazioniFirmaGrafica() {
        return informazioniFirmaGrafica;
    }

    /**
     * Imposta il valore della propriet� informazioniFirmaGrafica.
     * 
     * @param value
     *     allowed object is
     *     {@link InformazioniFirmaGraficaType }
     *     
     */
    public void setInformazioniFirmaGrafica(InformazioniFirmaGraficaType value) {
        this.informazioniFirmaGrafica = value;
    }

}
