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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Classi">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Classe" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ClasseSottoclasseType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="SottoClassi" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
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
    "classi"
})
@XmlRootElement(name = "RequestAggiornaAnagrafeClassiDoc")
public class RequestAggiornaAnagrafeClassiDoc {

    @XmlElement(name = "Classi", required = true)
    protected RequestAggiornaAnagrafeClassiDoc.Classi classi;

    /**
     * Recupera il valore della proprietà classi.
     * 
     * @return
     *     possible object is
     *     {@link RequestAggiornaAnagrafeClassiDoc.Classi }
     *     
     */
    public RequestAggiornaAnagrafeClassiDoc.Classi getClassi() {
        return classi;
    }

    /**
     * Imposta il valore della proprietà classi.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestAggiornaAnagrafeClassiDoc.Classi }
     *     
     */
    public void setClassi(RequestAggiornaAnagrafeClassiDoc.Classi value) {
        this.classi = value;
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
     *         &lt;element name="Classe" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ClasseSottoclasseType">
     *                 &lt;sequence minOccurs="0">
     *                   &lt;element name="SottoClassi" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
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
        "classe"
    })
    public static class Classi {

        @XmlElement(name = "Classe", required = true)
        protected List<RequestAggiornaAnagrafeClassiDoc.Classi.Classe> classe;

        /**
         * Gets the value of the classe property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the classe property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getClasse().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RequestAggiornaAnagrafeClassiDoc.Classi.Classe }
         * 
         * 
         */
        public List<RequestAggiornaAnagrafeClassiDoc.Classi.Classe> getClasse() {
            if (classe == null) {
                classe = new ArrayList<RequestAggiornaAnagrafeClassiDoc.Classi.Classe>();
            }
            return this.classe;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ClasseSottoclasseType">
         *       &lt;sequence minOccurs="0">
         *         &lt;element name="SottoClassi" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sottoClassi"
        })
        public static class Classe
            extends ClasseSottoclasseType
        {

            @XmlElement(name = "SottoClassi")
            protected RequestAggiornaAnagrafeClassiDoc.Classi.Classe.SottoClassi sottoClassi;

            /**
             * Recupera il valore della proprietà sottoClassi.
             * 
             * @return
             *     possible object is
             *     {@link RequestAggiornaAnagrafeClassiDoc.Classi.Classe.SottoClassi }
             *     
             */
            public RequestAggiornaAnagrafeClassiDoc.Classi.Classe.SottoClassi getSottoClassi() {
                return sottoClassi;
            }

            /**
             * Imposta il valore della proprietà sottoClassi.
             * 
             * @param value
             *     allowed object is
             *     {@link RequestAggiornaAnagrafeClassiDoc.Classi.Classe.SottoClassi }
             *     
             */
            public void setSottoClassi(RequestAggiornaAnagrafeClassiDoc.Classi.Classe.SottoClassi value) {
                this.sottoClassi = value;
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
             *         &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
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
                "sottoclasse"
            })
            public static class SottoClassi {

                @XmlElement(name = "Sottoclasse", required = true)
                protected List<ClasseSottoclasseType> sottoclasse;

                /**
                 * Gets the value of the sottoclasse property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the sottoclasse property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getSottoclasse().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link ClasseSottoclasseType }
                 * 
                 * 
                 */
                public List<ClasseSottoclasseType> getSottoclasse() {
                    if (sottoclasse == null) {
                        sottoclasse = new ArrayList<ClasseSottoclasseType>();
                    }
                    return this.sottoclasse;
                }

            }

        }

    }

}
