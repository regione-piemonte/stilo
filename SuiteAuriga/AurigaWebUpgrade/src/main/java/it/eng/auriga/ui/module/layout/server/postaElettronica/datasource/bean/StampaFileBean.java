/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class StampaFileBean {

	private String nomeFile;
	private String uri;
	private MimeTypeFirmaBean infoFile;
	private Boolean remoteUri;

	List<StampaFileBean> listaAllegati;

	public List<StampaFileBean> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<StampaFileBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	/**
	 * @return the remoteUri
	 */
	public Boolean getRemoteUri() {
		return remoteUri;
	}

	/**
	 * @param remoteUri the remoteUri to set
	 */
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}

}
