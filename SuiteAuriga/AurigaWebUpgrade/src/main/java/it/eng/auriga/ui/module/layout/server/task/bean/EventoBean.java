/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttachmentInvioNotEmailBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CompilaModelloAttivitaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class EventoBean {
	
	private String instanceId;
	private String idProcess;
	private String activityName;		
	private String warningMsg;
	private String idEventType;
	private String nomeEventType;
	private String rowIdEvento;
	private String idGUIEvento;
	private String idEvento;	
	
	private String idDocType;
	private String nomeDocType;
	private Boolean flgEventoDurativo;		
	private Date dataEvento;
	private Date dataFineEvento;
	private Date dataInizioEvento;	
	private String idUd;
	private Boolean flgDocEntrata;		
	private String flgTipoProv;		
	private String tipoRegistrazione;
	private String siglaRegistrazione;			
	private String nroRegistrazione;
	private String annoRegistrazione;
	private Date dataRegistrazione;
	private String idDocPrimario;
	private String nomeFilePrimario;
	private String uriFilePrimario;
	private MimeTypeFirmaBean infoFile;
	private Boolean remoteUriFilePrimario;
	private Boolean isDocPrimarioChanged;
	private String oggetto;
	private Date dataDocumento;
	private String rifOrigineProtRicevuto;
	private String nroProtRicevuto;
	private String annoProtRicevuto;
	private Date dataProtRicevuto;
	private List<AllegatoProtocolloBean> listaAllegati;
	private String note;	
	private Boolean flgPresenzaEsito;		
	private String esito;
	private Map<String,String> valoriEsito;
	private Map<String,Map<String,String>> mappaAttrToShow;	
	
	private Map<String,Object> attributiAdd;
	private Map<String,String> tipiAttributiAdd;	
	
	private String invioNotEmailSubject;
	private String invioNotEmailBody;
	private List<SimpleValueBean> invioNotEmailDestinatari;
	private List<SimpleValueBean> invioNotEmailDestinatariCC;
	private List<SimpleValueBean> invioNotEmailDestinatariCCN;
	private String invioNotEmailIdCasellaMittente;
	private String invioNotEmailIndirizzoMittente;
	private String invioNotEmailAliasIndirizzoMittente;
	private Boolean invioNotEmailFlgPEC;
	private List<AttachmentInvioNotEmailBean> invioNotEmailAttachment;	
	private Boolean invioNotEmailFlgInvioMailXComplTask;
	private Boolean invioNotEmailFlgConfermaInvio;
	private Boolean invioNotEmailFlgCallXDettagliMail;	
	private Boolean invioNotEmailFlg;	
	
	private NuovaPropostaAtto2CompletaBean dettaglioBean;
	private List<CompilaModelloAttivitaBean> listaRecordModelli;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getWarningMsg() {
		return warningMsg;
	}

	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}

	public String getIdEventType() {
		return idEventType;
	}

	public void setIdEventType(String idEventType) {
		this.idEventType = idEventType;
	}

	public String getNomeEventType() {
		return nomeEventType;
	}

	public void setNomeEventType(String nomeEventType) {
		this.nomeEventType = nomeEventType;
	}

	public String getRowIdEvento() {
		return rowIdEvento;
	}

	public void setRowIdEvento(String rowIdEvento) {
		this.rowIdEvento = rowIdEvento;
	}

	public String getIdGUIEvento() {
		return idGUIEvento;
	}

	public void setIdGUIEvento(String idGUIEvento) {
		this.idGUIEvento = idGUIEvento;
	}

	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getNomeDocType() {
		return nomeDocType;
	}

	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}

	public Boolean getFlgEventoDurativo() {
		return flgEventoDurativo;
	}

	public void setFlgEventoDurativo(Boolean flgEventoDurativo) {
		this.flgEventoDurativo = flgEventoDurativo;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public Date getDataFineEvento() {
		return dataFineEvento;
	}

	public void setDataFineEvento(Date dataFineEvento) {
		this.dataFineEvento = dataFineEvento;
	}

	public Date getDataInizioEvento() {
		return dataInizioEvento;
	}

	public void setDataInizioEvento(Date dataInizioEvento) {
		this.dataInizioEvento = dataInizioEvento;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public Boolean getFlgDocEntrata() {
		return flgDocEntrata;
	}

	public void setFlgDocEntrata(Boolean flgDocEntrata) {
		this.flgDocEntrata = flgDocEntrata;
	}

	public String getFlgTipoProv() {
		return flgTipoProv;
	}

	public void setFlgTipoProv(String flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}

	public String getTipoRegistrazione() {
		return tipoRegistrazione;
	}

	public void setTipoRegistrazione(String tipoRegistrazione) {
		this.tipoRegistrazione = tipoRegistrazione;
	}

	public String getSiglaRegistrazione() {
		return siglaRegistrazione;
	}

	public void setSiglaRegistrazione(String siglaRegistrazione) {
		this.siglaRegistrazione = siglaRegistrazione;
	}

	public String getNroRegistrazione() {
		return nroRegistrazione;
	}

	public void setNroRegistrazione(String nroRegistrazione) {
		this.nroRegistrazione = nroRegistrazione;
	}

	public String getAnnoRegistrazione() {
		return annoRegistrazione;
	}

	public void setAnnoRegistrazione(String annoRegistrazione) {
		this.annoRegistrazione = annoRegistrazione;
	}

	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}

	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}

	public String getUriFilePrimario() {
		return uriFilePrimario;
	}

	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	public Boolean getRemoteUriFilePrimario() {
		return remoteUriFilePrimario;
	}

	public void setRemoteUriFilePrimario(Boolean remoteUriFilePrimario) {
		this.remoteUriFilePrimario = remoteUriFilePrimario;
	}

	public Boolean getIsDocPrimarioChanged() {
		return isDocPrimarioChanged;
	}

	public void setIsDocPrimarioChanged(Boolean isDocPrimarioChanged) {
		this.isDocPrimarioChanged = isDocPrimarioChanged;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getRifOrigineProtRicevuto() {
		return rifOrigineProtRicevuto;
	}

	public void setRifOrigineProtRicevuto(String rifOrigineProtRicevuto) {
		this.rifOrigineProtRicevuto = rifOrigineProtRicevuto;
	}

	public String getNroProtRicevuto() {
		return nroProtRicevuto;
	}

	public void setNroProtRicevuto(String nroProtRicevuto) {
		this.nroProtRicevuto = nroProtRicevuto;
	}

	public String getAnnoProtRicevuto() {
		return annoProtRicevuto;
	}

	public void setAnnoProtRicevuto(String annoProtRicevuto) {
		this.annoProtRicevuto = annoProtRicevuto;
	}

	public Date getDataProtRicevuto() {
		return dataProtRicevuto;
	}

	public void setDataProtRicevuto(Date dataProtRicevuto) {
		this.dataProtRicevuto = dataProtRicevuto;
	}

	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getFlgPresenzaEsito() {
		return flgPresenzaEsito;
	}

	public void setFlgPresenzaEsito(Boolean flgPresenzaEsito) {
		this.flgPresenzaEsito = flgPresenzaEsito;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public Map<String, String> getValoriEsito() {
		return valoriEsito;
	}

	public void setValoriEsito(Map<String, String> valoriEsito) {
		this.valoriEsito = valoriEsito;
	}

	public Map<String, Map<String, String>> getMappaAttrToShow() {
		return mappaAttrToShow;
	}

	public void setMappaAttrToShow(Map<String, Map<String, String>> mappaAttrToShow) {
		this.mappaAttrToShow = mappaAttrToShow;
	}

	public Map<String, Object> getAttributiAdd() {
		return attributiAdd;
	}

	public void setAttributiAdd(Map<String, Object> attributiAdd) {
		this.attributiAdd = attributiAdd;
	}

	public Map<String, String> getTipiAttributiAdd() {
		return tipiAttributiAdd;
	}

	public void setTipiAttributiAdd(Map<String, String> tipiAttributiAdd) {
		this.tipiAttributiAdd = tipiAttributiAdd;
	}

	public String getInvioNotEmailSubject() {
		return invioNotEmailSubject;
	}

	public void setInvioNotEmailSubject(String invioNotEmailSubject) {
		this.invioNotEmailSubject = invioNotEmailSubject;
	}

	public String getInvioNotEmailBody() {
		return invioNotEmailBody;
	}

	public void setInvioNotEmailBody(String invioNotEmailBody) {
		this.invioNotEmailBody = invioNotEmailBody;
	}

	public List<SimpleValueBean> getInvioNotEmailDestinatari() {
		return invioNotEmailDestinatari;
	}

	public void setInvioNotEmailDestinatari(List<SimpleValueBean> invioNotEmailDestinatari) {
		this.invioNotEmailDestinatari = invioNotEmailDestinatari;
	}

	public List<SimpleValueBean> getInvioNotEmailDestinatariCC() {
		return invioNotEmailDestinatariCC;
	}

	public void setInvioNotEmailDestinatariCC(List<SimpleValueBean> invioNotEmailDestinatariCC) {
		this.invioNotEmailDestinatariCC = invioNotEmailDestinatariCC;
	}

	public List<SimpleValueBean> getInvioNotEmailDestinatariCCN() {
		return invioNotEmailDestinatariCCN;
	}

	public void setInvioNotEmailDestinatariCCN(List<SimpleValueBean> invioNotEmailDestinatariCCN) {
		this.invioNotEmailDestinatariCCN = invioNotEmailDestinatariCCN;
	}

	public String getInvioNotEmailIdCasellaMittente() {
		return invioNotEmailIdCasellaMittente;
	}

	public void setInvioNotEmailIdCasellaMittente(String invioNotEmailIdCasellaMittente) {
		this.invioNotEmailIdCasellaMittente = invioNotEmailIdCasellaMittente;
	}

	public String getInvioNotEmailIndirizzoMittente() {
		return invioNotEmailIndirizzoMittente;
	}

	public void setInvioNotEmailIndirizzoMittente(String invioNotEmailIndirizzoMittente) {
		this.invioNotEmailIndirizzoMittente = invioNotEmailIndirizzoMittente;
	}

	public String getInvioNotEmailAliasIndirizzoMittente() {
		return invioNotEmailAliasIndirizzoMittente;
	}

	public void setInvioNotEmailAliasIndirizzoMittente(String invioNotEmailAliasIndirizzoMittente) {
		this.invioNotEmailAliasIndirizzoMittente = invioNotEmailAliasIndirizzoMittente;
	}

	public Boolean getInvioNotEmailFlgPEC() {
		return invioNotEmailFlgPEC;
	}

	public void setInvioNotEmailFlgPEC(Boolean invioNotEmailFlgPEC) {
		this.invioNotEmailFlgPEC = invioNotEmailFlgPEC;
	}

	public List<AttachmentInvioNotEmailBean> getInvioNotEmailAttachment() {
		return invioNotEmailAttachment;
	}

	public void setInvioNotEmailAttachment(List<AttachmentInvioNotEmailBean> invioNotEmailAttachment) {
		this.invioNotEmailAttachment = invioNotEmailAttachment;
	}

	public Boolean getInvioNotEmailFlgInvioMailXComplTask() {
		return invioNotEmailFlgInvioMailXComplTask;
	}

	public void setInvioNotEmailFlgInvioMailXComplTask(Boolean invioNotEmailFlgInvioMailXComplTask) {
		this.invioNotEmailFlgInvioMailXComplTask = invioNotEmailFlgInvioMailXComplTask;
	}

	public Boolean getInvioNotEmailFlgConfermaInvio() {
		return invioNotEmailFlgConfermaInvio;
	}

	public void setInvioNotEmailFlgConfermaInvio(Boolean invioNotEmailFlgConfermaInvio) {
		this.invioNotEmailFlgConfermaInvio = invioNotEmailFlgConfermaInvio;
	}

	public Boolean getInvioNotEmailFlgCallXDettagliMail() {
		return invioNotEmailFlgCallXDettagliMail;
	}

	public void setInvioNotEmailFlgCallXDettagliMail(Boolean invioNotEmailFlgCallXDettagliMail) {
		this.invioNotEmailFlgCallXDettagliMail = invioNotEmailFlgCallXDettagliMail;
	}

	public Boolean getInvioNotEmailFlg() {
		return invioNotEmailFlg;
	}

	public void setInvioNotEmailFlg(Boolean invioNotEmailFlg) {
		this.invioNotEmailFlg = invioNotEmailFlg;
	}

	public NuovaPropostaAtto2CompletaBean getDettaglioBean() {
		return dettaglioBean;
	}

	public void setDettaglioBean(NuovaPropostaAtto2CompletaBean dettaglioBean) {
		this.dettaglioBean = dettaglioBean;
	}

	public List<CompilaModelloAttivitaBean> getListaRecordModelli() {
		return listaRecordModelli;
	}

	public void setListaRecordModelli(List<CompilaModelloAttivitaBean> listaRecordModelli) {
		this.listaRecordModelli = listaRecordModelli;
	}	
					
}
