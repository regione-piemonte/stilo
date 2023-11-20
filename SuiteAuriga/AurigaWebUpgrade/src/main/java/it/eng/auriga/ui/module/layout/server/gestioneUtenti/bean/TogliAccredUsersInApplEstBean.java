/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class TogliAccredUsersInApplEstBean {
		
	private String codiceApplEst;
	private String codiceIstAppl;
	
	private List<UtentiAccredInApplEstBean> listaUtentiXml;
	
	public List<UtentiAccredInApplEstBean> getListaUtentiXml() {
		return listaUtentiXml;
	}
	public void setListaUtentiXml(List<UtentiAccredInApplEstBean> listaUtentiXml) {
		this.listaUtentiXml = listaUtentiXml;
	}
	public String getCodiceApplEst() {
		return codiceApplEst;
	}
	public void setCodiceApplEst(String codiceApplEst) {
		this.codiceApplEst = codiceApplEst;
	}
	public String getCodiceIstAppl() {
		return codiceIstAppl;
	}
	public void setCodiceIstAppl(String codiceIstAppl) {
		this.codiceIstAppl = codiceIstAppl;
	}
	
	
}
