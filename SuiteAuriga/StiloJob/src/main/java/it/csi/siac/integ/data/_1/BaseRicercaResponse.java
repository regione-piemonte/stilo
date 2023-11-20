/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.integ.data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.csi.siac.ricerche.svc._1.BaseRicercaSoggettiResponse;
import it.csi.siac.ricerche.svc._1.RicercaCapitoloEntrataGestioneResponse;
import it.csi.siac.ricerche.svc._1.RicercaCapitoloUscitaGestioneResponse;


/**
 * <p>Classe Java per baseRicercaResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="baseRicercaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://siac.csi.it/integ/data/1.0}baseResponse"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseRicercaResponse")
@XmlSeeAlso({
    RicercaCapitoloEntrataGestioneResponse.class,
    RicercaCapitoloUscitaGestioneResponse.class,
    BaseRicercaSoggettiResponse.class,
    RicercaPaginataResponse.class
})
public abstract class BaseRicercaResponse
    extends BaseResponse
{


}
