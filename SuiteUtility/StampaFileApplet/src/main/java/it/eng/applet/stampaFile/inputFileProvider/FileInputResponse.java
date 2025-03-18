/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.applet.stampaFile.bean.FileBean;

import java.util.List;

public class FileInputResponse {

	private List<FileBean> fileBeanList;
	
	public List<FileBean> getFileBeanList() {
		return fileBeanList;
	}
	public void setFileBeanList(List<FileBean> fileBeanList) {
		this.fileBeanList = fileBeanList;
	}
	
}
