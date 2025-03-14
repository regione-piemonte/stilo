
package com.opentext.livelink.service.documentmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TableValue complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TableValue">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:Core.service.livelink.opentext.com}DataValue">
 *       &lt;sequence>
 *         &lt;element name="Values" type="{urn:Core.service.livelink.opentext.com}RowValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TableValue", namespace = "urn:Core.service.livelink.opentext.com", propOrder = {
    "values"
})
public class TableValue
    extends DataValue
{

    @XmlElement(name = "Values")
    protected List<RowValue> values;

    /**
     * Gets the value of the values property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the values property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RowValue }
     * 
     * 
     */
    public List<RowValue> getValues() {
        if (values == null) {
            values = new ArrayList<RowValue>();
        }
        return this.values;
    }

}
