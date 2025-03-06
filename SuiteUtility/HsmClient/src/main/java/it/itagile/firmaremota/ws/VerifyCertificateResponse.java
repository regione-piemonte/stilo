
package it.itagile.firmaremota.ws;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="verifyCertificateReturn" type="{http://ws.firmaremota.itagile.it}CertificateStatus" maxOccurs="unbounded"/&gt;
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
    "verifyCertificateReturn"
})
@XmlRootElement(name = "verifyCertificateResponse")
public class VerifyCertificateResponse {

    @XmlElement(required = true)
    protected List<CertificateStatus> verifyCertificateReturn;

    /**
     * Gets the value of the verifyCertificateReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verifyCertificateReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerifyCertificateReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CertificateStatus }
     * 
     * 
     */
    public List<CertificateStatus> getVerifyCertificateReturn() {
        if (verifyCertificateReturn == null) {
            verifyCertificateReturn = new ArrayList<CertificateStatus>();
        }
        return this.verifyCertificateReturn;
    }

}
