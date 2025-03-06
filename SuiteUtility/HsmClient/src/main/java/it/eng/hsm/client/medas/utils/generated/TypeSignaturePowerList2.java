
package it.eng.hsm.client.medas.utils.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeSignaturePowerList2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeSignaturePowerList2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="signaturePower" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeSignaturePower2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeSignaturePowerList2", propOrder = {
    "signaturePower"
})
public class TypeSignaturePowerList2 {

    protected List<TypeSignaturePower2> signaturePower;

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
     * {@link TypeSignaturePower2 }
     * 
     * 
     */
    public List<TypeSignaturePower2> getSignaturePower() {
        if (signaturePower == null) {
            signaturePower = new ArrayList<TypeSignaturePower2>();
        }
        return this.signaturePower;
    }

}
