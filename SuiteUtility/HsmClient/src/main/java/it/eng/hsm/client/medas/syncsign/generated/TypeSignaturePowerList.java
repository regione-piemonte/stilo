
package it.eng.hsm.client.medas.syncsign.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSignaturePowerList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSignaturePowerList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signaturePower" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSignaturePower" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSignaturePowerList", propOrder = {
    "signaturePower"
})
public class TypeSignaturePowerList {

    @XmlElement(nillable = true)
    protected List<TypeSignaturePower> signaturePower;

    /**
     * Gets the value of the signaturePower property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signaturePower property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignaturePower().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeSignaturePower }
     * 
     * 
     */
    public List<TypeSignaturePower> getSignaturePower() {
        if (signaturePower == null) {
            signaturePower = new ArrayList<TypeSignaturePower>();
        }
        return this.signaturePower;
    }

}
