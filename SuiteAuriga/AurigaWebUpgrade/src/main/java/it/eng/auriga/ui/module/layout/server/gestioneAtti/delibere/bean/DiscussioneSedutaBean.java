/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.shared.bean.FileDocumentoBean;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;

public class DiscussioneSedutaBean {

	private String organoCollegiale; // Valori possibili: giunta, consiglio, commissione
	private String listaCommissioni;

	private String idSeduta;
	private String numero;
	private Date dtPrimaConvocazione;
	private String luogoPrimaConvocazione;
	private Date dtSecondaConvocazione;
	private String luogoSecondaConvocazione;
	private String tipoSessione; // Valori possibili: ordinaria, straordinaria, urgente
	/**
	 * Valori possibili di stato: inviata_convocazione, 1a_convocazione, 1a_convocazione_deserta, 2a_convocazione, lavori_conclusi, firmati_atti_adottati
	 */
	private String stato;
	private String desStato;
	private Boolean disattivatoRiordinoAutomatico;
	private OdgInfoXmlBean odgInfo;
	
	private List<ArgomentiOdgXmlBean> listaArgomentiOdgEliminati;
	private List<ArgomentiOdgXmlBean> listaArgomentiOdg;
	private List<EsitiXTipoArgomentoXmlBean> esitiXtipoArgomento;
	
	private VerbaleInfoXmlBean verbaleInfoPrimaConvocazione;
	private VerbaleInfoXmlBean verbaleInfoSecondaConvocazione;

	// Attributi di appoggio per prima convocazione
	private Date datiDiscPrimaConvDtInizioLavori;
	private Date datiDiscPrimaConvDtFineLavori;
	private List<PresenzeOdgXmlBean> listaDatiDiscPrimaConvPresenzeAppelloIniziale;
	private String datiDiscPrimaConvEditorVerbale;
//	private FileVerbaleSedutaBean fileVerbalePrimaConvocazione;

	// Attributi di appoggio per seconda convocazione
	private Date datiDiscSecondaConvDtInizioLavori;
	private Date datiDiscSecondaConvDtFineLavori;
	private List<PresenzeOdgXmlBean> listaDatiDiscSecondaConvPresenzeAppelloIniziale;
	private boolean datiDiscSecondaConvVerbaleFlgPdfizzabile;
	private String datiDiscSecondaConvEditorVerbale;
//	private FileVerbaleSedutaBean fileVerbaleSecondaConvocazione;

	// Attributo di apoggio per generaDaModelloPerSalvaChiudiVerbale
	int numeroDiscussioneDaGenerare;
	FileDaFirmareBean fileGenerato;
	
	private FileDocumentoBean fileDocumento;

	private String listaNomiCommissioniConvocate;
	private String listaIdCommissioniConvocate;
	
	private String motivoAnnullamento;
	
	public String getOrganoCollegiale() {
		return organoCollegiale;
	}

	public void setOrganoCollegiale(String organoCollegiale) {
		this.organoCollegiale = organoCollegiale;
	}

	public String getListaCommissioni() {
		return listaCommissioni;
	}

	public void setListaCommissioni(String listaCommissioni) {
		this.listaCommissioni = listaCommissioni;
	}

	public String getIdSeduta() {
		return idSeduta;
	}

	public void setIdSeduta(String idSeduta) {
		this.idSeduta = idSeduta;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDtPrimaConvocazione() {
		return dtPrimaConvocazione;
	}

	public void setDtPrimaConvocazione(Date dtPrimaConvocazione) {
		this.dtPrimaConvocazione = dtPrimaConvocazione;
	}

	public String getLuogoPrimaConvocazione() {
		return luogoPrimaConvocazione;
	}

	public void setLuogoPrimaConvocazione(String luogoPrimaConvocazione) {
		this.luogoPrimaConvocazione = luogoPrimaConvocazione;
	}

	public Date getDtSecondaConvocazione() {
		return dtSecondaConvocazione;
	}

	public void setDtSecondaConvocazione(Date dtSecondaConvocazione) {
		this.dtSecondaConvocazione = dtSecondaConvocazione;
	}

	public String getLuogoSecondaConvocazione() {
		return luogoSecondaConvocazione;
	}

	public void setLuogoSecondaConvocazione(String luogoSecondaConvocazione) {
		this.luogoSecondaConvocazione = luogoSecondaConvocazione;
	}

	public String getTipoSessione() {
		return tipoSessione;
	}

	public void setTipoSessione(String tipoSessione) {
		this.tipoSessione = tipoSessione;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public String getDesStato() {
		return desStato;
	}

	public void setDesStato(String desStato) {
		this.desStato = desStato;
	}
	
	public Boolean getDisattivatoRiordinoAutomatico() {
		return disattivatoRiordinoAutomatico;
	}

	public void setDisattivatoRiordinoAutomatico(Boolean disattivatoRiordinoAutomatico) {
		this.disattivatoRiordinoAutomatico = disattivatoRiordinoAutomatico;
	}

	public OdgInfoXmlBean getOdgInfo() {
		return odgInfo;
	}

	public void setOdgInfo(OdgInfoXmlBean odgInfo) {
		this.odgInfo = odgInfo;
	}
	
	public List<ArgomentiOdgXmlBean> getListaArgomentiOdgEliminati() {
		return listaArgomentiOdgEliminati;
	}

	public void setListaArgomentiOdgEliminati(List<ArgomentiOdgXmlBean> listaArgomentiOdgEliminati) {
		this.listaArgomentiOdgEliminati = listaArgomentiOdgEliminati;
	}

	public List<ArgomentiOdgXmlBean> getListaArgomentiOdg() {
		return listaArgomentiOdg;
	}

	public void setListaArgomentiOdg(List<ArgomentiOdgXmlBean> listaArgomentiOdg) {
		this.listaArgomentiOdg = listaArgomentiOdg;
	}

	public List<EsitiXTipoArgomentoXmlBean> getEsitiXtipoArgomento() {
		return esitiXtipoArgomento;
	}

	public void setEsitiXtipoArgomento(List<EsitiXTipoArgomentoXmlBean> esitiXtipoArgomento) {
		this.esitiXtipoArgomento = esitiXtipoArgomento;
	}

	public VerbaleInfoXmlBean getVerbaleInfoPrimaConvocazione() {
		return verbaleInfoPrimaConvocazione;
	}

	public void setVerbaleInfoPrimaConvocazione(VerbaleInfoXmlBean verbaleInfoPrimaConvocazione) {
		this.verbaleInfoPrimaConvocazione = verbaleInfoPrimaConvocazione;
	}

	public VerbaleInfoXmlBean getVerbaleInfoSecondaConvocazione() {
		return verbaleInfoSecondaConvocazione;
	}

	public void setVerbaleInfoSecondaConvocazione(VerbaleInfoXmlBean verbaleInfoSecondaConvocazione) {
		this.verbaleInfoSecondaConvocazione = verbaleInfoSecondaConvocazione;
	}

	public Date getDatiDiscPrimaConvDtInizioLavori() {
		return datiDiscPrimaConvDtInizioLavori;
	}

	public void setDatiDiscPrimaConvDtInizioLavori(Date datiDiscPrimaConvDtInizioLavori) {
		this.datiDiscPrimaConvDtInizioLavori = datiDiscPrimaConvDtInizioLavori;
	}

	public Date getDatiDiscPrimaConvDtFineLavori() {
		return datiDiscPrimaConvDtFineLavori;
	}

	public void setDatiDiscPrimaConvDtFineLavori(Date datiDiscPrimaConvDtFineLavori) {
		this.datiDiscPrimaConvDtFineLavori = datiDiscPrimaConvDtFineLavori;
	}

	public List<PresenzeOdgXmlBean> getListaDatiDiscPrimaConvPresenzeAppelloIniziale() {
		return listaDatiDiscPrimaConvPresenzeAppelloIniziale;
	}

	public void setListaDatiDiscPrimaConvPresenzeAppelloIniziale(List<PresenzeOdgXmlBean> listaDatiDiscPrimaConvPresenzeAppelloIniziale) {
		this.listaDatiDiscPrimaConvPresenzeAppelloIniziale = listaDatiDiscPrimaConvPresenzeAppelloIniziale;
	}

	public String getDatiDiscPrimaConvEditorVerbale() {
		return datiDiscPrimaConvEditorVerbale;
	}

	public void setDatiDiscPrimaConvEditorVerbale(String datiDiscPrimaConvEditorVerbale) {
		this.datiDiscPrimaConvEditorVerbale = datiDiscPrimaConvEditorVerbale;
	}

//	public FileVerbaleSedutaBean getFileVerbalePrimaConvocazione() {
//		return fileVerbalePrimaConvocazione;
//	}
//
//	public void setFileVerbalePrimaConvocazione(FileVerbaleSedutaBean fileVerbalePrimaConvocazione) {
//		this.fileVerbalePrimaConvocazione = fileVerbalePrimaConvocazione;
//	}

	public Date getDatiDiscSecondaConvDtInizioLavori() {
		return datiDiscSecondaConvDtInizioLavori;
	}

	public void setDatiDiscSecondaConvDtInizioLavori(Date datiDiscSecondaConvDtInizioLavori) {
		this.datiDiscSecondaConvDtInizioLavori = datiDiscSecondaConvDtInizioLavori;
	}

	public Date getDatiDiscSecondaConvDtFineLavori() {
		return datiDiscSecondaConvDtFineLavori;
	}

	public void setDatiDiscSecondaConvDtFineLavori(Date datiDiscSecondaConvDtFineLavori) {
		this.datiDiscSecondaConvDtFineLavori = datiDiscSecondaConvDtFineLavori;
	}

	public List<PresenzeOdgXmlBean> getListaDatiDiscSecondaConvPresenzeAppelloIniziale() {
		return listaDatiDiscSecondaConvPresenzeAppelloIniziale;
	}

	public void setListaDatiDiscSecondaConvPresenzeAppelloIniziale(List<PresenzeOdgXmlBean> listaDatiDiscSecondaConvPresenzeAppelloIniziale) {
		this.listaDatiDiscSecondaConvPresenzeAppelloIniziale = listaDatiDiscSecondaConvPresenzeAppelloIniziale;
	}

	public boolean isDatiDiscSecondaConvVerbaleFlgPdfizzabile() {
		return datiDiscSecondaConvVerbaleFlgPdfizzabile;
	}

	public void setDatiDiscSecondaConvVerbaleFlgPdfizzabile(boolean datiDiscSecondaConvVerbaleFlgPdfizzabile) {
		this.datiDiscSecondaConvVerbaleFlgPdfizzabile = datiDiscSecondaConvVerbaleFlgPdfizzabile;
	}

	public String getDatiDiscSecondaConvEditorVerbale() {
		return datiDiscSecondaConvEditorVerbale;
	}

	public void setDatiDiscSecondaConvEditorVerbale(String datiDiscSecondaConvEditorVerbale) {
		this.datiDiscSecondaConvEditorVerbale = datiDiscSecondaConvEditorVerbale;
	}

//	public FileVerbaleSedutaBean getFileVerbaleSecondaConvocazione() {
//		return fileVerbaleSecondaConvocazione;
//	}
//
//	public void setFileVerbaleSecondaConvocazione(FileVerbaleSedutaBean fileVerbaleSecondaConvocazione) {
//		this.fileVerbaleSecondaConvocazione = fileVerbaleSecondaConvocazione;
//	}

	public int getNumeroDiscussioneDaGenerare() {
		return numeroDiscussioneDaGenerare;
	}

	public void setNumeroDiscussioneDaGenerare(int numeroDiscussioneDaGenerare) {
		this.numeroDiscussioneDaGenerare = numeroDiscussioneDaGenerare;
	}

	public FileDaFirmareBean getFileGenerato() {
		return fileGenerato;
	}

	public void setFileGenerato(FileDaFirmareBean fileGenerato) {
		this.fileGenerato = fileGenerato;
	}

	public FileDocumentoBean getFileDocumento() {
		return fileDocumento;
	}

	public void setFileDocumento(FileDocumentoBean fileDocumento) {
		this.fileDocumento = fileDocumento;
	}

	public String getListaNomiCommissioniConvocate() {
		return listaNomiCommissioniConvocate;
	}

	public void setListaNomiCommissioniConvocate(String listaNomiCommissioniConvocate) {
		this.listaNomiCommissioniConvocate = listaNomiCommissioniConvocate;
	}

	public String getListaIdCommissioniConvocate() {
		return listaIdCommissioniConvocate;
	}

	public void setListaIdCommissioniConvocate(String listaIdCommissioniConvocate) {
		this.listaIdCommissioniConvocate = listaIdCommissioniConvocate;
	}

	public String getMotivoAnnullamento() {
		return motivoAnnullamento;
	}

	public void setMotivoAnnullamento(String motivoAnnullamento) {
		this.motivoAnnullamento = motivoAnnullamento;
	}
}