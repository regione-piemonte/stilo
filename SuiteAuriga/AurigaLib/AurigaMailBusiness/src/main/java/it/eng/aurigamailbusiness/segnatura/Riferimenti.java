/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.12 at 12:00:07 PM CEST 
//


package it.eng.aurigamailbusiness.segnatura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Riferimenti complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Riferimenti">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}Messaggio"/>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}ContestoProcedurale"/>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}Procedimento"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Riferimenti", propOrder = {
    "messaggioOrContestoProceduraleOrProcedimento"
})
public class Riferimenti {

    @XmlElements({
        @XmlElement(name = "Messaggio", type = Messaggio.class),
        @XmlElement(name = "ContestoProcedurale", type = ContestoProcedurale.class),
        @XmlElement(name = "Procedimento", type = Procedimento.class)
    })
    protected List<Object> messaggioOrContestoProceduraleOrProcedimento;

    /**
     * Gets the value of the messaggioOrContestoProceduraleOrProcedimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messaggioOrContestoProceduraleOrProcedimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessaggioOrContestoProceduraleOrProcedimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Messaggio }
     * {@link ContestoProcedurale }
     * {@link Procedimento }
     * 
     * 
     */
    public List<Object> getMessaggioOrContestoProceduraleOrProcedimento() {
        if (messaggioOrContestoProceduraleOrProcedimento == null) {
            messaggioOrContestoProceduraleOrProcedimento = new ArrayList<Object>();
        }
        return this.messaggioOrContestoProceduraleOrProcedimento;
    }

}
