/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per entitaCodificataBase complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="entitaCodificataBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}entitaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entitaCodificataBase", propOrder = {
    "descrizione"
})
@XmlSeeAlso({
    Ente.class,
    Stato.class,
    MessaggioBase.class,
    ClassificatoreGenerico.class,
    PianoDeiContiFinanziario.class,
    StrutturaAmministrativa.class,
    TipoFinanziamento.class,
    TipoFondo.class,
    Titolo.class,
    Categoria.class,
    ClasseSoggettoStilo.class,
    Macroaggregato.class,
    Missione.class,
    ProgettoStilo.class,
    Programma.class,
    SoggettoStilo.class,
    TipoDebitoSiopeStilo.class,
    Tipologia.class
})
public abstract class EntitaCodificataBase
    extends EntitaBase
{

    protected String descrizione;

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

}
