
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="categoryID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="addVersion" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "categoryID",
    "version",
    "addVersion"
})
@XmlRootElement(name = "UpgradeCategoryItemsContext")
public class UpgradeCategoryItemsContext {

    protected long categoryID;
    protected long version;
    protected boolean addVersion;

    /**
     * Recupera il valore della proprietà categoryID.
     * 
     */
    public long getCategoryID() {
        return categoryID;
    }

    /**
     * Imposta il valore della proprietà categoryID.
     * 
     */
    public void setCategoryID(long value) {
        this.categoryID = value;
    }

    /**
     * Recupera il valore della proprietà version.
     * 
     */
    public long getVersion() {
        return version;
    }

    /**
     * Imposta il valore della proprietà version.
     * 
     */
    public void setVersion(long value) {
        this.version = value;
    }

    /**
     * Recupera il valore della proprietà addVersion.
     * 
     */
    public boolean isAddVersion() {
        return addVersion;
    }

    /**
     * Imposta il valore della proprietà addVersion.
     * 
     */
    public void setAddVersion(boolean value) {
        this.addVersion = value;
    }

}
