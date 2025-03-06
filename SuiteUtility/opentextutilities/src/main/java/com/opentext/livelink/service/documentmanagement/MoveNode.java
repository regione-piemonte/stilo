
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="parentID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="newName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moveOptions" type="{urn:DocMan.service.livelink.opentext.com}MoveOptions" minOccurs="0"/>
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
    "id",
    "parentID",
    "newName",
    "moveOptions"
})
@XmlRootElement(name = "MoveNode")
public class MoveNode {

    @XmlElement(name = "ID")
    protected long id;
    protected long parentID;
    protected String newName;
    protected MoveOptions moveOptions;

    /**
     * Recupera il valore della proprietà id.
     * 
     */
    public long getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     */
    public void setID(long value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà parentID.
     * 
     */
    public long getParentID() {
        return parentID;
    }

    /**
     * Imposta il valore della proprietà parentID.
     * 
     */
    public void setParentID(long value) {
        this.parentID = value;
    }

    /**
     * Recupera il valore della proprietà newName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewName() {
        return newName;
    }

    /**
     * Imposta il valore della proprietà newName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewName(String value) {
        this.newName = value;
    }

    /**
     * Recupera il valore della proprietà moveOptions.
     * 
     * @return
     *     possible object is
     *     {@link MoveOptions }
     *     
     */
    public MoveOptions getMoveOptions() {
        return moveOptions;
    }

    /**
     * Imposta il valore della proprietà moveOptions.
     * 
     * @param value
     *     allowed object is
     *     {@link MoveOptions }
     *     
     */
    public void setMoveOptions(MoveOptions value) {
        this.moveOptions = value;
    }

}
