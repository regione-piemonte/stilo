/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela;

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
 *         &lt;element name="Processi">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Processo" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="IDProcesso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DescrizioneProcesso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Classi">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Classe" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;extension base="{http://aggiornaanagrafeclassidocclientela.jaxws.jaxbBean.repository2.auriga.eng.it}ClasseSottoclasseType">
 *                                               &lt;sequence minOccurs="0">
 *                                                 &lt;element name="SottoClassi" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/extension>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
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
    "processi"
})
@XmlRootElement(name = "RequestAggiornaAnagrafeClassiDocClientela")
public class RequestAggiornaAnagrafeClassiDocClientela {

    @XmlElement(name = "Processi", required = true)
    protected RequestAggiornaAnagrafeClassiDocClientela.Processi processi;

    /**
     * Recupera il valore della proprietà processi.
     * 
     * @return
     *     possible object is
     *     {@link RequestAggiornaAnagrafeClassiDocClientela.Processi }
     *     
     */
    public RequestAggiornaAnagrafeClassiDocClientela.Processi getProcessi() {
        return processi;
    }

    /**
     * Imposta il valore della proprietà processi.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestAggiornaAnagrafeClassiDocClientela.Processi }
     *     
     */
    public void setProcessi(RequestAggiornaAnagrafeClassiDocClientela.Processi value) {
        this.processi = value;
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
     *         &lt;element name="Processo" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IDProcesso" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="DescrizioneProcesso" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Classi">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Classe" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;extension base="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType">
     *                                     &lt;sequence minOccurs="0">
     *                                       &lt;element name="SottoClassi" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/extension>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
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
        "processo"
    })
    public static class Processi {

        @XmlElement(name = "Processo")
        protected List<RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo> processo;

        /**
         * Gets the value of the processo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the processo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProcesso().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo }
         * 
         * 
         */
        public List<RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo> getProcesso() {
            if (processo == null) {
                processo = new ArrayList<RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo>();
            }
            return this.processo;
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
         *         &lt;element name="IDProcesso" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="DescrizioneProcesso" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Classi">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Classe" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;extension base="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType">
         *                           &lt;sequence minOccurs="0">
         *                             &lt;element name="SottoClassi" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
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
            "idProcesso",
            "descrizioneProcesso",
            "classi"
        })
        public static class Processo {

            @XmlElement(name = "IDProcesso", required = true)
            protected String idProcesso;
            @XmlElement(name = "DescrizioneProcesso", required = true)
            protected String descrizioneProcesso;
            @XmlElement(name = "Classi", required = true)
            protected RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi classi;

            /**
             * Recupera il valore della proprietà idProcesso.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIDProcesso() {
                return idProcesso;
            }

            /**
             * Imposta il valore della proprietà idProcesso.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIDProcesso(String value) {
                this.idProcesso = value;
            }

            /**
             * Recupera il valore della proprietà descrizioneProcesso.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescrizioneProcesso() {
                return descrizioneProcesso;
            }

            /**
             * Imposta il valore della proprietà descrizioneProcesso.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescrizioneProcesso(String value) {
                this.descrizioneProcesso = value;
            }

            /**
             * Recupera il valore della proprietà classi.
             * 
             * @return
             *     possible object is
             *     {@link RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi }
             *     
             */
            public RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi getClassi() {
                return classi;
            }

            /**
             * Imposta il valore della proprietà classi.
             * 
             * @param value
             *     allowed object is
             *     {@link RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi }
             *     
             */
            public void setClassi(RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi value) {
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
             *               &lt;extension base="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType">
             *                 &lt;sequence minOccurs="0">
             *                   &lt;element name="SottoClassi" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
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
                protected List<RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe> classe;

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
                 * {@link RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe }
                 * 
                 * 
                 */
                public List<RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe> getClasse() {
                    if (classe == null) {
                        classe = new ArrayList<RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe>();
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
                 *     &lt;extension base="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType">
                 *       &lt;sequence minOccurs="0">
                 *         &lt;element name="SottoClassi" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
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
                    protected RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe.SottoClassi sottoClassi;

                    /**
                     * Recupera il valore della proprietà sottoClassi.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe.SottoClassi }
                     *     
                     */
                    public RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe.SottoClassi getSottoClassi() {
                        return sottoClassi;
                    }

                    /**
                     * Imposta il valore della proprietà sottoClassi.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe.SottoClassi }
                     *     
                     */
                    public void setSottoClassi(RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe.SottoClassi value) {
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
                     *         &lt;element name="Sottoclasse" type="{http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it}ClasseSottoclasseType" maxOccurs="unbounded"/>
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

    }

}
