/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.doqui.acta.acaris.common.IdAOOType;
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
 *         &lt;element name="idAOO" type="{common.acaris.acta.doqui.it}IdAOOType"/&gt;
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
    "idAOO"
})
@XmlRootElement(name = "getDettaglioAOO")
public class GetDettaglioAOO {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected IdAOOType idAOO;

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

}
