/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.documenti.svc._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.ricerche.data._1.DocumentoSpesa;


/**
 * <p>Classe Java per ricercaDocumentoSpesaResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaDocumentoSpesaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/documenti/svc/1.0}baseRicercaDocumentoResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="documentiSpesa" type="{http://siac.csi.it/ricerche/data/1.0}documentoSpesa" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaDocumentoSpesaResponse", propOrder = {
    "documentiSpesa"
})
public class RicercaDocumentoSpesaResponse
    extends BaseRicercaDocumentoResponse
{

    @XmlElement(nillable = true)
    protected List<DocumentoSpesa> documentiSpesa;

    /**
     * Gets the value of the documentiSpesa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentiSpesa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentiSpesa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentoSpesa }
     * 
     * 
     */
    public List<DocumentoSpesa> getDocumentiSpesa() {
        if (documentiSpesa == null) {
            documentiSpesa = new ArrayList<DocumentoSpesa>();
        }
        return this.documentiSpesa;
    }

}
