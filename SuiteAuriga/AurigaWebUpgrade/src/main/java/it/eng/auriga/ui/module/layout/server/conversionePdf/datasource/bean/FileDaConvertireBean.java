/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.task.bean.InfoFirmaGraficaBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;


public class FileDaConvertireBean extends IdFileBean {

	private String nomeFilePrec;
	private String uriPrec;	
	private MimeTypeFirmaBean infoFilePrec;
	private List<InfoFirmaGraficaBean> listaInformazioniFirmaGrafica;
	private Boolean isFilePrincipaleAtto; 
	
	public String getNomeFilePrec() {
		return nomeFilePrec;
	}
	public void setNomeFilePrec(String nomeFilePrec) {
		this.nomeFilePrec = nomeFilePrec;
	}
	public String getUriPrec() {
		return uriPrec;
	}
	public void setUriPrec(String uriPrec) {
		this.uriPrec = uriPrec;
	}
	public MimeTypeFirmaBean getInfoFilePrec() {
		return infoFilePrec;
	}
	public void setInfoFilePrec(MimeTypeFirmaBean infoFilePrec) {
		this.infoFilePrec = infoFilePrec;
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
