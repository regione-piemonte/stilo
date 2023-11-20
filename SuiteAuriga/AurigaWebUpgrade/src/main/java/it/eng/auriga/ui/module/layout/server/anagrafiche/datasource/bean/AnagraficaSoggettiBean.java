/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamicoListaOutputBean;

public class AnagraficaSoggettiBean extends AnagraficaSoggettiXmlBean{
	
	private String tipologia;	
	
	private Boolean flgUnitaDiPersonale;
	
	private String sesso;

	private String codicUoInIpa;
	
	private String nomeComuneNascitaIstituzione;
	
	private String provNascitaIstituzione;
	
	private String cittaNascitaIstituzione;		
 
	private List<IndirizzoSoggettoBean> listaIndirizzi;
	
	private List<BillingAccountBean> listaBillingAccount;
	
	private List<ContattoSoggettoBean> listaContatti;
	
	private List<AltraDenominazioneSoggettoBean> listaAltreDenominazioni;

	private String citta;

	private LoadAttrDinamicoListaOutputBean attributiDinamici;

	private String rsVentCorr; 
	
	private String rsProvRea;
	
	private String rsPrgrRea;
	
	private String rsNroRea;
	
	private String rsNote;
	
	private String rsEccReqDim;
    
	private String beneficiario;
	
	private String istitutoFinanziario;
	
	private String iban;
	
	private String abi;
	
	private String cab;
	
	private String idPuntoVenditaRiferimento;
	
	private String descrizionePuntoVenditaRiferimento;
	
	private String idUoAssociata;

	private String numeroLivelli;

	private String descrizioneUo;

	private Boolean flgVisibileDaSottoUo;

	private Boolean flgModificabileDaSottoUo;

	private List<OrganigrammaBean> listaUoAppartenenza;
	
	
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	public Boolean getFlgUnitaDiPersonale() {
		return flgUnitaDiPersonale;
	}
	public void setFlgUnitaDiPersonale(Boolean flgUnitaDiPersonale) {
		this.flgUnitaDiPersonale = flgUnitaDiPersonale;
	}

	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getCodicUoInIpa() {
		return codicUoInIpa;
	}
	public void setCodicUoInIpa(String codicUoInIpa) {
		this.codicUoInIpa = codicUoInIpa;
	}

	public String getProvNascitaIstituzione() {
		return provNascitaIstituzione;
	}
	public void setProvNascitaIstituzione(String provNascitaIstituzione) {
		this.provNascitaIstituzione = provNascitaIstituzione;
	}
	public String getCittaNascitaIstituzione() {
		return cittaNascitaIstituzione;
	}
	public void setCittaNascitaIstituzione(String cittaNascitaIstituzione) {
		this.cittaNascitaIstituzione = cittaNascitaIstituzione;
	}

	public List<IndirizzoSoggettoBean> getListaIndirizzi() {
		return listaIndirizzi;
	}
	public void setListaIndirizzi(List<IndirizzoSoggettoBean> listaIndirizzi) {
		this.listaIndirizzi = listaIndirizzi;
	}
	public List<ContattoSoggettoBean> getListaContatti() {
		return listaContatti;
	}
	public void setListaContatti(List<ContattoSoggettoBean> listaContatti) {
		this.listaContatti = listaContatti;
	}
	public List<AltraDenominazioneSoggettoBean> getListaAltreDenominazioni() {
		return listaAltreDenominazioni;
	}
	public void setListaAltreDenominazioni(List<AltraDenominazioneSoggettoBean> listaAltreDenominazioni) {
		this.listaAltreDenominazioni = listaAltreDenominazioni;
	}

	public String getNomeComuneNascitaIstituzione() {
		return nomeComuneNascitaIstituzione;
	}
	public void setNomeComuneNascitaIstituzione(
			String nomeComuneNascitaIstituzione) {
		this.nomeComuneNascitaIstituzione = nomeComuneNascitaIstituzione;
	}
	
	public LoadAttrDinamicoListaOutputBean getAttributiDinamici() {
		return attributiDinamici;
	}
	public void setAttributiDinamici(
			LoadAttrDinamicoListaOutputBean attributiDinamici) {
		this.attributiDinamici = attributiDinamici;
	}

	public String getRsVentCorr() {
		return rsVentCorr;
	}
	public String getRsProvRea() {
		return rsProvRea;
	}
	public String getRsPrgrRea() {
		return rsPrgrRea;
	}
	public String getRsNroRea() {
		return rsNroRea;
	}
	public String getRsNote() {
		return rsNote;
	}
	public String getRsEccReqDim() {
		return rsEccReqDim;
	}
	public void setRsVentCorr(String rsVentCorr) {
		this.rsVentCorr = rsVentCorr;
	}
	public void setRsProvRea(String rsProvRea) {
		this.rsProvRea = rsProvRea;
	}
	public void setRsPrgrRea(String rsPrgrRea) {
		this.rsPrgrRea = rsPrgrRea;
	}
	public void setRsNroRea(String rsNroRea) {
		this.rsNroRea = rsNroRea;
	}
	public void setRsNote(String rsNote) {
		this.rsNote = rsNote;
	}
	public void setRsEccReqDim(String rsEccReqDim) {
		this.rsEccReqDim = rsEccReqDim;
	}

	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	public String getIstitutoFinanziario() {
		return istitutoFinanziario;
	}
	public void setIstitutoFinanziario(String istitutoFinanziario) {
		this.istitutoFinanziario = istitutoFinanziario;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getAbi() {
		return abi;
	}
	public void setAbi(String abi) {
		this.abi = abi;
	}
	public String getCab() {
		return cab;
	}
	public void setCab(String cab) {
		this.cab = cab;
	}

	public List<BillingAccountBean> getListaBillingAccount() {
		return listaBillingAccount;
	}
	public void setListaBillingAccount(List<BillingAccountBean> listaBillingAccount) {
		this.listaBillingAccount = listaBillingAccount;
	}
	public String getIdPuntoVenditaRiferimento() {
		return idPuntoVenditaRiferimento;
	}
	public void setIdPuntoVenditaRiferimento(String idPuntoVenditaRiferimento) {
		this.idPuntoVenditaRiferimento = idPuntoVenditaRiferimento;
	}
	public String getDescrizionePuntoVenditaRiferimento() {
		return descrizionePuntoVenditaRiferimento;
	}
	public void setDescrizionePuntoVenditaRiferimento(
			String descrizionePuntoVenditaRiferimento) {
		this.descrizionePuntoVenditaRiferimento = descrizionePuntoVenditaRiferimento;
	}
	public String getIdUoAssociata() {
		return idUoAssociata;
	}
	public void setIdUoAssociata(String idUoAssociata) {
		this.idUoAssociata = idUoAssociata;
	}
	public String getNumeroLivelli() {
		return numeroLivelli;
	}
	public void setNumeroLivelli(String numeroLivelli) {
		this.numeroLivelli = numeroLivelli;
	}
	public String getDescrizioneUo() {
		return descrizioneUo;
	}
	public void setDescrizioneUo(String descrizioneUo) {
		this.descrizioneUo = descrizioneUo;
	}
	public Boolean getFlgVisibileDaSottoUo() {
		return flgVisibileDaSottoUo;
	}
	public void setFlgVisibileDaSottoUo(Boolean flgVisibileDaSottoUo) {
		this.flgVisibileDaSottoUo = flgVisibileDaSottoUo;
	}
	public Boolean getFlgModificabileDaSottoUo() {
		return flgModificabileDaSottoUo;
	}
	public void setFlgModificabileDaSottoUo(Boolean flgModificabileDaSottoUo) {
		this.flgModificabileDaSottoUo = flgModificabileDaSottoUo;
	}
	public List<OrganigrammaBean> getListaUoAppartenenza() {
		return listaUoAppartenenza;
	}
	public void setListaUoAppartenenza(List<OrganigrammaBean> listaUoAppartenenza) {
		this.listaUoAppartenenza = listaUoAppartenenza;
	}
	

	
	
}
