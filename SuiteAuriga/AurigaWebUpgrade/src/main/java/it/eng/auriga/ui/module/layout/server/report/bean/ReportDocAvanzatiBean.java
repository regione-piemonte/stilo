/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

public class ReportDocAvanzatiBean {
	
	// filtri
	private String tipoReport;	
	private Date da;
	private Date a;
	
	
	private String idEnteAoo;
	private String idUO;
	private boolean flgIncluseSottoUO;
	private String idUtente;
	private String supporto;
	private String presenzaFile;
	
	private List<String> livelliRiservatezza;
	private List<String> applicazioniEsterne;
	private List<String> tipoRegistrazione;
	private List<String> categoriaRegistrazione;
	private List<String> mezzoTrasmissione;
	private List<String> registroNumerazione;
		
	// raggruppamenti
	private String raggruppaPeriodo;
	private String raggruppaUo;
	private boolean raggruppaTipoRegistrazione;
	private boolean raggruppaCategoriaRegistrazione;
	private boolean raggruppaRegistroNumerazione;
	private boolean raggruppaRegValideAnnullate;
	private boolean raggruppaEnteAoo;	
	private boolean raggruppaUtente;
	private boolean raggruppaApplicazioniEsterne;
	private boolean raggruppaSupporto;
	private boolean raggruppaPresenzaFile;
	private boolean raggruppaMezzoTrasmissione;
	private boolean raggruppaLivelliRiservatezza;
	
	private Integer level;
	private Integer idSoggetto;
	
	public String getTipoReport() {
		return tipoReport;
	}
	public void setTipoReport(String tipoReport) {
		this.tipoReport = tipoReport;
	}
	public Date getDa() {
		return da;
	}
	public void setDa(Date da) {
		this.da = da;
	}
	public Date getA() {
		return a;
	}
	public void setA(Date a) {
		this.a = a;
	}
	
	
	public String getIdEnteAoo() {
		return idEnteAoo;
	}
	public void setIdEnteAoo(String idEnteAoo) {
		this.idEnteAoo = idEnteAoo;
	}
	public String getIdUO() {
		return idUO;
	}
	public void setIdUO(String idUO) {
		this.idUO = idUO;
	}
	
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	
	public String getSupporto() {
		return supporto;
	}
	public void setSupporto(String supporto) {
		this.supporto = supporto;
	}
	public String getPresenzaFile() {
		return presenzaFile;
	}
	public void setPresenzaFile(String presenzaFile) {
		this.presenzaFile = presenzaFile;
	}
	
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Integer idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public boolean isFlgIncluseSottoUO() {
		return flgIncluseSottoUO;
	}
	public void setFlgIncluseSottoUO(boolean flgIncluseSottoUO) {
		this.flgIncluseSottoUO = flgIncluseSottoUO;
	}
	
	public boolean isRaggruppaTipoRegistrazione() {
		return raggruppaTipoRegistrazione;
	}
	public void setRaggruppaTipoRegistrazione(boolean raggruppaTipoRegistrazione) {
		this.raggruppaTipoRegistrazione = raggruppaTipoRegistrazione;
	}
	public boolean isRaggruppaCategoriaRegistrazione() {
		return raggruppaCategoriaRegistrazione;
	}
	public void setRaggruppaCategoriaRegistrazione(
			boolean raggruppaCategoriaRegistrazione) {
		this.raggruppaCategoriaRegistrazione = raggruppaCategoriaRegistrazione;
	}
	public boolean isRaggruppaRegistroNumerazione() {
		return raggruppaRegistroNumerazione;
	}
	public void setRaggruppaRegistroNumerazione(boolean raggruppaRegistroNumerazione) {
		this.raggruppaRegistroNumerazione = raggruppaRegistroNumerazione;
	}
	public boolean isRaggruppaRegValideAnnullate() {
		return raggruppaRegValideAnnullate;
	}
	public void setRaggruppaRegValideAnnullate(boolean raggruppaRegValideAnnullate) {
		this.raggruppaRegValideAnnullate = raggruppaRegValideAnnullate;
	}
	public boolean isRaggruppaEnteAoo() {
		return raggruppaEnteAoo;
	}
	public void setRaggruppaEnteAoo(boolean raggruppaEnteAoo) {
		this.raggruppaEnteAoo = raggruppaEnteAoo;
	}
	public boolean isRaggruppaUtente() {
		return raggruppaUtente;
	}
	public void setRaggruppaUtente(boolean raggruppaUtente) {
		this.raggruppaUtente = raggruppaUtente;
	}
	public boolean isRaggruppaApplicazioniEsterne() {
		return raggruppaApplicazioniEsterne;
	}
	public void setRaggruppaApplicazioniEsterne(boolean raggruppaApplicazioniEsterne) {
		this.raggruppaApplicazioniEsterne = raggruppaApplicazioniEsterne;
	}
	public boolean isRaggruppaSupporto() {
		return raggruppaSupporto;
	}
	public void setRaggruppaSupporto(boolean raggruppaSupporto) {
		this.raggruppaSupporto = raggruppaSupporto;
	}
	public boolean isRaggruppaPresenzaFile() {
		return raggruppaPresenzaFile;
	}
	public void setRaggruppaPresenzaFile(boolean raggruppaPresenzaFile) {
		this.raggruppaPresenzaFile = raggruppaPresenzaFile;
	}
	public boolean isRaggruppaMezzoTrasmissione() {
		return raggruppaMezzoTrasmissione;
	}
	public void setRaggruppaMezzoTrasmissione(boolean raggruppaMezzoTrasmissione) {
		this.raggruppaMezzoTrasmissione = raggruppaMezzoTrasmissione;
	}
	public boolean isRaggruppaLivelliRiservatezza() {
		return raggruppaLivelliRiservatezza;
	}
	public void setRaggruppaLivelliRiservatezza(boolean raggruppaLivelliRiservatezza) {
		this.raggruppaLivelliRiservatezza = raggruppaLivelliRiservatezza;
	}
	public List<String> getApplicazioniEsterne() {
		return applicazioniEsterne;
	}
	public void setApplicazioniEsterne(List<String> applicazioniEsterne) {
		this.applicazioniEsterne = applicazioniEsterne;
	}
	public List<String> getTipoRegistrazione() {
		return tipoRegistrazione;
	}
	public void setTipoRegistrazione(List<String> tipoRegistrazione) {
		this.tipoRegistrazione = tipoRegistrazione;
	}
	public List<String> getCategoriaRegistrazione() {
		return categoriaRegistrazione;
	}
	public void setCategoriaRegistrazione(List<String> categoriaRegistrazione) {
		this.categoriaRegistrazione = categoriaRegistrazione;
	}
	public void setMezzoTrasmissione(List<String> mezzoTrasmissione) {
		this.mezzoTrasmissione = mezzoTrasmissione;
	}
	public List<String> getMezzoTrasmissione() {
		return mezzoTrasmissione;
	}
	public List<String> getLivelliRiservatezza() {
		return livelliRiservatezza;
	}
	public void setLivelliRiservatezza(List<String> livelliRiservatezza) {
		this.livelliRiservatezza = livelliRiservatezza;
	}
	public List<String> getRegistroNumerazione() {
		return registroNumerazione;
	}
	public void setRegistroNumerazione(List<String> registroNumerazione) {
		this.registroNumerazione = registroNumerazione;
	}
	public String getRaggruppaPeriodo() {
		return raggruppaPeriodo;
	}
	public void setRaggruppaPeriodo(String raggruppaPeriodo) {
		this.raggruppaPeriodo = raggruppaPeriodo;
	}
	public String getRaggruppaUo() {
		return raggruppaUo;
	}
	public void setRaggruppaUo(String raggruppaUo) {
		this.raggruppaUo = raggruppaUo;
	}
	
	}
