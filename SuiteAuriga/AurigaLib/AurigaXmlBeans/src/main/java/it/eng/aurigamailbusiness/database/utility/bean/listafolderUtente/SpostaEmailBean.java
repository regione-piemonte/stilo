/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.aurigamailbusiness.bean.EmailGroupBean;

public class SpostaEmailBean extends EmailGroupBean {

	private static final long serialVersionUID = 8976856555955216561L;
	
	List<String> folderArrivo;

	public List<String> getFolderArrivo() {
		return folderArrivo;
	}

	public void setFolderArrivo(List<String> folderArrivo) {
		this.folderArrivo = folderArrivo;
	}

}
