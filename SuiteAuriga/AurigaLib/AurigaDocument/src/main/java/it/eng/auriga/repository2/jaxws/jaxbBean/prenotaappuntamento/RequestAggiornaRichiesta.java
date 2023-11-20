/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine.
// Generato il: 2018.09.19 alle 10:17:30 AM CEST
//

package it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Classe Java per anonymous complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}gYear"/>
 *         &lt;element name="Nro">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;totalDigits value="7"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Appuntamento">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Richiedente">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Cognome">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="250"/>
 *                                   &lt;minLength value="1"/>
 *                                   &lt;whiteSpace value="collapse"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Nome">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="250"/>
 *                                   &lt;whiteSpace value="collapse"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="UserID" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="100"/>
 *                                   &lt;whiteSpace value="collapse"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="CodFiscale" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}CodiceFiscalePFType" minOccurs="0"/>
 *                             &lt;element name="Email" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}EmailType" minOccurs="0"/>
 *                             &lt;element name="Tel" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}TelFaxType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Delegante" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Cognome_Denominazione" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="250"/>
 *                                   &lt;minLength value="1"/>
 *                                   &lt;whiteSpace value="collapse"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Nome" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="250"/>
 *                                   &lt;whiteSpace value="collapse"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="CodFiscale" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}CodiceFiscaleType" minOccurs="0"/>
 *                             &lt;element name="Email" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}EmailType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="NominativoPresenza" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Cognome">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="250"/>
 *                                   &lt;minLength value="1"/>
 *                                   &lt;whiteSpace value="collapse"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Nome">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="250"/>
 *                                   &lt;whiteSpace value="collapse"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="CodFiscale" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}CodiceFiscalePFType" minOccurs="0"/>
 *                             &lt;element name="Email" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}EmailType" minOccurs="0"/>
 *                             &lt;element name="Tel" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}TelFaxType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="DaAnnullare" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="SedeAppuntamento">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="100"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="UID">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="100"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="AppCode">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                       &lt;maxLength value="100"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="DataOra" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
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
@XmlType(name = "", propOrder = { "anno", "nro", "appuntamento" })
@XmlRootElement(name = "RequestAggiornaRichiesta")
public class RequestAggiornaRichiesta {

	@Override
	public String toString() {
		return "RequestAggiornaRichiesta [anno=" + anno + ", nro=" + nro + ", appuntamento=" + appuntamento + "]";
	}

	@XmlElement(name = "Anno", required = true)
	@XmlSchemaType(name = "gYear")
	protected XMLGregorianCalendar anno;
	@XmlElement(name = "Nro", required = true)
	protected BigInteger nro;
	@XmlElement(name = "Appuntamento", required = true)
	protected RequestAggiornaRichiesta.Appuntamento appuntamento;

	/**
	 * Recupera il valore della proprietà anno.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getAnno() {
		return anno;
	}

	/**
	 * Imposta il valore della proprietà anno.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setAnno(XMLGregorianCalendar value) {
		this.anno = value;
	}

	/**
	 * Recupera il valore della proprietà nro.
	 * 
	 * @return possible object is {@link BigInteger }
	 * 
	 */
	public BigInteger getNro() {
		return nro;
	}

	/**
	 * Imposta il valore della proprietà nro.
	 * 
	 * @param value
	 *            allowed object is {@link BigInteger }
	 * 
	 */
	public void setNro(BigInteger value) {
		this.nro = value;
	}

	/**
	 * Recupera il valore della proprietà appuntamento.
	 * 
	 * @return possible object is {@link RequestAggiornaRichiesta.Appuntamento }
	 * 
	 */
	public RequestAggiornaRichiesta.Appuntamento getAppuntamento() {
		return appuntamento;
	}

	/**
	 * Imposta il valore della proprietà appuntamento.
	 * 
	 * @param value
	 *            allowed object is {@link RequestAggiornaRichiesta.Appuntamento }
	 * 
	 */
	public void setAppuntamento(RequestAggiornaRichiesta.Appuntamento value) {
		this.appuntamento = value;
	}

	/**
	 * <p>
	 * Classe Java per anonymous complex type.
	 * 
	 * <p>
	 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="Richiedente">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="Cognome">
	 *                     &lt;simpleType>
	 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *                         &lt;maxLength value="250"/>
	 *                         &lt;minLength value="1"/>
	 *                         &lt;whiteSpace value="collapse"/>
	 *                       &lt;/restriction>
	 *                     &lt;/simpleType>
	 *                   &lt;/element>
	 *                   &lt;element name="Nome">
	 *                     &lt;simpleType>
	 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *                         &lt;minLength value="1"/>
	 *                         &lt;maxLength value="250"/>
	 *                         &lt;whiteSpace value="collapse"/>
	 *                       &lt;/restriction>
	 *                     &lt;/simpleType>
	 *                   &lt;/element>
	 *                   &lt;element name="UserID" minOccurs="0">
	 *                     &lt;simpleType>
	 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *                         &lt;minLength value="1"/>
	 *                         &lt;maxLength value="100"/>
	 *                         &lt;whiteSpace value="collapse"/>
	 *                       &lt;/restriction>
	 *                     &lt;/simpleType>
	 *                   &lt;/element>
	 *                   &lt;element name="CodFiscale" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}CodiceFiscalePFType" minOccurs="0"/>
	 *                   &lt;element name="Email" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}EmailType" minOccurs="0"/>
	 *                   &lt;element name="Tel" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}TelFaxType" minOccurs="0"/>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *         &lt;element name="Delegante" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="Cognome_Denominazione" minOccurs="0">
	 *                     &lt;simpleType>
	 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *                         &lt;maxLength value="250"/>
	 *                         &lt;minLength value="1"/>
	 *                         &lt;whiteSpace value="collapse"/>
	 *                       &lt;/restriction>
	 *                     &lt;/simpleType>
	 *                   &lt;/element>
	 *                   &lt;element name="Nome" minOccurs="0">
	 *                     &lt;simpleType>
	 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *                         &lt;minLength value="1"/>
	 *                         &lt;maxLength value="250"/>
	 *                         &lt;whiteSpace value="collapse"/>
	 *                       &lt;/restriction>
	 *                     &lt;/simpleType>
	 *                   &lt;/element>
	 *                   &lt;element name="CodFiscale" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}CodiceFiscaleType" minOccurs="0"/>
	 *                   &lt;element name="Email" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}EmailType" minOccurs="0"/>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *         &lt;element name="NominativoPresenza" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="Cognome">
	 *                     &lt;simpleType>
	 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *                         &lt;maxLength value="250"/>
	 *                         &lt;minLength value="1"/>
	 *                         &lt;whiteSpace value="collapse"/>
	 *                       &lt;/restriction>
	 *                     &lt;/simpleType>
	 *                   &lt;/element>
	 *                   &lt;element name="Nome">
	 *                     &lt;simpleType>
	 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *                         &lt;minLength value="1"/>
	 *                         &lt;maxLength value="250"/>
	 *                         &lt;whiteSpace value="collapse"/>
	 *                       &lt;/restriction>
	 *                     &lt;/simpleType>
	 *                   &lt;/element>
	 *                   &lt;element name="CodFiscale" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}CodiceFiscalePFType" minOccurs="0"/>
	 *                   &lt;element name="Email" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}EmailType" minOccurs="0"/>
	 *                   &lt;element name="Tel" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}TelFaxType" minOccurs="0"/>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *       &lt;attribute name="DaAnnullare" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
	 *       &lt;attribute name="SedeAppuntamento">
	 *         &lt;simpleType>
	 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *             &lt;minLength value="1"/>
	 *             &lt;maxLength value="100"/>
	 *             &lt;whiteSpace value="collapse"/>
	 *           &lt;/restriction>
	 *         &lt;/simpleType>
	 *       &lt;/attribute>
	 *       &lt;attribute name="UID">
	 *         &lt;simpleType>
	 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *             &lt;minLength value="1"/>
	 *             &lt;maxLength value="100"/>
	 *             &lt;whiteSpace value="collapse"/>
	 *           &lt;/restriction>
	 *         &lt;/simpleType>
	 *       &lt;/attribute>
	 *       &lt;attribute name="AppCode">
	 *         &lt;simpleType>
	 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *             &lt;minLength value="1"/>
	 *             &lt;maxLength value="100"/>
	 *             &lt;whiteSpace value="collapse"/>
	 *           &lt;/restriction>
	 *         &lt;/simpleType>
	 *       &lt;/attribute>
	 *       &lt;attribute name="DataOra" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "richiedente", "delegante", "nominativoPresenza" })
	public static class Appuntamento {

		@Override
		public String toString() {
			return "Appuntamento [richiedente=" + richiedente + ", delegante=" + delegante + ", nominativoPresenza=" + nominativoPresenza + ", daAnnullare="
					+ daAnnullare + ", sedeAppuntamento=" + sedeAppuntamento + ", uid=" + uid + ", appCode=" + appCode + ", dataOra=" + dataOra + "]";
		}

		@XmlElement(name = "Richiedente", required = true)
		protected RequestAggiornaRichiesta.Appuntamento.Richiedente richiedente;
		@XmlElement(name = "Delegante")
		protected RequestAggiornaRichiesta.Appuntamento.Delegante delegante;
		@XmlElement(name = "NominativoPresenza")
		protected RequestAggiornaRichiesta.Appuntamento.NominativoPresenza nominativoPresenza;
		@XmlAttribute(name = "DaAnnullare", required = true)
		protected boolean daAnnullare;
		@XmlAttribute(name = "SedeAppuntamento")
		protected String sedeAppuntamento;
		@XmlAttribute(name = "UID")
		protected String uid;
		@XmlAttribute(name = "AppCode")
		protected String appCode;
		@XmlAttribute(name = "DataOra")
		@XmlSchemaType(name = "dateTime")
		protected XMLGregorianCalendar dataOra;

		/**
		 * Recupera il valore della proprietà richiedente.
		 * 
		 * @return possible object is {@link RequestAggiornaRichiesta.Appuntamento.Richiedente }
		 * 
		 */
		public RequestAggiornaRichiesta.Appuntamento.Richiedente getRichiedente() {
			return richiedente;
		}

		/**
		 * Imposta il valore della proprietà richiedente.
		 * 
		 * @param value
		 *            allowed object is {@link RequestAggiornaRichiesta.Appuntamento.Richiedente }
		 * 
		 */
		public void setRichiedente(RequestAggiornaRichiesta.Appuntamento.Richiedente value) {
			this.richiedente = value;
		}

		/**
		 * Recupera il valore della proprietà delegante.
		 * 
		 * @return possible object is {@link RequestAggiornaRichiesta.Appuntamento.Delegante }
		 * 
		 */
		public RequestAggiornaRichiesta.Appuntamento.Delegante getDelegante() {
			return delegante;
		}

		/**
		 * Imposta il valore della proprietà delegante.
		 * 
		 * @param value
		 *            allowed object is {@link RequestAggiornaRichiesta.Appuntamento.Delegante }
		 * 
		 */
		public void setDelegante(RequestAggiornaRichiesta.Appuntamento.Delegante value) {
			this.delegante = value;
		}

		/**
		 * Recupera il valore della proprietà nominativoPresenza.
		 * 
		 * @return possible object is {@link RequestAggiornaRichiesta.Appuntamento.NominativoPresenza }
		 * 
		 */
		public RequestAggiornaRichiesta.Appuntamento.NominativoPresenza getNominativoPresenza() {
			return nominativoPresenza;
		}

		/**
		 * Imposta il valore della proprietà nominativoPresenza.
		 * 
		 * @param value
		 *            allowed object is {@link RequestAggiornaRichiesta.Appuntamento.NominativoPresenza }
		 * 
		 */
		public void setNominativoPresenza(RequestAggiornaRichiesta.Appuntamento.NominativoPresenza value) {
			this.nominativoPresenza = value;
		}

		/**
		 * Recupera il valore della proprietà daAnnullare.
		 * 
		 */
		public boolean isDaAnnullare() {
			return daAnnullare;
		}

		/**
		 * Imposta il valore della proprietà daAnnullare.
		 * 
		 */
		public void setDaAnnullare(boolean value) {
			this.daAnnullare = value;
		}

		/**
		 * Recupera il valore della proprietà sedeAppuntamento.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getSedeAppuntamento() {
			return sedeAppuntamento;
		}

		/**
		 * Imposta il valore della proprietà sedeAppuntamento.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setSedeAppuntamento(String value) {
			this.sedeAppuntamento = value;
		}

		/**
		 * Recupera il valore della proprietà uid.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getUID() {
			return uid;
		}

		/**
		 * Imposta il valore della proprietà uid.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setUID(String value) {
			this.uid = value;
		}

		/**
		 * Recupera il valore della proprietà appCode.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getAppCode() {
			return appCode;
		}

		/**
		 * Imposta il valore della proprietà appCode.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setAppCode(String value) {
			this.appCode = value;
		}

		/**
		 * Recupera il valore della proprietà dataOra.
		 * 
		 * @return possible object is {@link XMLGregorianCalendar }
		 * 
		 */
		public XMLGregorianCalendar getDataOra() {
			return dataOra;
		}

		/**
		 * Imposta il valore della proprietà dataOra.
		 * 
		 * @param value
		 *            allowed object is {@link XMLGregorianCalendar }
		 * 
		 */
		public void setDataOra(XMLGregorianCalendar value) {
			this.dataOra = value;
		}

		/**
		 * <p>
		 * Classe Java per anonymous complex type.
		 * 
		 * <p>
		 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="Cognome_Denominazione" minOccurs="0">
		 *           &lt;simpleType>
		 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
		 *               &lt;maxLength value="250"/>
		 *               &lt;minLength value="1"/>
		 *               &lt;whiteSpace value="collapse"/>
		 *             &lt;/restriction>
		 *           &lt;/simpleType>
		 *         &lt;/element>
		 *         &lt;element name="Nome" minOccurs="0">
		 *           &lt;simpleType>
		 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
		 *               &lt;minLength value="1"/>
		 *               &lt;maxLength value="250"/>
		 *               &lt;whiteSpace value="collapse"/>
		 *             &lt;/restriction>
		 *           &lt;/simpleType>
		 *         &lt;/element>
		 *         &lt;element name="CodFiscale" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}CodiceFiscaleType" minOccurs="0"/>
		 *         &lt;element name="Email" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}EmailType" minOccurs="0"/>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "cognomeDenominazione", "nome", "codFiscale", "email" })
		public static class Delegante {

			@Override
			public String toString() {
				return "Delegante [cognomeDenominazione=" + cognomeDenominazione + ", nome=" + nome + ", codFiscale=" + codFiscale + ", email=" + email + "]";
			}

			@XmlElement(name = "Cognome_Denominazione")
			protected String cognomeDenominazione;
			@XmlElement(name = "Nome")
			protected String nome;
			@XmlElement(name = "CodFiscale")
			protected String codFiscale;
			@XmlElement(name = "Email")
			protected String email;

			/**
			 * Recupera il valore della proprietà cognomeDenominazione.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getCognomeDenominazione() {
				return cognomeDenominazione;
			}

			/**
			 * Imposta il valore della proprietà cognomeDenominazione.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setCognomeDenominazione(String value) {
				this.cognomeDenominazione = value;
			}

			/**
			 * Recupera il valore della proprietà nome.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getNome() {
				return nome;
			}

			/**
			 * Imposta il valore della proprietà nome.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setNome(String value) {
				this.nome = value;
			}

			/**
			 * Recupera il valore della proprietà codFiscale.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getCodFiscale() {
				return codFiscale;
			}

			/**
			 * Imposta il valore della proprietà codFiscale.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setCodFiscale(String value) {
				this.codFiscale = value;
			}

			/**
			 * Recupera il valore della proprietà email.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getEmail() {
				return email;
			}

			/**
			 * Imposta il valore della proprietà email.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setEmail(String value) {
				this.email = value;
			}

		}

		/**
		 * <p>
		 * Classe Java per anonymous complex type.
		 * 
		 * <p>
		 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="Cognome">
		 *           &lt;simpleType>
		 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
		 *               &lt;maxLength value="250"/>
		 *               &lt;minLength value="1"/>
		 *               &lt;whiteSpace value="collapse"/>
		 *             &lt;/restriction>
		 *           &lt;/simpleType>
		 *         &lt;/element>
		 *         &lt;element name="Nome">
		 *           &lt;simpleType>
		 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
		 *               &lt;minLength value="1"/>
		 *               &lt;maxLength value="250"/>
		 *               &lt;whiteSpace value="collapse"/>
		 *             &lt;/restriction>
		 *           &lt;/simpleType>
		 *         &lt;/element>
		 *         &lt;element name="CodFiscale" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}CodiceFiscalePFType" minOccurs="0"/>
		 *         &lt;element name="Email" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}EmailType" minOccurs="0"/>
		 *         &lt;element name="Tel" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}TelFaxType" minOccurs="0"/>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "cognome", "nome", "codFiscale", "email", "tel" })
		public static class NominativoPresenza {

			@Override
			public String toString() {
				return "NominativoPresenza [cognome=" + cognome + ", nome=" + nome + ", codFiscale=" + codFiscale + ", email=" + email + ", tel=" + tel + "]";
			}

			@XmlElement(name = "Cognome", required = true)
			protected String cognome;
			@XmlElement(name = "Nome", required = true)
			protected String nome;
			@XmlElement(name = "CodFiscale")
			protected String codFiscale;
			@XmlElement(name = "Email")
			protected String email;
			@XmlElement(name = "Tel")
			@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
			@XmlSchemaType(name = "normalizedString")
			protected String tel;

			/**
			 * Recupera il valore della proprietà cognome.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getCognome() {
				return cognome;
			}

			/**
			 * Imposta il valore della proprietà cognome.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setCognome(String value) {
				this.cognome = value;
			}

			/**
			 * Recupera il valore della proprietà nome.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getNome() {
				return nome;
			}

			/**
			 * Imposta il valore della proprietà nome.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setNome(String value) {
				this.nome = value;
			}

			/**
			 * Recupera il valore della proprietà codFiscale.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getCodFiscale() {
				return codFiscale;
			}

			/**
			 * Imposta il valore della proprietà codFiscale.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setCodFiscale(String value) {
				this.codFiscale = value;
			}

			/**
			 * Recupera il valore della proprietà email.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getEmail() {
				return email;
			}

			/**
			 * Imposta il valore della proprietà email.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setEmail(String value) {
				this.email = value;
			}

			/**
			 * Recupera il valore della proprietà tel.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getTel() {
				return tel;
			}

			/**
			 * Imposta il valore della proprietà tel.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setTel(String value) {
				this.tel = value;
			}

		}

		/**
		 * <p>
		 * Classe Java per anonymous complex type.
		 * 
		 * <p>
		 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="Cognome">
		 *           &lt;simpleType>
		 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
		 *               &lt;maxLength value="250"/>
		 *               &lt;minLength value="1"/>
		 *               &lt;whiteSpace value="collapse"/>
		 *             &lt;/restriction>
		 *           &lt;/simpleType>
		 *         &lt;/element>
		 *         &lt;element name="Nome">
		 *           &lt;simpleType>
		 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
		 *               &lt;minLength value="1"/>
		 *               &lt;maxLength value="250"/>
		 *               &lt;whiteSpace value="collapse"/>
		 *             &lt;/restriction>
		 *           &lt;/simpleType>
		 *         &lt;/element>
		 *         &lt;element name="UserID" minOccurs="0">
		 *           &lt;simpleType>
		 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
		 *               &lt;minLength value="1"/>
		 *               &lt;maxLength value="100"/>
		 *               &lt;whiteSpace value="collapse"/>
		 *             &lt;/restriction>
		 *           &lt;/simpleType>
		 *         &lt;/element>
		 *         &lt;element name="CodFiscale" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}CodiceFiscalePFType" minOccurs="0"/>
		 *         &lt;element name="Email" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}EmailType" minOccurs="0"/>
		 *         &lt;element name="Tel" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}TelFaxType" minOccurs="0"/>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "cognome", "nome", "userID", "codFiscale", "email", "tel" })
		public static class Richiedente {

			@Override
			public String toString() {
				return "Richiedente [cognome=" + cognome + ", nome=" + nome + ", userID=" + userID + ", codFiscale=" + codFiscale + ", email=" + email
						+ ", tel=" + tel + "]";
			}

			@XmlElement(name = "Cognome", required = true)
			protected String cognome;
			@XmlElement(name = "Nome", required = true)
			protected String nome;
			@XmlElement(name = "UserID")
			protected String userID;
			@XmlElement(name = "CodFiscale")
			protected String codFiscale;
			@XmlElement(name = "Email")
			protected String email;
			@XmlElement(name = "Tel")
			@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
			@XmlSchemaType(name = "normalizedString")
			protected String tel;

			/**
			 * Recupera il valore della proprietà cognome.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getCognome() {
				return cognome;
			}

			/**
			 * Imposta il valore della proprietà cognome.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setCognome(String value) {
				this.cognome = value;
			}

			/**
			 * Recupera il valore della proprietà nome.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getNome() {
				return nome;
			}

			/**
			 * Imposta il valore della proprietà nome.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setNome(String value) {
				this.nome = value;
			}

			/**
			 * Recupera il valore della proprietà userID.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getUserID() {
				return userID;
			}

			/**
			 * Imposta il valore della proprietà userID.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setUserID(String value) {
				this.userID = value;
			}

			/**
			 * Recupera il valore della proprietà codFiscale.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getCodFiscale() {
				return codFiscale;
			}

			/**
			 * Imposta il valore della proprietà codFiscale.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setCodFiscale(String value) {
				this.codFiscale = value;
			}

			/**
			 * Recupera il valore della proprietà email.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getEmail() {
				return email;
			}

			/**
			 * Imposta il valore della proprietà email.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setEmail(String value) {
				this.email = value;
			}

			/**
			 * Recupera il valore della proprietà tel.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getTel() {
				return tel;
			}

			/**
			 * Imposta il valore della proprietà tel.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setTel(String value) {
				this.tel = value;
			}

		}

	}

}
