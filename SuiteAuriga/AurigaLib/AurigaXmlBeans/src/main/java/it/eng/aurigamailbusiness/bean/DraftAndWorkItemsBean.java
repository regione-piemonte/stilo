/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.annotation.Attachment;
import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class DraftAndWorkItemsBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -2520763821166821158L;

	public static final Integer MODE_SAVE_ONLY_WORK_ITEMS = 0;
	public static final Integer MODE_SAVE_DRAFT = 1;
	public static final Integer MODE_SAVE_DRAFT_PRE_SEND = 2;

	public static final Integer SENT_FROM_DETAIL = 0;
	public static final Integer SENT_FROM_LIST = 1;

	/** XML con i dati della mail, tra cui i dati della mail di riferimento nel caso di salvataggio di una mail in risposta o in inoltro **/
	private InvioMailXmlBean datiemailin;

	/** eventuale id della mail da aggiornare, valorizzato in caso di risposta con successo dalla store */
	private java.lang.String idemailio;

	@Attachment
	private List<File> listaFileItemLavorazione;

	public InvioMailXmlBean getDatiemailin() {
		return datiemailin;
	}

	public void setDatiemailin(InvioMailXmlBean datiemailin) {
		this.datiemailin = datiemailin;
	}

	public java.lang.String getIdemailio() {
		return idemailio;
	}

	public void setIdemailio(java.lang.String idemailio) {
		this.idemailio = idemailio;
	}

	public List<File> getListaFileItemLavorazione() {
		return listaFileItemLavorazione;
	}

	public void setListaFileItemLavorazione(List<File> listaFileItemLavorazione) {
		this.listaFileItemLavorazione = listaFileItemLavorazione;
	}

}
