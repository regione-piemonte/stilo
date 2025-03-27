/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import java.util.HashMap;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;

/**
 * 
 * @author MMANIERO
 *
 */

public class AzioneSuccessivaIterFirmaBozzeBean {

	private HashMap<String, String> errorMessages;
	private String idUd;
	private String motivo;
	
	private Boolean flgFirma;
	private Boolean flgInviaAlVistoDi;
	private Boolean flgInvioA;
	private Boolean flgRestituzioneRedattori;
	private Boolean flgTrasmissioneMailAiDestinatari;
	private Boolean flgInviaPassoSuccessivo;
	private String idVistatore;
	private String idFirmatario;
	private String desUtenteVistatore;
	private String desUtenteFirmatario;
	private List<AssegnazioneBean> invioA;
	
	private List<ArchivioBean> listUD;

	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}

	public String getIdUd() {
		return idUd;
	}

	public String getMotivo() {
		return motivo;
	}

	public Boolean getFlgFirma() {
		return flgFirma;
	}

	public Boolean getFlgInviaAlVistoDi() {
		return flgInviaAlVistoDi;
	}

	public Boolean getFlgInvioA() {
		return flgInvioA;
	}

	public Boolean getFlgRestituzioneRedattori() {
		return flgRestituzioneRedattori;
	}

	public Boolean getFlgTrasmissioneMailAiDestinatari() {
		return flgTrasmissioneMailAiDestinatari;
	}

	public Boolean getFlgInviaPassoSuccessivo() {
		return flgInviaPassoSuccessivo;
	}

	public String getIdVistatore() {
		return idVistatore;
	}

	public String getIdFirmatario() {
		return idFirmatario;
	}

	public String getDesUtenteVistatore() {
		return desUtenteVistatore;
	}

	public String getDesUtenteFirmatario() {
		return desUtenteFirmatario;
	}

	public List<AssegnazioneBean> getInvioA() {
		return invioA;
	}

	public List<ArchivioBean> getListUD() {
		return listUD;
	}

	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public void setFlgFirma(Boolean flgFirma) {
		this.flgFirma = flgFirma;
	}

	public void setFlgInviaAlVistoDi(Boolean flgInviaAlVistoDi) {
		this.flgInviaAlVistoDi = flgInviaAlVistoDi;
	}

	public void setFlgInvioA(Boolean flgInvioA) {
		this.flgInvioA = flgInvioA;
	}

	public void setFlgRestituzioneRedattori(Boolean flgRestituzioneRedattori) {
		this.flgRestituzioneRedattori = flgRestituzioneRedattori;
	}

	public void setFlgTrasmissioneMailAiDestinatari(Boolean flgTrasmissioneMailAiDestinatari) {
		this.flgTrasmissioneMailAiDestinatari = flgTrasmissioneMailAiDestinatari;
	}

	public void setFlgInviaPassoSuccessivo(Boolean flgInviaPassoSuccessivo) {
		this.flgInviaPassoSuccessivo = flgInviaPassoSuccessivo;
	}

	public void setIdVistatore(String idVistatore) {
		this.idVistatore = idVistatore;
	}

	public void setIdFirmatario(String idFirmatario) {
		this.idFirmatario = idFirmatario;
	}

	public void setDesUtenteVistatore(String desUtenteVistatore) {
		this.desUtenteVistatore = desUtenteVistatore;
	}

	public void setDesUtenteFirmatario(String desUtenteFirmatario) {
		this.desUtenteFirmatario = desUtenteFirmatario;
	}

	public void setInvioA(List<AssegnazioneBean> invioA) {
		this.invioA = invioA;
	}

	public void setListUD(List<ArchivioBean> listUD) {
		this.listUD = listUD;
	}
	
}
