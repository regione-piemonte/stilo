/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.09.19 alle 10:17:30 AM CEST 
//


package it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;sequence minOccurs="0">
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
 *       &lt;/sequence>
 *       &lt;attribute name="EsitoAggiornamento" use="required" type="{http://prenotaappuntamento.webservices.repository2.auriga.eng.it}ResultType" />
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
@XmlRootElement(name = "ResponseAggiornaRichiesta")
public class ResponseAggiornaRichiesta {

    @Override
	public String toString() {
		return "ResponseAggiornaRichiesta [errore=" + errore + ", esitoAggiornamento=" + esitoAggiornamento + "]";
	}


	@XmlElement(name = "Errore")
    protected ResponseAggiornaRichiesta.Errore errore;
    @XmlAttribute(name = "EsitoAggiornamento", required = true)
    protected ResultType esitoAggiornamento;

    /**
     * Recupera il valore della proprietà errore.
     * 
     * @return
     *     possible object is
     *     {@link ResponseAggiornaRichiesta.Errore }
     *     
     */
    public ResponseAggiornaRichiesta.Errore getErrore() {
        return errore;
    }

    /**
     * Imposta il valore della proprietà errore.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseAggiornaRichiesta.Errore }
     *     
     */
    public void setErrore(ResponseAggiornaRichiesta.Errore value) {
        this.errore = value;
    }

    /**
     * Recupera il valore della proprietà esitoAggiornamento.
     * 
     * @return
     *     possible object is
     *     {@link ResultType }
     *     
     */
    public ResultType getEsitoAggiornamento() {
        return esitoAggiornamento;
    }

    /**
     * Imposta il valore della proprietà esitoAggiornamento.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultType }
     *     
     */
    public void setEsitoAggiornamento(ResultType value) {
        this.esitoAggiornamento = value;
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
         * Recupera il valore della proprietà messaggio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessaggio() {
            return messaggio;
        }

        /**
         * Imposta il valore della proprietà messaggio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessaggio(String value) {
            this.messaggio = value;
        }

    }

}
