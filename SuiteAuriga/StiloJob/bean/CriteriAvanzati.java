/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Registrazione;
// import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Stato;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.Date;

public class CriteriAvanzati implements Serializable {

	// @XmlVariabile(nome="@Registrazioni", tipo=TipoVariabile.LISTA)
	// private List<Registrazione> registrazioni;

	@XmlVariabile(nome = "IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String idDocType;

	@XmlVariabile(nome = "CodApplOwner", tipo = TipoVariabile.SEMPLICE)
	private String codApplOwner;

	@XmlVariabile(nome = "CodIstApplOwner", tipo = TipoVariabile.SEMPLICE)
	private String codIstApplOwner;

	@XmlVariabile(nome = "NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String nomeDocType;

	// @XmlVariabile(nome="@ProgrInvioFatture", tipo=TipoVariabile.LISTA)
	// private List<ProgrInvioFattura> progrInvioFatture;

	@XmlVariabile(nome = "DataStesuraDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataStesuraDa;

	@XmlVariabile(nome = "DataStesuraA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataStesuraA;

	// @XmlVariabile(nome="@StatiDettDoc", tipo=TipoVariabile.LISTA)
	// private List<Stato> statiDettDoc;

	@XmlVariabile(nome = "DataAggStatoDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAggStatoDa;

	@XmlVariabile(nome = "DataAggStatoA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAggStatoA;

	@XmlVariabile(nome = "CodiceIPA", tipo = TipoVariabile.SEMPLICE)
	private String codiceIPA;

	@XmlVariabile(nome = "RagioneSocialeCliente", tipo = TipoVariabile.SEMPLICE)
	private String ragioneSocialeCliente;

	@XmlVariabile(nome = "ProgrTrasmSDIDa", tipo = TipoVariabile.SEMPLICE)
	private String progrTrasmSDIDa;

	@XmlVariabile(nome = "ProgrTrasmSDIA", tipo = TipoVariabile.SEMPLICE)
	private String progrTrasmSDIA;

	@XmlVariabile(nome = "TipoDocumento", tipo = TipoVariabile.SEMPLICE)
	private String tipoDocumento;

	@XmlVariabile(nome = "DestinatarioCod", tipo = TipoVariabile.SEMPLICE)
	private String destinatarioCod;

	@XmlVariabile(nome = "OrdineAcquistoDataDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date ordineAcquistoDataDa;

	@XmlVariabile(nome = "OrdineAcquistoDataA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date ordineAcquistoDataA;

	@XmlVariabile(nome = "OrdineAcquistoCID", tipo = TipoVariabile.SEMPLICE)
	private String ordineAcquistoCID;

	@XmlVariabile(nome = "DatiPagamentoImporto", tipo = TipoVariabile.SEMPLICE)
	private String datiPagamentoImporto;

	@XmlVariabile(nome = "DatiPagamentoDataScadenzaDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date datiPagamentoDataScadenzaDa;

	@XmlVariabile(nome = "DatiPagamentoDataScadenzaA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date datiPagamentoDataScadenzaA;

	@XmlVariabile(nome = "ClienteUD", tipo = TipoVariabile.SEMPLICE)
	private String clienteUD;

	@XmlVariabile(nome = "CIProvClienteUD", tipo = TipoVariabile.SEMPLICE)
	private String ciProvClienteUD;

	@XmlVariabile(nome = "PIVA_CF_ClienteUD", tipo = TipoVariabile.SEMPLICE)
	private String pIvaCfClienteUD;

	@XmlVariabile(nome = "IdLotto", tipo = TipoVariabile.SEMPLICE)
	private String idLotto;

	@XmlVariabile(nome = "NomeLotto", tipo = TipoVariabile.SEMPLICE)
	private String nomeLotto;

	@XmlVariabile(nome = "CodStatoLotto", tipo = TipoVariabile.SEMPLICE)
	private String codStatoLotto;

	@XmlVariabile(nome = "DataLavorazioneLottoDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataLavorazioneLottoDa;

	@XmlVariabile(nome = "DataLavorazioneLottoA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataLavorazioneLottoA;

	@XmlVariabile(nome = "SocietaLotto", tipo = TipoVariabile.SEMPLICE)
	private String societaLotto;

	@XmlVariabile(nome = "NrDocumento", tipo = TipoVariabile.SEMPLICE)
	private String nrDocumento;

	@XmlVariabile(nome = "NomeFile", tipo = TipoVariabile.SEMPLICE)
	private String nomeFile;

	@XmlVariabile(nome = "DataDocumentoDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataDocumentoDa;

	@XmlVariabile(nome = "DataDocumentoA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataDocumentoA;

	@XmlVariabile(nome = "DataPubblicazioneDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblicazioneDa;

	@XmlVariabile(nome = "DataPubblicazioneA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblicazioneA;

	@XmlVariabile(nome = "CodStatoDocumentoLotto", tipo = TipoVariabile.SEMPLICE)
	private String codStatoDocumentoLotto;

	@XmlVariabile(nome = "IdSezionale", tipo = TipoVariabile.SEMPLICE)
	private String idSezionale;

	@XmlVariabile(nome = "CodSezionale", tipo = TipoVariabile.SEMPLICE)
	private String codSezionale;

	@XmlVariabile(nome = "DescrizioneSezionale", tipo = TipoVariabile.SEMPLICE)
	private String descrizioneSezionale;

	@XmlVariabile(nome = "Anno", tipo = TipoVariabile.SEMPLICE)
	private String anno;

	@XmlVariabile(nome = "Mese", tipo = TipoVariabile.SEMPLICE)
	private String mese;

	@XmlVariabile(nome = "DataInsA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInsA;

	@XmlVariabile(nome = "DataInsDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInsDa;

	@XmlVariabile(nome = "FlgEmailNotificaInviata", tipo = TipoVariabile.SEMPLICE)
	private String flgEmailNotificaInviata;

	@XmlVariabile(nome = "DisplayFilename", tipo = TipoVariabile.SEMPLICE)
	private String displayFilename;

	// public List<Registrazione> getRegistrazioni() {
	// return registrazioni;
	// }

	public String getIdDocType() {
		return idDocType;
	}

	public String getCodApplOwner() {
		return codApplOwner;
	}

	public String getCodIstApplOwner() {
		return codIstApplOwner;
	}

	public String getNomeDocType() {
		return nomeDocType;
	}

	// public List<ProgrInvioFattura> getProgrInvioFatture() {
	// return progrInvioFatture;
	// }

	public Date getDataStesuraDa() {
		return dataStesuraDa;
	}

	public Date getDataStesuraA() {
		return dataStesuraA;
	}

	// public List<Stato> getStatiDettDoc() {
	// return statiDettDoc;
	// }

	public Date getDataAggStatoDa() {
		return dataAggStatoDa;
	}

	public Date getDataAggStatoA() {
		return dataAggStatoA;
	}

	public String getCodiceIPA() {
		return codiceIPA;
	}

	public String getRagioneSocialeCliente() {
		return ragioneSocialeCliente;
	}

	public String getProgrTrasmSDIDa() {
		return progrTrasmSDIDa;
	}

	public String getProgrTrasmSDIA() {
		return progrTrasmSDIA;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public String getDestinatarioCod() {
		return destinatarioCod;
	}

	public Date getOrdineAcquistoDataDa() {
		return ordineAcquistoDataDa;
	}

	public Date getOrdineAcquistoDataA() {
		return ordineAcquistoDataA;
	}

	public String getOrdineAcquistoCID() {
		return ordineAcquistoCID;
	}

	public String getDatiPagamentoImporto() {
		return datiPagamentoImporto;
	}

	public Date getDatiPagamentoDataScadenzaDa() {
		return datiPagamentoDataScadenzaDa;
	}

	public Date getDatiPagamentoDataScadenzaA() {
		return datiPagamentoDataScadenzaA;
	}

	public String getClienteUD() {
		return clienteUD;
	}

	public String getCiProvClienteUD() {
		return ciProvClienteUD;
	}

	public String getpIvaCfClienteUD() {
		return pIvaCfClienteUD;
	}

	public String getIdLotto() {
		return idLotto;
	}

	public String getNomeLotto() {
		return nomeLotto;
	}

	public Date getDataLavorazioneLottoDa() {
		return dataLavorazioneLottoDa;
	}

	public Date getDataLavorazioneLottoA() {
		return dataLavorazioneLottoA;
	}

	public String getSocietaLotto() {
		return societaLotto;
	}

	// public void setRegistrazioni(List<Registrazione> registrazioni) {
	// this.registrazioni = registrazioni;
	// }

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
	}

	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}

	// public void setProgrInvioFatture(List<ProgrInvioFattura> progrInvioFatture) {
	// this.progrInvioFatture = progrInvioFatture;
	// }

	public void setDataStesuraDa(Date dataStesuraDa) {
		this.dataStesuraDa = dataStesuraDa;
	}

	public void setDataStesuraA(Date dataStesuraA) {
		this.dataStesuraA = dataStesuraA;
	}

	// public void setStatiDettDoc(List<Stato> statiDettDoc) {
	// this.statiDettDoc = statiDettDoc;
	// }

	public void setDataAggStatoDa(Date dataAggStatoDa) {
		this.dataAggStatoDa = dataAggStatoDa;
	}

	public void setDataAggStatoA(Date dataAggStatoA) {
		this.dataAggStatoA = dataAggStatoA;
	}

	public void setCodiceIPA(String codiceIPA) {
		this.codiceIPA = codiceIPA;
	}

	public void setRagioneSocialeCliente(String ragioneSocialeCliente) {
		this.ragioneSocialeCliente = ragioneSocialeCliente;
	}

	public void setProgrTrasmSDIDa(String progrTrasmSDIDa) {
		this.progrTrasmSDIDa = progrTrasmSDIDa;
	}

	public void setProgrTrasmSDIA(String progrTrasmSDIA) {
		this.progrTrasmSDIA = progrTrasmSDIA;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public void setDestinatarioCod(String destinatarioCod) {
		this.destinatarioCod = destinatarioCod;
	}

	public void setOrdineAcquistoDataDa(Date ordineAcquistoDataDa) {
		this.ordineAcquistoDataDa = ordineAcquistoDataDa;
	}

	public void setOrdineAcquistoDataA(Date ordineAcquistoDataA) {
		this.ordineAcquistoDataA = ordineAcquistoDataA;
	}

	public void setOrdineAcquistoCID(String ordineAcquistoCID) {
		this.ordineAcquistoCID = ordineAcquistoCID;
	}

	public void setDatiPagamentoImporto(String datiPagamentoImporto) {
		this.datiPagamentoImporto = datiPagamentoImporto;
	}

	public void setDatiPagamentoDataScadenzaDa(Date datiPagamentoDataScadenzaDa) {
		this.datiPagamentoDataScadenzaDa = datiPagamentoDataScadenzaDa;
	}

	public void setDatiPagamentoDataScadenzaA(Date datiPagamentoDataScadenzaA) {
		this.datiPagamentoDataScadenzaA = datiPagamentoDataScadenzaA;
	}

	public void setClienteUD(String clienteUD) {
		this.clienteUD = clienteUD;
	}

	public void setCiProvClienteUD(String ciProvClienteUD) {
		this.ciProvClienteUD = ciProvClienteUD;
	}

	public void setpIvaCfClienteUD(String pIvaCfClienteUD) {
		this.pIvaCfClienteUD = pIvaCfClienteUD;
	}

	public void setIdLotto(String idLotto) {
		this.idLotto = idLotto;
	}

	public void setNomeLotto(String nomeLotto) {
		this.nomeLotto = nomeLotto;
	}

	public void setDataLavorazioneLottoDa(Date dataLavorazioneLottoDa) {
		this.dataLavorazioneLottoDa = dataLavorazioneLottoDa;
	}

	public void setDataLavorazioneLottoA(Date dataLavorazioneLottoA) {
		this.dataLavorazioneLottoA = dataLavorazioneLottoA;
	}

	public void setSocietaLotto(String societaLotto) {
		this.societaLotto = societaLotto;
	}

	public String getNrDocumento() {
		return nrDocumento;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public Date getDataDocumentoDa() {
		return dataDocumentoDa;
	}

	public Date getDataDocumentoA() {
		return dataDocumentoA;
	}

	public Date getDataPubblicazioneDa() {
		return dataPubblicazioneDa;
	}

	public Date getDataPubblicazioneA() {
		return dataPubblicazioneA;
	}

	public void setNrDocumento(String nrDocumento) {
		this.nrDocumento = nrDocumento;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public void setDataDocumentoDa(Date dataDocumentoDa) {
		this.dataDocumentoDa = dataDocumentoDa;
	}

	public void setDataDocumentoA(Date dataDocumentoA) {
		this.dataDocumentoA = dataDocumentoA;
	}

	public void setDataPubblicazioneDa(Date dataPubblicazioneDa) {
		this.dataPubblicazioneDa = dataPubblicazioneDa;
	}

	public void setDataPubblicazioneA(Date dataPubblicazioneA) {
		this.dataPubblicazioneA = dataPubblicazioneA;
	}

	public String getCodStatoLotto() {
		return codStatoLotto;
	}

	public void setCodStatoLotto(String codStatoLotto) {
		this.codStatoLotto = codStatoLotto;
	}

	public String getCodStatoDocumentoLotto() {
		return codStatoDocumentoLotto;
	}

	public void setCodStatoDocumentoLotto(String codStatoDocumentoLotto) {
		this.codStatoDocumentoLotto = codStatoDocumentoLotto;
	}

	public String getIdSezionale() {
		return idSezionale;
	}

	public String getCodSezionale() {
		return codSezionale;
	}

	public String getDescrizioneSezionale() {
		return descrizioneSezionale;
	}

	public void setIdSezionale(String idSezionale) {
		this.idSezionale = idSezionale;
	}

	public void setCodSezionale(String codSezionale) {
		this.codSezionale = codSezionale;
	}

	public void setDescrizioneSezionale(String descrizioneSezionale) {
		this.descrizioneSezionale = descrizioneSezionale;
	}

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public Date getDataInsA() {
		return dataInsA;
	}

	public void setDataInsA(Date dataInsA) {
		this.dataInsA = dataInsA;
	}

	public Date getDataInsDa() {
		return dataInsDa;
	}

	public void setDataInsDa(Date dataInsDa) {
		this.dataInsDa = dataInsDa;
	}

	public String getFlgEmailNotificaInviata() {
		return flgEmailNotificaInviata;
	}

	public void setFlgEmailNotificaInviata(String flgEmailNotificaInviata) {
		this.flgEmailNotificaInviata = flgEmailNotificaInviata;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

}