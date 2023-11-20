/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.doqui.acta.acaris.common.CodiceFiscaleType;
import it.doqui.acta.acaris.common.IdAOOType;
import it.doqui.acta.acaris.common.IdNodoType;
import it.doqui.acta.acaris.common.IdStrutturaType;
import it.doqui.acta.acaris.common.ObjectIdType;


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
 *         &lt;element name="repositoryId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="idUtente" type="{common.acaris.acta.doqui.it}CodiceFiscaleType"/&gt;
 *         &lt;element name="idAOO" type="{common.acaris.acta.doqui.it}IdAOOType"/&gt;
 *         &lt;element name="idStruttura" type="{common.acaris.acta.doqui.it}IdStrutturaType" minOccurs="0"/&gt;
 *         &lt;element name="idNodo" type="{common.acaris.acta.doqui.it}IdNodoType" minOccurs="0"/&gt;
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
    "repositoryId",
    "idUtente",
    "idAOO",
    "idStruttura",
    "idNodo"
})
@XmlRootElement(name = "getPrincipal")
public class GetPrincipal {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected CodiceFiscaleType idUtente;
    @XmlElement(required = true)
    protected IdAOOType idAOO;
    protected IdStrutturaType idStruttura;
    protected IdNodoType idNodo;

    /**
     * Recupera il valore della proprietà repositoryId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getRepositoryId() {
        return repositoryId;
    }

    /**
     * Imposta il valore della proprietà repositoryId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setRepositoryId(ObjectIdType value) {
        this.repositoryId = value;
    }

    /**
     * Recupera il valore della proprietà idUtente.
     * 
     * @return
     *     possible object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public CodiceFiscaleType getIdUtente() {
        return idUtente;
    }

    /**
     * Imposta il valore della proprietà idUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public void setIdUtente(CodiceFiscaleType value) {
        this.idUtente = value;
    }

    /**
     * Recupera il valore della proprietà idAOO.
     * 
     * @return
     *     possible object is
     *     {@link IdAOOType }
     *     
     */
    public IdAOOType getIdAOO() {
        return idAOO;
    }

    /**
     * Imposta il valore della proprietà idAOO.
     * 
     * @param value
     *     allowed object is
     *     {@link IdAOOType }
     *     
     */
    public void setIdAOO(IdAOOType value) {
        this.idAOO = value;
    }

    /**
     * Recupera il valore della proprietà idStruttura.
     * 
     * @return
     *     possible object is
     *     {@link IdStrutturaType }
     *     
     */
    public IdStrutturaType getIdStruttura() {
        return idStruttura;
    }

    /**
     * Imposta il valore della proprietà idStruttura.
     * 
     * @param value
     *     allowed object is
     *     {@link IdStrutturaType }
     *     
     */
    public void setIdStruttura(IdStrutturaType value) {
        this.idStruttura = value;
    }

    /**
     * Recupera il valore della proprietà idNodo.
     * 
     * @return
     *     possible object is
     *     {@link IdNodoType }
     *     
     */
    public IdNodoType getIdNodo() {
        return idNodo;
    }

    /**
     * Imposta il valore della proprietà idNodo.
     * 
     * @param value
     *     allowed object is
     *     {@link IdNodoType }
     *     
     */
    public void setIdNodo(IdNodoType value) {
        this.idNodo = value;
    }

}
