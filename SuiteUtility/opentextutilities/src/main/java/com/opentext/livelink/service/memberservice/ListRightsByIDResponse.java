
package com.opentext.livelink.service.memberservice;

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
 *         &lt;element name="ListRightsByIDResult" type="{urn:MemberService.service.livelink.opentext.com}MemberRight" maxOccurs="unbounded" minOccurs="0"/>
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
    "listRightsByIDResult"
})
@XmlRootElement(name = "ListRightsByIDResponse")
public class ListRightsByIDResponse {

    @XmlElement(name = "ListRightsByIDResult", nillable = true)
    protected List<MemberRight> listRightsByIDResult;

    /**
     * Gets the value of the listRightsByIDResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listRightsByIDResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListRightsByIDResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MemberRight }
     * 
     * 
     */
    public List<MemberRight> getListRightsByIDResult() {
        if (listRightsByIDResult == null) {
            listRightsByIDResult = new ArrayList<MemberRight>();
        }
        return this.listRightsByIDResult;
    }

}
