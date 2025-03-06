
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CategoryInheritance complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CategoryInheritance">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="CategoryID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Inheritance" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryInheritance", propOrder = {
    "categoryID",
    "inheritance"
})
public class CategoryInheritance
    extends ServiceDataObject
{

    @XmlElement(name = "CategoryID")
    protected long categoryID;
    @XmlElement(name = "Inheritance")
    protected boolean inheritance;

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
     * Recupera il valore della proprietà inheritance.
     * 
     */
    public boolean isInheritance() {
        return inheritance;
    }

    /**
     * Imposta il valore della proprietà inheritance.
     * 
     */
    public void setInheritance(boolean value) {
        this.inheritance = value;
    }

}
