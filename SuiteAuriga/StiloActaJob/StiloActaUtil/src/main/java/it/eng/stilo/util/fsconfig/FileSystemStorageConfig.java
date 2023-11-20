/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.01.22 alle 10:03:14 AM CET 
//


package it.eng.stilo.util.fsconfig;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="baseFolderPath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="repositoryLocations" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tempRepositoryBasePath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "baseFolderPath",
    "repositoryLocations",
    "tempRepositoryBasePath"
})
@XmlRootElement(name = "fileSystemStorageConfig")
public class FileSystemStorageConfig {

    @XmlElement(required = true)
    protected String baseFolderPath;
    @XmlElement(required = true)
    protected String repositoryLocations;
    @XmlElement(required = true)
    protected String tempRepositoryBasePath;

    /**
     * Recupera il valore della propriet� baseFolderPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseFolderPath() {
        return baseFolderPath;
    }

    /**
     * Imposta il valore della propriet� baseFolderPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseFolderPath(String value) {
        this.baseFolderPath = value;
    }

    /**
     * Recupera il valore della propriet� repositoryLocations.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepositoryLocations() {
        return repositoryLocations;
    }

    /**
     * Imposta il valore della propriet� repositoryLocations.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryLocations(String value) {
        this.repositoryLocations = value;
    }

    /**
     * Recupera il valore della propriet� tempRepositoryBasePath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTempRepositoryBasePath() {
        return tempRepositoryBasePath;
    }

    /**
     * Imposta il valore della propriet� tempRepositoryBasePath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTempRepositoryBasePath(String value) {
        this.tempRepositoryBasePath = value;
    }

}
