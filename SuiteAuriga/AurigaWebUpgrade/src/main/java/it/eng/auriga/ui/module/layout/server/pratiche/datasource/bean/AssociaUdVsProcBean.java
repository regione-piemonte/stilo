/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class AssociaUdVsProcBean {
	
	private String idProcess;
	private List<AllegatoProcBean> listaAllegatiProc;
	
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public List<AllegatoProcBean> getListaAllegatiProc() {
		return listaAllegatiProc;
	}
	public void setListaAllegatiProc(List<AllegatoProcBean> listaAllegatiProc) {
		this.listaAllegatiProc = listaAllegatiProc;
	}
	
}
