
package it.eng.hsm.client.medas.syncsign.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeCertificate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeCertificate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="automatic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hsmCerts" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeHsmCert" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="processes" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeProcess" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeCertificate", propOrder = {
    "automatic",
    "hsmCerts",
    "processes"
})
public class TypeCertificate {

    protected boolean automatic;
    @XmlElement(nillable = true)
    protected List<TypeHsmCert> hsmCerts;
    @XmlElement(nillable = true)
    protected List<TypeProcess> processes;

    /**
     * Gets the value of the automatic property.
     * 
     */
    public boolean isAutomatic() {
        return automatic;
    }

    /**
     * Sets the value of the automatic property.
     * 
     */
    public void setAutomatic(boolean value) {
        this.automatic = value;
    }

    /**
     * Gets the value of the hsmCerts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hsmCerts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHsmCerts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeHsmCert }
     * 
     * 
     */
    public List<TypeHsmCert> getHsmCerts() {
        if (hsmCerts == null) {
            hsmCerts = new ArrayList<TypeHsmCert>();
        }
        return this.hsmCerts;
    }

    /**
     * Gets the value of the processes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the processes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcesses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeProcess }
     * 
     * 
     */
    public List<TypeProcess> getProcesses() {
        if (processes == null) {
            processes = new ArrayList<TypeProcess>();
        }
        return this.processes;
    }

}
