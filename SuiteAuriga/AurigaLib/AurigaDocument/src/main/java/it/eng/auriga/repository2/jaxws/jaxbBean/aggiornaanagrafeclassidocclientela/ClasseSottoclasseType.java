/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Contiene il data model di una classe o sottoclasse
 * 
 * <p>Classe Java per ClasseSottoclasseType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ClasseSottoclasseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Attributi" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Attributo" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Label">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="64"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="RegExp" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="350"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Tipo">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;pattern value="Boolean"/>
 *                                   &lt;pattern value="Double"/>
 *                                   &lt;pattern value="ID"/>
 *                                   &lt;pattern value="Integer"/>
 *                                   &lt;pattern value="String"/>
 *                                   &lt;pattern value="Time"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Dimensione" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                                   &lt;totalDigits value="4"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Ripetibile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;element name="ValoriAmmessi" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ValoreAmmesso" maxOccurs="unbounded">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;minLength value="1"/>
 *                                             &lt;maxLength value="255"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="nome" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;minLength value="1"/>
 *                                 &lt;maxLength value="32"/>
 *                                 &lt;whiteSpace value="collapse"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Tipologie" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Tipologia" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="id" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;minLength value="1"/>
 *                                 &lt;maxLength value="30"/>
 *                                 &lt;whiteSpace value="replace"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="descrizione" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;minLength value="1"/>
 *                                 &lt;maxLength value="300"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="valida" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
 *       &lt;attribute name="nome" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="300"/>
 *             &lt;whiteSpace value="collapse"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="descrizione" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="300"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClasseSottoclasseType", propOrder = {
    "attributi",
    "tipologie"
})
@XmlSeeAlso({
    it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela.RequestAggiornaAnagrafeClassiDocClientela.Processi.Processo.Classi.Classe.class
})
public class ClasseSottoclasseType {

    @XmlElement(name = "Attributi")
    protected ClasseSottoclasseType.Attributi attributi;
    @XmlElement(name = "Tipologie")
    protected ClasseSottoclasseType.Tipologie tipologie;
    @XmlAttribute(name = "nome", required = true)
    protected String nome;
    @XmlAttribute(name = "descrizione", required = true)
    protected String descrizione;

    /**
     * Recupera il valore della proprietà attributi.
     * 
     * @return
     *     possible object is
     *     {@link ClasseSottoclasseType.Attributi }
     *     
     */
    public ClasseSottoclasseType.Attributi getAttributi() {
        return attributi;
    }

    /**
     * Imposta il valore della proprietà attributi.
     * 
     * @param value
     *     allowed object is
     *     {@link ClasseSottoclasseType.Attributi }
     *     
     */
    public void setAttributi(ClasseSottoclasseType.Attributi value) {
        this.attributi = value;
    }

    /**
     * Recupera il valore della proprietà tipologie.
     * 
     * @return
     *     possible object is
     *     {@link ClasseSottoclasseType.Tipologie }
     *     
     */
    public ClasseSottoclasseType.Tipologie getTipologie() {
        return tipologie;
    }

    /**
     * Imposta il valore della proprietà tipologie.
     * 
     * @param value
     *     allowed object is
     *     {@link ClasseSottoclasseType.Tipologie }
     *     
     */
    public void setTipologie(ClasseSottoclasseType.Tipologie value) {
        this.tipologie = value;
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
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
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
     *         &lt;element name="Attributo" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Label">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="1"/>
     *                         &lt;maxLength value="64"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="RegExp" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="350"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Tipo">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;pattern value="Boolean"/>
     *                         &lt;pattern value="Double"/>
     *                         &lt;pattern value="ID"/>
     *                         &lt;pattern value="Integer"/>
     *                         &lt;pattern value="String"/>
     *                         &lt;pattern value="Time"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Dimensione" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *                         &lt;totalDigits value="4"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Ripetibile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                   &lt;element name="ValoriAmmessi" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ValoreAmmesso" maxOccurs="unbounded">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="255"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="nome" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;minLength value="1"/>
     *                       &lt;maxLength value="32"/>
     *                       &lt;whiteSpace value="collapse"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
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
        "attributo"
    })
    public static class Attributi {

        @XmlElement(name = "Attributo", required = true)
        protected List<ClasseSottoclasseType.Attributi.Attributo> attributo;

        /**
         * Gets the value of the attributo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the attributo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAttributo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClasseSottoclasseType.Attributi.Attributo }
         * 
         * 
         */
        public List<ClasseSottoclasseType.Attributi.Attributo> getAttributo() {
            if (attributo == null) {
                attributo = new ArrayList<ClasseSottoclasseType.Attributi.Attributo>();
            }
            return this.attributo;
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
         *         &lt;element name="Label">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="1"/>
         *               &lt;maxLength value="64"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="RegExp" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="350"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Tipo">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;pattern value="Boolean"/>
         *               &lt;pattern value="Double"/>
         *               &lt;pattern value="ID"/>
         *               &lt;pattern value="Integer"/>
         *               &lt;pattern value="String"/>
         *               &lt;pattern value="Time"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Dimensione" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
         *               &lt;totalDigits value="4"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Ripetibile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *         &lt;element name="ValoriAmmessi" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ValoreAmmesso" maxOccurs="unbounded">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="255"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="nome" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;minLength value="1"/>
         *             &lt;maxLength value="32"/>
         *             &lt;whiteSpace value="collapse"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "label",
            "regExp",
            "tipo",
            "dimensione",
            "ripetibile",
            "valoriAmmessi"
        })
        public static class Attributo {

            @XmlElement(name = "Label", required = true)
            protected String label;
            @XmlElement(name = "RegExp")
            protected String regExp;
            @XmlElement(name = "Tipo", required = true)
            protected String tipo;
            @XmlElement(name = "Dimensione")
            protected BigInteger dimensione;
            @XmlElement(name = "Ripetibile")
            protected boolean ripetibile;
            @XmlElement(name = "ValoriAmmessi")
            protected ClasseSottoclasseType.Attributi.Attributo.ValoriAmmessi valoriAmmessi;
            @XmlAttribute(name = "nome", required = true)
            protected String nome;

            /**
             * Recupera il valore della proprietà label.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLabel() {
                return label;
            }

            /**
             * Imposta il valore della proprietà label.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLabel(String value) {
                this.label = value;
            }

            /**
             * Recupera il valore della proprietà regExp.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRegExp() {
                return regExp;
            }

            /**
             * Imposta il valore della proprietà regExp.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRegExp(String value) {
                this.regExp = value;
            }

            /**
             * Recupera il valore della proprietà tipo.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipo() {
                return tipo;
            }

            /**
             * Imposta il valore della proprietà tipo.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipo(String value) {
                this.tipo = value;
            }

            /**
             * Recupera il valore della proprietà dimensione.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getDimensione() {
                return dimensione;
            }

            /**
             * Imposta il valore della proprietà dimensione.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setDimensione(BigInteger value) {
                this.dimensione = value;
            }

            /**
             * Recupera il valore della proprietà ripetibile.
             * 
             */
            public boolean isRipetibile() {
                return ripetibile;
            }

            /**
             * Imposta il valore della proprietà ripetibile.
             * 
             */
            public void setRipetibile(boolean value) {
                this.ripetibile = value;
            }

            /**
             * Recupera il valore della proprietà valoriAmmessi.
             * 
             * @return
             *     possible object is
             *     {@link ClasseSottoclasseType.Attributi.Attributo.ValoriAmmessi }
             *     
             */
            public ClasseSottoclasseType.Attributi.Attributo.ValoriAmmessi getValoriAmmessi() {
                return valoriAmmessi;
            }

            /**
             * Imposta il valore della proprietà valoriAmmessi.
             * 
             * @param value
             *     allowed object is
             *     {@link ClasseSottoclasseType.Attributi.Attributo.ValoriAmmessi }
             *     
             */
            public void setValoriAmmessi(ClasseSottoclasseType.Attributi.Attributo.ValoriAmmessi value) {
                this.valoriAmmessi = value;
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
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="ValoreAmmesso" maxOccurs="unbounded">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="255"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
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
                "valoreAmmesso"
            })
            public static class ValoriAmmessi {

                @XmlElement(name = "ValoreAmmesso", required = true)
                protected List<String> valoreAmmesso;

                /**
                 * Gets the value of the valoreAmmesso property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the valoreAmmesso property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getValoreAmmesso().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getValoreAmmesso() {
                    if (valoreAmmesso == null) {
                        valoreAmmesso = new ArrayList<String>();
                    }
                    return this.valoreAmmesso;
                }

            }

        }

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
     *         &lt;element name="Tipologia" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="id" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;minLength value="1"/>
     *                       &lt;maxLength value="30"/>
     *                       &lt;whiteSpace value="replace"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="descrizione" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;minLength value="1"/>
     *                       &lt;maxLength value="300"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="valida" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
        "tipologia"
    })
    public static class Tipologie {

        @XmlElement(name = "Tipologia", required = true)
        protected List<ClasseSottoclasseType.Tipologie.Tipologia> tipologia;

        /**
         * Gets the value of the tipologia property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tipologia property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTipologia().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClasseSottoclasseType.Tipologie.Tipologia }
         * 
         * 
         */
        public List<ClasseSottoclasseType.Tipologie.Tipologia> getTipologia() {
            if (tipologia == null) {
                tipologia = new ArrayList<ClasseSottoclasseType.Tipologie.Tipologia>();
            }
            return this.tipologia;
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
         *       &lt;attribute name="id" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;minLength value="1"/>
         *             &lt;maxLength value="30"/>
         *             &lt;whiteSpace value="replace"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="descrizione" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;minLength value="1"/>
         *             &lt;maxLength value="300"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="valida" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Tipologia {

            @XmlAttribute(name = "id", required = true)
            protected String id;
            @XmlAttribute(name = "descrizione", required = true)
            protected String descrizione;
            @XmlAttribute(name = "valida", required = true)
            protected boolean valida;

            /**
             * Recupera il valore della proprietà id.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getId() {
                return id;
            }

            /**
             * Imposta il valore della proprietà id.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setId(String value) {
                this.id = value;
            }

            /**
             * Recupera il valore della proprietà descrizione.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescrizione() {
                return descrizione;
            }

            /**
             * Imposta il valore della proprietà descrizione.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescrizione(String value) {
                this.descrizione = value;
            }

            /**
             * Recupera il valore della proprietà valida.
             * 
             */
            public boolean isValida() {
                return valida;
            }

            /**
             * Imposta il valore della proprietà valida.
             * 
             */
            public void setValida(boolean value) {
                this.valida = value;
            }

        }

    }

}
