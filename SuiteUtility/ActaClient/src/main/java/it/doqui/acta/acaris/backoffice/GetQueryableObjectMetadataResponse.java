
package it.doqui.acta.acaris.backoffice;

import java.util.ArrayList;
import java.util.List;
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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="queryableObjectMetadataList" type="{common.acaris.acta.doqui.it}ObjectMetadataType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "queryableObjectMetadataList"
})
@XmlRootElement(name = "getQueryableObjectMetadataResponse", namespace = "backoffice.acaris.acta.doqui.it")
public class GetQueryableObjectMetadataResponse {

    protected List<ObjectMetadataType> queryableObjectMetadataList;

    /**
     * Gets the value of the queryableObjectMetadataList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the queryableObjectMetadataList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQueryableObjectMetadataList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectMetadataType }
     * 
     * 
     */
    public List<ObjectMetadataType> getQueryableObjectMetadataList() {
        if (queryableObjectMetadataList == null) {
            queryableObjectMetadataList = new ArrayList<ObjectMetadataType>();
        }
        return this.queryableObjectMetadataList;
    }

}
