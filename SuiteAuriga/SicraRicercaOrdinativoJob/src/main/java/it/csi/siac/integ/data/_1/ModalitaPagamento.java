/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per modalitaPagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="modalitaPagamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaCodificataBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceTipoAccredito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneTipoAccredito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sede" type="{http://siac.csi.it/integ/data/1.0}sede" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modalitaPagamento", propOrder = {
    "codiceTipoAccredito",
    "descrizioneTipoAccredito",
    "sede"
})
public class ModalitaPagamento
    extends EntitaCodificataBase
{

    protected String codiceTipoAccredito;
    protected String descrizioneTipoAccredito;
    protected Sede sede;

    /**
     * Recupera il valore della proprietà codiceTipoAccredito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceTipoAccredito() {
        return codiceTipoAccredito;
    }

    /**
     * Imposta il valore della proprietà codiceTipoAccredito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceTipoAccredito(String value) {
        this.codiceTipoAccredito = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneTipoAccredito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneTipoAccredito() {
        return descrizioneTipoAccredito;
    }

    /**
     * Imposta il valore della proprietà descrizioneTipoAccredito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneTipoAccredito(String value) {
        this.descrizioneTipoAccredito = value;
    }

    /**
     * Recupera il valore della proprietà sede.
     * 
     * @return
     *     possible object is
     *     {@link Sede }
     *     
     */
    public Sede getSede() {
        return sede;
    }

    /**
     * Imposta il valore della proprietà sede.
     * 
     * @param value
     *     allowed object is
     *     {@link Sede }
     *     
     */
    public void setSede(Sede value) {
        this.sede = value;
    }

}
