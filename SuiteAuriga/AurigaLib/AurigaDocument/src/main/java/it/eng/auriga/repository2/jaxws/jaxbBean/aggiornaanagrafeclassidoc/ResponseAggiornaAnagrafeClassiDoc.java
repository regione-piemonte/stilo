/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.06.09 alle 02:55:53 PM CEST 
//


package it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidoc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="Errori" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Errore" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="codice" use="required" type="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ErrorType" />
 *                           &lt;attribute name="nomeClasse" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="nomeSottoclasse" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="idTipologia" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="nomeAttributo" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="esito" use="required" type="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ResultType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "errori"
})
@XmlRootElement(name = "ResponseAggiornaAnagrafeClassiDoc")
public class ResponseAggiornaAnagrafeClassiDoc {

    @XmlElement(name = "Errori")
    protected ResponseAggiornaAnagrafeClassiDoc.Errori errori;
    @XmlAttribute(name = "esito", required = true)
    protected ResultType esito;

    /**
     * Recupera il valore della proprietà errori.
     * 
     * @return
     *     possible object is
     *     {@link ResponseAggiornaAnagrafeClassiDoc.Errori }
     *     
     */
    public ResponseAggiornaAnagrafeClassiDoc.Errori getErrori() {
        return errori;
    }

    /**
     * Imposta il valore della proprietà errori.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseAggiornaAnagrafeClassiDoc.Errori }
     *     
     */
    public void setErrori(ResponseAggiornaAnagrafeClassiDoc.Errori value) {
        this.errori = value;
    }

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link ResultType }
     *     
     */
    public ResultType getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultType }
     *     
     */
    public void setEsito(ResultType value) {
        this.esito = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Errore" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="codice" use="required" type="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ErrorType" />
     *                 &lt;attribute name="nomeClasse" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="nomeSottoclasse" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="idTipologia" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="nomeAttributo" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "errore"
    })
    public static class Errori {

        @XmlElement(name = "Errore", required = true)
        protected List<ResponseAggiornaAnagrafeClassiDoc.Errori.Errore> errore;

        
		public void setErrore(List<ResponseAggiornaAnagrafeClassiDoc.Errori.Errore> errore) {
			this.errore = errore;
		}
		

		/**
         * Gets the value of the errore property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the errore property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getErrore().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ResponseAggiornaAnagrafeClassiDoc.Errori.Errore }
         * 
         * 
         */
        public List<ResponseAggiornaAnagrafeClassiDoc.Errori.Errore> getErrore() {
            if (errore == null) {
                errore = new ArrayList<ResponseAggiornaAnagrafeClassiDoc.Errori.Errore>();
            }
            return this.errore;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="codice" use="required" type="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ErrorType" />
         *       &lt;attribute name="nomeClasse" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="nomeSottoclasse" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="idTipologia" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="nomeAttributo" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Errore {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "codice", required = true)
            protected ErrorType codice;
            @XmlAttribute(name = "nomeClasse")
            protected String nomeClasse;
            @XmlAttribute(name = "nomeSottoclasse")
            protected String nomeSottoclasse;
            @XmlAttribute(name = "idTipologia")
            protected String idTipologia;
            @XmlAttribute(name = "nomeAttributo")
            protected String nomeAttributo;

            /**
             * Recupera il valore della proprietà value.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Imposta il valore della proprietà value.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Recupera il valore della proprietà codice.
             * 
             * @return
             *     possible object is
             *     {@link ErrorType }
             *     
             */
            public ErrorType getCodice() {
                return codice;
            }

            /**
             * Imposta il valore della proprietà codice.
             * 
             * @param value
             *     allowed object is
             *     {@link ErrorType }
             *     
             */
            public void setCodice(ErrorType value) {
                this.codice = value;
            }

            /**
             * Recupera il valore della proprietà nomeClasse.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNomeClasse() {
                return nomeClasse;
            }

            /**
             * Imposta il valore della proprietà nomeClasse.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNomeClasse(String value) {
                this.nomeClasse = value;
            }

            /**
             * Recupera il valore della proprietà nomeSottoclasse.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNomeSottoclasse() {
                return nomeSottoclasse;
            }

            /**
             * Imposta il valore della proprietà nomeSottoclasse.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNomeSottoclasse(String value) {
                this.nomeSottoclasse = value;
            }

            /**
             * Recupera il valore della proprietà idTipologia.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIdTipologia() {
                return idTipologia;
            }

            /**
             * Imposta il valore della proprietà idTipologia.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIdTipologia(String value) {
                this.idTipologia = value;
            }

            /**
             * Recupera il valore della proprietà nomeAttributo.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNomeAttributo() {
                return nomeAttributo;
            }

            /**
             * Imposta il valore della proprietà nomeAttributo.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNomeAttributo(String value) {
                this.nomeAttributo = value;
            }

        }

    }

}
