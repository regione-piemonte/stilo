/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class InterOpBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -4305592794843220854L;

	private List<TEmailMgoBean> mailAgganciate;

	private List<TRegEstVsEmailBean> relazioniEsterne;

	private List<TRegEstVsEmailBean> relazioniEsterneToInsert;

	private List<TRegEstVsEmailBean> relazioniEsterneToUpdate;

	private List<TRelEmailFolderBean> caselleMailAgganciate; // nuove associazioni fra mail e relative folder

	private List<TRelEmailFolderBean> caselleMailOriginarie; // pecedenti associazioni fra mail e relative folder

	private Boolean saveExt;

	public List<TRegEstVsEmailBean> getRelazioniEsterne() {
		return relazioniEsterne;
	}

	public void setRelazioniEsterne(List<TRegEstVsEmailBean> relazioniEsterne) {
		this.relazioniEsterne = relazioniEsterne;
	}

	public Boolean getSaveExt() {
		return saveExt;
	}

	public List<TRegEstVsEmailBean> getRelazioniEsterneToInsert() {
		return relazioniEsterneToInsert;
	}

	public void setRelazioniEsterneToInsert(List<TRegEstVsEmailBean> relazioniEsterneToInsert) {
		this.relazioniEsterneToInsert = relazioniEsterneToInsert;
	}

	public List<TRegEstVsEmailBean> getRelazioniEsterneToUpdate() {
		return relazioniEsterneToUpdate;
	}

	public void setRelazioniEsterneToUpdate(List<TRegEstVsEmailBean> relazioniEsterneToUpdate) {
		this.relazioniEsterneToUpdate = relazioniEsterneToUpdate;
	}

	public void setSaveExt(Boolean saveExt) {
		this.saveExt = saveExt;
	}

	public List<TEmailMgoBean> getMailAgganciate() {
		return mailAgganciate;
	}

	public void setMailAgganciate(List<TEmailMgoBean> mailAgganciate) {
		this.mailAgganciate = mailAgganciate;
	}

	public List<TRelEmailFolderBean> getCaselleMailAgganciate() {
		return caselleMailAgganciate;
	}

	public void setCaselleMailAgganciate(List<TRelEmailFolderBean> caselleMailAgganciate) {
		this.caselleMailAgganciate = caselleMailAgganciate;
	}

	public List<TRelEmailFolderBean> getCaselleMailOriginarie() {
		return caselleMailOriginarie;
	}

	public void setCaselleMailOriginarie(List<TRelEmailFolderBean> caselleMailOriginarie) {
		this.caselleMailOriginarie = caselleMailOriginarie;
	}

}
