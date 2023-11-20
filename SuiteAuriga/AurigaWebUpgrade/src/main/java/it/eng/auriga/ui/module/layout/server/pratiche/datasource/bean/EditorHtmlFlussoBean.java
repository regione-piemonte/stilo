/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;

import java.util.List;

public class EditorHtmlFlussoBean extends AttProcBean {

	private List<IdFileBean> files;
	
	public List<IdFileBean> getFiles() {
		return files;
	}

	public void setFiles(List<IdFileBean> files) {
		this.files = files;
	}

}
