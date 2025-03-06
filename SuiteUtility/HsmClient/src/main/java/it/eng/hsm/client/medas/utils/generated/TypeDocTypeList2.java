
package it.eng.hsm.client.medas.utils.generated;

import it.eng.hsm.client.medas.syncsign.generated.TypeDocumentType;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeDocTypeList2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeDocTypeList2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docType" type="{http://www.medas-solutions.it/ScrybaSignServer/}typeDocumentType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeDocTypeList2", propOrder = {
    "docType"
})
public class TypeDocTypeList2 {

    protected List<TypeDocumentType> docType;

    /**
     * Gets the value of the docType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the docType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeDocumentType }
     * 
     * 
     */
    public List<TypeDocumentType> getDocType() {
        if (docType == null) {
            docType = new ArrayList<TypeDocumentType>();
        }
        return this.docType;
    }

}
