
package it.eng.hsm.client.medas.syncsign.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeDsPadesProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeDsPadesProperties">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dsPadesPropertiesApparence" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeDsPadesPropertiesApparence" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeDsPadesProperties", propOrder = {
    "dsPadesPropertiesApparence"
})
public class TypeDsPadesProperties {

    protected TypeDsPadesPropertiesApparence dsPadesPropertiesApparence;

    /**
     * Gets the value of the dsPadesPropertiesApparence property.
     * 
     * @return
     *     possible object is
     *     {@link TypeDsPadesPropertiesApparence }
     *     
     */
    public TypeDsPadesPropertiesApparence getDsPadesPropertiesApparence() {
        return dsPadesPropertiesApparence;
    }

    /**
     * Sets the value of the dsPadesPropertiesApparence property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeDsPadesPropertiesApparence }
     *     
     */
    public void setDsPadesPropertiesApparence(TypeDsPadesPropertiesApparence value) {
        this.dsPadesPropertiesApparence = value;
    }

}
