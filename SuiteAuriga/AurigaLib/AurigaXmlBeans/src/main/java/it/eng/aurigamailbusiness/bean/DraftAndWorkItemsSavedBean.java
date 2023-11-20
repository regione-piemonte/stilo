/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

/**
 * riferimento delle email salvate in bozza
 * 
 * @author mzanetti
 * 
 */
@XmlRootElement
public class DraftAndWorkItemsSavedBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6635888600132119050L;

	/**
	 * Informazioni sulla mail salvata come bozza, nel caso del salvataggio degli item di lavorazione non si aggiorna e quindi non si restituisce un oggetto
	 * SenderBean, da utilizzare per l'invio della mail
	 */
	private SenderBean draftEmail;

	/**
	 * id della mail salvata, è restituita in tutti i casi in caso di esito positivo della store
	 */

	private String idEmail;

	public SenderBean getDraftEmail() {
		return draftEmail;
	}

	public void setDraftEmail(SenderBean draftEmail) {
		this.draftEmail = draftEmail;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	// TODO eventualmente inserire anche il bean XML inviato alla store

}
