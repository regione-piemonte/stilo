/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.jaxb.variabili.SezioneCache;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EseguiTaskInBean implements Serializable {

	private String instanceId;
	private String activityName;
	private String idProcess;

	private String idUd;
	private String idDocPrimario;
	private XmlDocumentoEventoBean documentoXml;
	
	private String idEvento;
	private String idEventType;
	private Boolean flgEventoDurativo;		
	private Date dataEvento;
	private Date dataFineEvento;
	private Date dataInizioEvento;	
	private String noteEvento;	
	private Boolean flgPresenzaEsito;		
	private String esito;
		
	private SezioneCache scAttributiAdd;
	
	private FilePrimarioBean filePrimario;
	private AllegatiBean allegati;
	
	
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getIdDocPrimario() {
		return idDocPrimario;
	}
	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
	public XmlDocumentoEventoBean getDocumentoXml() {
		return documentoXml;
	}
	public void setDocumentoXml(XmlDocumentoEventoBean documentoXml) {
		this.documentoXml = documentoXml;
	}
	public String getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}	
	public String getIdEventType() {
		return idEventType;
	}
	public void setIdEventType(String idEventType) {
		this.idEventType = idEventType;
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
	public String getNoteEvento() {
		return noteEvento;
	}
	public void setNoteEvento(String noteEvento) {
		this.noteEvento = noteEvento;
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
	public SezioneCache getScAttributiAdd() {
		return scAttributiAdd;
	}
	public void setScAttributiAdd(SezioneCache scAttributiAdd) {
		this.scAttributiAdd = scAttributiAdd;
	}
	public FilePrimarioBean getFilePrimario() {
		return filePrimario;
	}
	public void setFilePrimario(FilePrimarioBean filePrimario) {
		this.filePrimario = filePrimario;
	}
	public AllegatiBean getAllegati() {
		return allegati;
	}
	public void setAllegati(AllegatiBean allegati) {
		this.allegati = allegati;
	}	
			
}
