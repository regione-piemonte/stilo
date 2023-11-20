/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.FileDaFirmare;

public class FirmaMultiplaHsmAutomaticaBean {

	private List<FileDaFirmare> listaFileDaFirmare;
	private String provider;
	private String useridFirmatario;
	private String firmaInDelegaPer;
	private String password;

	public List<FileDaFirmare> getListaFileDaFirmare() {
		return listaFileDaFirmare;
	}

	public void setListaFileDaFirmare(List<FileDaFirmare> listaFileDaFirmare) {
		this.listaFileDaFirmare = listaFileDaFirmare;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getUseridFirmatario() {
		return useridFirmatario;
	}

	public void setUseridFirmatario(String useridFirmatario) {
		this.useridFirmatario = useridFirmatario;
	}

	public String getFirmaInDelegaPer() {
		return firmaInDelegaPer;
	}

	public void setFirmaInDelegaPer(String firmaInDelegaPer) {
		this.firmaInDelegaPer = firmaInDelegaPer;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
