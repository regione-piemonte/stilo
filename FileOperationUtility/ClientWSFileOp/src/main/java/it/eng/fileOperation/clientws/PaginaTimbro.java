/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per paginaTimbro complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paginaTimbro"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pagine" minOccurs="0" form="qualified"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="pagina" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0" form="qualified"/&gt;
 *                   &lt;element name="paginaDa" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *                   &lt;element name="paginaA" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="tipoPagina" type="{it.eng.fileoperation.ws.timbro}tipoPagina" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paginaTimbro", namespace = "it.eng.fileoperation.ws.timbro", propOrder = {
    "pagine",
    "tipoPagina"
})
public class PaginaTimbro {

    protected PaginaTimbro.Pagine pagine;
    @XmlSchemaType(name = "string")
    protected TipoPagina tipoPagina;

    /**
     * Recupera il valore della proprietà pagine.
     * 
     * @return
     *     possible object is
     *     {@link PaginaTimbro.Pagine }
     *     
     */
    public PaginaTimbro.Pagine getPagine() {
        return pagine;
    }

    /**
     * Imposta il valore della proprietà pagine.
     * 
     * @param value
     *     allowed object is
     *     {@link PaginaTimbro.Pagine }
     *     
     */
    public void setPagine(PaginaTimbro.Pagine value) {
        this.pagine = value;
    }

    /**
     * Recupera il valore della proprietà tipoPagina.
     * 
     * @return
     *     possible object is
     *     {@link TipoPagina }
     *     
     */
    public TipoPagina getTipoPagina() {
        return tipoPagina;
    }

    /**
     * Imposta il valore della proprietà tipoPagina.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoPagina }
     *     
     */
    public void setTipoPagina(TipoPagina value) {
        this.tipoPagina = value;
    }


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
     *         &lt;element name="pagina" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0" form="qualified"/&gt;
     *         &lt;element name="paginaDa" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
     *         &lt;element name="paginaA" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0" form="qualified"/&gt;
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
        "pagina",
        "paginaDa",
        "paginaA"
    })
    public static class Pagine {

        @XmlElement(namespace = "it.eng.fileoperation.ws.timbro", type = Integer.class)
        protected List<Integer> pagina;
        @XmlElement(namespace = "it.eng.fileoperation.ws.timbro")
        protected Integer paginaDa;
        @XmlElement(namespace = "it.eng.fileoperation.ws.timbro")
        protected Integer paginaA;

        /**
         * Gets the value of the pagina property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pagina property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPagina().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Integer }
         * 
         * 
         */
        public List<Integer> getPagina() {
            if (pagina == null) {
                pagina = new ArrayList<Integer>();
            }
            return this.pagina;
        }

        /**
         * Recupera il valore della proprietà paginaDa.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getPaginaDa() {
            return paginaDa;
        }

        /**
         * Imposta il valore della proprietà paginaDa.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setPaginaDa(Integer value) {
            this.paginaDa = value;
        }

        /**
         * Recupera il valore della proprietà paginaA.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getPaginaA() {
            return paginaA;
        }

        /**
         * Imposta il valore della proprietà paginaA.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setPaginaA(Integer value) {
            this.paginaA = value;
        }

    }

}
