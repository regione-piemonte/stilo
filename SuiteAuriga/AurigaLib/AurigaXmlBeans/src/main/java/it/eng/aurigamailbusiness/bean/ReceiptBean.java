/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe relativa ai valori delle ricevute
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class ReceiptBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3846530136630163064L;

	List<TDestinatariEmailMgoBean> destinatari;

	List<TRubricaEmailBean> vociRubrica;

	TEmailMgoBean mailOriginaria;

	List<TRelEmailFolderBean> folderOriginarie;

	List<TRelEmailFolderBean> nuoveFolder;

	public List<TDestinatariEmailMgoBean> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<TDestinatariEmailMgoBean> destinatari) {
		this.destinatari = destinatari;
	}

	public List<TRubricaEmailBean> getVociRubrica() {
		return vociRubrica;
	}

	public void setVociRubrica(List<TRubricaEmailBean> vociRubrica) {
		this.vociRubrica = vociRubrica;
	}

	public TEmailMgoBean getMailOriginaria() {
		return mailOriginaria;
	}

	public void setMailOriginaria(TEmailMgoBean mailOriginaria) {
		this.mailOriginaria = mailOriginaria;
	}

	public List<TRelEmailFolderBean> getFolderOriginarie() {
		return folderOriginarie;
	}

	public void setFolderOriginarie(List<TRelEmailFolderBean> folderOriginarie) {
		this.folderOriginarie = folderOriginarie;
	}

	public List<TRelEmailFolderBean> getNuoveFolder() {
		return nuoveFolder;
	}

	public void setNuoveFolder(List<TRelEmailFolderBean> nuoveFolder) {
		this.nuoveFolder = nuoveFolder;
	}

	

}
