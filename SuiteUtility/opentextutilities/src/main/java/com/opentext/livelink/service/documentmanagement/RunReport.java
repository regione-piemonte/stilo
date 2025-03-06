
package com.opentext.livelink.service.documentmanagement;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="reportID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="inputs" type="{urn:Core.service.livelink.opentext.com}DataValue" maxOccurs="unbounded" minOccurs="0"/>
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
    "reportID",
    "inputs"
})
@XmlRootElement(name = "RunReport")
public class RunReport {

    protected long reportID;
    @XmlElement(nillable = true)
    protected List<DataValue> inputs;

    /**
     * Recupera il valore della proprietà reportID.
     * 
     */
    public long getReportID() {
        return reportID;
    }

    /**
     * Imposta il valore della proprietà reportID.
     * 
     */
    public void setReportID(long value) {
        this.reportID = value;
    }

    /**
     * Gets the value of the inputs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataValue }
     * 
     * 
     */
    public List<DataValue> getInputs() {
        if (inputs == null) {
            inputs = new ArrayList<DataValue>();
        }
        return this.inputs;
    }

}
