/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.TipoFirmaHsm;
import it.eng.auriga.ui.module.layout.server.task.bean.InfoFirmaGraficaBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class CalcolaFirmaBean {

	private String uri;
	private String nomeFile;
	private String idFile;
	private MimeTypeFirmaBean infoFile;
	private TipoFirmaHsm tipoFirmaHsm;
	private List<InfoFirmaGraficaBean> listaInformazioniFirmaGrafica;
	private Boolean isFilePrincipaleAtto; 

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getIdFile() {
		return idFile;
	}

	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	public TipoFirmaHsm getTipoFirmaHsm() {
		return tipoFirmaHsm;
	}

	public void setTipoFirmaHsm(TipoFirmaHsm tipoFirmaHsm) {
		this.tipoFirmaHsm = tipoFirmaHsm;
	}

	public List<InfoFirmaGraficaBean> getListaInformazioniFirmaGrafica() {
		return listaInformazioniFirmaGrafica;
	}

	public void setListaInformazioniFirmaGrafica(List<InfoFirmaGraficaBean> listaInformazioniFirmaGrafica) {
		this.listaInformazioniFirmaGrafica = listaInformazioniFirmaGrafica;
	}

	public Boolean getIsFilePrincipaleAtto() {
		return isFilePrincipaleAtto;
	}
	
	public void setIsFilePrincipaleAtto(Boolean isFilePrincipaleAtto) {
		this.isFilePrincipaleAtto = isFilePrincipaleAtto;
	}

}
