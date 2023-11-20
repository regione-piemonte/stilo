/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine.
// Generato il: 2018.09.19 alle 10:17:30 AM CEST
//

package it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
 *       &lt;choice>
 *         &lt;element name="Errore" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="Codice" use="required" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}ErrorType" />
 *                 &lt;attribute name="Messaggio" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DatiRichiesta">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Oggetto">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="4000"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="AttiRichiesti">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="EstremiAtto" maxOccurs="unbounded">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;whiteSpace value="collapse"/>
 *                                   &lt;maxLength value="250"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="SediAppuntamento">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SedeAppuntamento" maxOccurs="unbounded">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;whiteSpace value="collapse"/>
 *                                   &lt;maxLength value="250"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="ID" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *                       &lt;minLength value="1"/>
 *                       &lt;whiteSpace value="collapse"/>
 *                       &lt;maxLength value="50"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *       &lt;attribute name="EsitoVerifica" use="required" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}ResultType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "errore", "datiRichiesta" })
@XmlRootElement(name = "ResponseVerificaRichiesta")
public class ResponseVerificaRichiesta {

	@Override
	public String toString() {
		return "ResponseVerificaRichiesta [errore=" + errore + ", datiRichiesta=" + datiRichiesta + ", esitoVerifica=" + esitoVerifica + "]";
	}

	@XmlElement(name = "Errore")
	protected ResponseVerificaRichiesta.Errore errore;
	@XmlElement(name = "DatiRichiesta")
	protected ResponseVerificaRichiesta.DatiRichiesta datiRichiesta;
	@XmlAttribute(name = "EsitoVerifica", required = true)
	protected ResultType esitoVerifica;

	/**
	 * Recupera il valore della proprietà errore.
	 * 
	 * @return possible object is {@link ResponseVerificaRichiesta.Errore }
	 * 
	 */
	public ResponseVerificaRichiesta.Errore getErrore() {
		return errore;
	}

	/**
	 * Imposta il valore della proprietà errore.
	 * 
	 * @param value
	 *            allowed object is {@link ResponseVerificaRichiesta.Errore }
	 * 
	 */
	public void setErrore(ResponseVerificaRichiesta.Errore value) {
		this.errore = value;
	}

	/**
	 * Recupera il valore della proprietà datiRichiesta.
	 * 
	 * @return possible object is {@link ResponseVerificaRichiesta.DatiRichiesta }
	 * 
	 */
	public ResponseVerificaRichiesta.DatiRichiesta getDatiRichiesta() {
		return datiRichiesta;
	}

	/**
	 * Imposta il valore della proprietà datiRichiesta.
	 * 
	 * @param value
	 *            allowed object is {@link ResponseVerificaRichiesta.DatiRichiesta }
	 * 
	 */
	public void setDatiRichiesta(ResponseVerificaRichiesta.DatiRichiesta value) {
		this.datiRichiesta = value;
	}

	/**
	 * Recupera il valore della proprietà esitoVerifica.
	 * 
	 * @return possible object is {@link ResultType }
	 * 
	 */
	public ResultType getEsitoVerifica() {
		return esitoVerifica;
	}

	/**
	 * Imposta il valore della proprietà esitoVerifica.
	 * 
	 * @param value
	 *            allowed object is {@link ResultType }
	 * 
	 */
	public void setEsitoVerifica(ResultType value) {
		this.esitoVerifica = value;
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
	 *         &lt;element name="Oggetto">
	 *           &lt;simpleType>
	 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *               &lt;maxLength value="4000"/>
	 *             &lt;/restriction>
	 *           &lt;/simpleType>
	 *         &lt;/element>
	 *         &lt;element name="AttiRichiesti">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="EstremiAtto" maxOccurs="unbounded">
	 *                     &lt;simpleType>
	 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *                         &lt;minLength value="1"/>
	 *                         &lt;whiteSpace value="collapse"/>
	 *                         &lt;maxLength value="250"/>
	 *                       &lt;/restriction>
	 *                     &lt;/simpleType>
	 *                   &lt;/element>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *         &lt;element name="SediAppuntamento">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="SedeAppuntamento" maxOccurs="unbounded">
	 *                     &lt;simpleType>
	 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
	 *                         &lt;minLength value="1"/>
	 *                         &lt;whiteSpace value="collapse"/>
	 *                         &lt;maxLength value="250"/>
	 *                       &lt;/restriction>
	 *                     &lt;/simpleType>
	 *                   &lt;/element>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *       &lt;attribute name="ID" use="required">
	 *         &lt;simpleType>
	 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
	 *             &lt;minLength value="1"/>
	 *             &lt;whiteSpace value="collapse"/>
	 *             &lt;maxLength value="50"/>
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
	@XmlType(name = "", propOrder = { "oggetto", "attiRichiesti", "sediAppuntamento" })
	public static class DatiRichiesta {

		@Override
		public String toString() {
			return "DatiRichiesta [oggetto=" + oggetto + ", attiRichiesti=" + attiRichiesti + ", sediAppuntamento=" + sediAppuntamento + ", id=" + id + "]";
		}

		@XmlElement(name = "Oggetto", required = true)
		protected String oggetto;
		@XmlElement(name = "AttiRichiesti", required = true)
		protected ResponseVerificaRichiesta.DatiRichiesta.AttiRichiesti attiRichiesti;
		@XmlElement(name = "SediAppuntamento", required = true)
		protected ResponseVerificaRichiesta.DatiRichiesta.SediAppuntamento sediAppuntamento;
		@XmlAttribute(name = "ID", required = true)
		@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
		protected String id;

		/**
		 * Recupera il valore della proprietà oggetto.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getOggetto() {
			return oggetto;
		}

		/**
		 * Imposta il valore della proprietà oggetto.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setOggetto(String value) {
			this.oggetto = value;
		}

		/**
		 * Recupera il valore della proprietà attiRichiesti.
		 * 
		 * @return possible object is {@link ResponseVerificaRichiesta.DatiRichiesta.AttiRichiesti }
		 * 
		 */
		public ResponseVerificaRichiesta.DatiRichiesta.AttiRichiesti getAttiRichiesti() {
			return attiRichiesti;
		}

		/**
		 * Imposta il valore della proprietà attiRichiesti.
		 * 
		 * @param value
		 *            allowed object is {@link ResponseVerificaRichiesta.DatiRichiesta.AttiRichiesti }
		 * 
		 */
		public void setAttiRichiesti(ResponseVerificaRichiesta.DatiRichiesta.AttiRichiesti value) {
			this.attiRichiesti = value;
		}

		/**
		 * Recupera il valore della proprietà sediAppuntamento.
		 * 
		 * @return possible object is {@link ResponseVerificaRichiesta.DatiRichiesta.SediAppuntamento }
		 * 
		 */
		public ResponseVerificaRichiesta.DatiRichiesta.SediAppuntamento getSediAppuntamento() {
			return sediAppuntamento;
		}

		/**
		 * Imposta il valore della proprietà sediAppuntamento.
		 * 
		 * @param value
		 *            allowed object is {@link ResponseVerificaRichiesta.DatiRichiesta.SediAppuntamento }
		 * 
		 */
		public void setSediAppuntamento(ResponseVerificaRichiesta.DatiRichiesta.SediAppuntamento value) {
			this.sediAppuntamento = value;
		}

		/**
		 * Recupera il valore della proprietà id.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getID() {
			return id;
		}

		/**
		 * Imposta il valore della proprietà id.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setID(String value) {
			this.id = value;
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
		 *         &lt;element name="EstremiAtto" maxOccurs="unbounded">
		 *           &lt;simpleType>
		 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
		 *               &lt;minLength value="1"/>
		 *               &lt;whiteSpace value="collapse"/>
		 *               &lt;maxLength value="250"/>
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
		@XmlType(name = "", propOrder = { "estremiAtto" })
		public static class AttiRichiesti {

			@Override
			public String toString() {
				return "AttiRichiesti [estremiAtto=" + estremiAtto + "]";
			}

			public AttiRichiesti(List<String> estremiAtto) {
				super();
				this.estremiAtto = estremiAtto;
			}

			public AttiRichiesti() {
				this.estremiAtto = null;
			}

			@XmlElement(name = "EstremiAtto", required = true)
			protected List<String> estremiAtto;

			/**
			 * Gets the value of the estremiAtto property.
			 * 
			 * <p>
			 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be
			 * present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the estremiAtto property.
			 * 
			 * <p>
			 * For example, to add a new item, do as follows:
			 * 
			 * <pre>
			 * getEstremiAtto().add(newItem);
			 * </pre>
			 * 
			 * 
			 * <p>
			 * Objects of the following type(s) are allowed in the list {@link String }
			 * 
			 * 
			 */
			public List<String> getEstremiAtto() {
				if (estremiAtto == null) {
					estremiAtto = new ArrayList<String>();
				}
				return this.estremiAtto;
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
		 *         &lt;element name="SedeAppuntamento" maxOccurs="unbounded">
		 *           &lt;simpleType>
		 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
		 *               &lt;minLength value="1"/>
		 *               &lt;whiteSpace value="collapse"/>
		 *               &lt;maxLength value="250"/>
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
		@XmlType(name = "", propOrder = { "sedeAppuntamento" })
		public static class SediAppuntamento {

			public SediAppuntamento(List<String> sedeAppuntamento) {
				super();
				this.sedeAppuntamento = sedeAppuntamento;
			}

			public SediAppuntamento() {
				this.sedeAppuntamento = null;
			}

			@Override
			public String toString() {
				return "SediAppuntamento [sedeAppuntamento=" + sedeAppuntamento + "]";
			}

			@XmlElement(name = "SedeAppuntamento", required = true)
			protected List<String> sedeAppuntamento;

			/**
			 * Gets the value of the sedeAppuntamento property.
			 * 
			 * <p>
			 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be
			 * present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the sedeAppuntamento property.
			 * 
			 * <p>
			 * For example, to add a new item, do as follows:
			 * 
			 * <pre>
			 * getSedeAppuntamento().add(newItem);
			 * </pre>
			 * 
			 * 
			 * <p>
			 * Objects of the following type(s) are allowed in the list {@link String }
			 * 
			 * 
			 */
			public List<String> getSedeAppuntamento() {
				if (sedeAppuntamento == null) {
					sedeAppuntamento = new ArrayList<String>();
				}
				return this.sedeAppuntamento;
			}

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
	 *       &lt;attribute name="Codice" use="required" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}ErrorType" />
	 *       &lt;attribute name="Messaggio" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class Errore {

		@Override
		public String toString() {
			return "Errore [codice=" + codice + ", messaggio=" + messaggio + "]";
		}

		@XmlAttribute(name = "Codice", required = true)
		protected ErrorType codice;
		@XmlAttribute(name = "Messaggio", required = true)
		protected String messaggio;

		/**
		 * Recupera il valore della proprietà codice.
		 * 
		 * @return possible object is {@link ErrorType }
		 * 
		 */
		public ErrorType getCodice() {
			return codice;
		}

		/**
		 * Imposta il valore della proprietà codice.
		 * 
		 * @param value
		 *            allowed object is {@link ErrorType }
		 * 
		 */
		public void setCodice(ErrorType value) {
			this.codice = value;
		}

		/**
		 * Recupera il valore della proprietà messaggio.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getMessaggio() {
			return messaggio;
		}

		/**
		 * Imposta il valore della proprietà messaggio.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setMessaggio(String value) {
			this.messaggio = value;
		}

	}

}
