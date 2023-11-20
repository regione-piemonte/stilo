/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;


import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

public class ContenutiAmmTraspTreeNodeBean extends TreeNodeBean {
	
	private String idSezione;
	private String idSezionePrec;
	private String idSezionePadre;
	private String motivoCancellazione;
	private String tipoEntita;
	
	private List<PrivilegiEntitaSezioneBean>  listaPrivilegiEntitaAbilitate;
	
	private Boolean showRifNormativiButton;
	private Boolean showHeaderButton;
	private Boolean showTitoloSezioneNewButton; 
	private Boolean showFineSezioneNewButton;
	private Boolean showParagrafoNewButton;
	private Boolean showDocumentoSempliceNewButton;
	private Boolean showDocumentoConAllegatiNewButton;
	private Boolean showTabellaNewButton;
	private Boolean showContenutiTabellaButton;
	private Boolean showContextMenuTree;
	private Boolean showAggiungiSottoSezioneMenu;
	private Boolean flgSezApertaATutti;
	
	private Boolean flgToAbil;

	public String getMotivoCancellazione() {
		return motivoCancellazione;
	}

	public void setMotivoCancellazione(String motivoCancellazione) {
		this.motivoCancellazione = motivoCancellazione;
	}
	
	public String getIdSezione() {
		return idSezione;
	}

	public void setIdSezione(String idSezione) {
		this.idSezione = idSezione;
	}

	public String getIdSezionePrec() {
		return idSezionePrec;
	}

	public void setIdSezionePrec(String idSezionePrec) {
		this.idSezionePrec = idSezionePrec;
	}

	public String getIdSezionePadre() {
		return idSezionePadre;
	}

	public void setIdSezionePadre(String idSezionePadre) {
		this.idSezionePadre = idSezionePadre;
	}

	public Boolean getShowRifNormativiButton() {
		return showRifNormativiButton;
	}

	public void setShowRifNormativiButton(Boolean showRifNormativiButton) {
		this.showRifNormativiButton = showRifNormativiButton;
	}

	public Boolean getShowHeaderButton() {
		return showHeaderButton;
	}

	public void setShowHeaderButton(Boolean showHeaderButton) {
		this.showHeaderButton = showHeaderButton;
	}

	public Boolean getShowTitoloSezioneNewButton() {
		return showTitoloSezioneNewButton;
	}

	public void setShowTitoloSezioneNewButton(Boolean showTitoloSezioneNewButton) {
		this.showTitoloSezioneNewButton = showTitoloSezioneNewButton;
	}

	public Boolean getShowFineSezioneNewButton() {
		return showFineSezioneNewButton;
	}

	public void setShowFineSezioneNewButton(Boolean showFineSezioneNewButton) {
		this.showFineSezioneNewButton = showFineSezioneNewButton;
	}

	public Boolean getShowParagrafoNewButton() {
		return showParagrafoNewButton;
	}

	public void setShowParagrafoNewButton(Boolean showParagrafoNewButton) {
		this.showParagrafoNewButton = showParagrafoNewButton;
	}

	public Boolean getShowDocumentoSempliceNewButton() {
		return showDocumentoSempliceNewButton;
	}

	public void setShowDocumentoSempliceNewButton(Boolean showDocumentoSempliceNewButton) {
		this.showDocumentoSempliceNewButton = showDocumentoSempliceNewButton;
	}

	public Boolean getShowDocumentoConAllegatiNewButton() {
		return showDocumentoConAllegatiNewButton;
	}

	public void setShowDocumentoConAllegatiNewButton(Boolean showDocumentoConAllegatiNewButton) {
		this.showDocumentoConAllegatiNewButton = showDocumentoConAllegatiNewButton;
	}

	public Boolean getShowTabellaNewButton() {
		return showTabellaNewButton;
	}

	public void setShowTabellaNewButton(Boolean showTabellaNewButton) {
		this.showTabellaNewButton = showTabellaNewButton;
	}

	public Boolean getShowContenutiTabellaButton() {
		return showContenutiTabellaButton;
	}

	public void setShowContenutiTabellaButton(Boolean showContenutiTabellaButton) {
		this.showContenutiTabellaButton = showContenutiTabellaButton;
	}

	public Boolean getShowContextMenuTree() {
		return showContextMenuTree;
	}

	public void setShowContextMenuTree(Boolean showContextMenuTree) {
		this.showContextMenuTree = showContextMenuTree;
	}

	public String getTipoEntita() {
		return tipoEntita;
	}

	public void setTipoEntita(String tipoEntita) {
		this.tipoEntita = tipoEntita;
	}

	public List<PrivilegiEntitaSezioneBean> getListaPrivilegiEntitaAbilitate() {
		return listaPrivilegiEntitaAbilitate;
	}

	public void setListaPrivilegiEntitaAbilitate(List<PrivilegiEntitaSezioneBean> listaPrivilegiEntitaAbilitate) {
		this.listaPrivilegiEntitaAbilitate = listaPrivilegiEntitaAbilitate;
	}

	public Boolean getShowAggiungiSottoSezioneMenu() {
		return showAggiungiSottoSezioneMenu;
	}

	public void setShowAggiungiSottoSezioneMenu(Boolean showAggiungiSottoSezioneMenu) {
		this.showAggiungiSottoSezioneMenu = showAggiungiSottoSezioneMenu;
	}
	public Boolean getFlgSezApertaATutti() {
		return flgSezApertaATutti;
	}
	public void setFlgSezApertaATutti(Boolean flgSezApertaATutti) {
		this.flgSezApertaATutti = flgSezApertaATutti;
	}

	public Boolean getFlgToAbil() {
		return flgToAbil;
	}

	public void setFlgToAbil(Boolean flgToAbil) {
		this.flgToAbil = flgToAbil;
	}

}