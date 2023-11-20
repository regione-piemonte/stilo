/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DatiCertificatoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DatiCertificatoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="DatiCertificatoMarcaTemporale" type="{archive.acaris.acta.doqui.it}DatiCertificatoMarcaTemporaleXMLType"/&gt;
 *         &lt;element name="DatiCertificatoFirmaDigitale" type="{archive.acaris.acta.doqui.it}DatiCertificatoFirmaDigitaleXMLType"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiCertificatoType", propOrder = {
    "datiCertificatoMarcaTemporale",
    "datiCertificatoFirmaDigitale"
})
public class DatiCertificatoType {

    @XmlElement(name = "DatiCertificatoMarcaTemporale")
    protected String datiCertificatoMarcaTemporale;
    @XmlElement(name = "DatiCertificatoFirmaDigitale")
    protected String datiCertificatoFirmaDigitale;

    /**
     * Recupera il valore della proprietà datiCertificatoMarcaTemporale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiCertificatoMarcaTemporale() {
        return datiCertificatoMarcaTemporale;
    }

    /**
     * Imposta il valore della proprietà datiCertificatoMarcaTemporale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiCertificatoMarcaTemporale(String value) {
        this.datiCertificatoMarcaTemporale = value;
    }

    /**
     * Recupera il valore della proprietà datiCertificatoFirmaDigitale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiCertificatoFirmaDigitale() {
        return datiCertificatoFirmaDigitale;
    }

    /**
     * Imposta il valore della proprietà datiCertificatoFirmaDigitale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiCertificatoFirmaDigitale(String value) {
        this.datiCertificatoFirmaDigitale = value;
    }

}
