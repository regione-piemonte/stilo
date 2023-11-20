/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class RelEventTypeProcessBean {

	private String idEventTypeIn;
	private String desEventTypeIn;
	private String nomeProcessType;
	private String categoriaIO;
	private Boolean flgDurativoIO;
	private Boolean flgVldXTuttiProcAmmIO;
	private String idProcessTypeIO;
	private String nomeProcessTypeIO;
	private String idProcessIO;
	private Boolean flgModTerminiProcIO;
	private Boolean flgAssociatoTipoDocIO;
	private String idDocTypeIO;
	private String nomeDocTypeIO;
	private String cIProvEventTypeIO;
	private String codApplicazioneIO;
	private String codIstApplicazioneIO;
	private Boolean flgInclAnnullatiIO;
	private List<ProcessTypesXMLBean> listProcessTypesXMLBean;

	public String getIdEventTypeIn() {
		return idEventTypeIn;
	}

	public void setIdEventTypeIn(String idEventTypeIn) {
		this.idEventTypeIn = idEventTypeIn;
	}

	public String getDesEventTypeIn() {
		return desEventTypeIn;
	}

	public void setDesEventTypeIn(String desEventTypeIn) {
		this.desEventTypeIn = desEventTypeIn;
	}

	public String getNomeProcessType() {
		return nomeProcessType;
	}

	public void setNomeProcessType(String nomeProcessType) {
		this.nomeProcessType = nomeProcessType;
	}

	public String getCategoriaIO() {
		return categoriaIO;
	}

	public void setCategoriaIO(String categoriaIO) {
		this.categoriaIO = categoriaIO;
	}

	public Boolean getFlgDurativoIO() {
		return flgDurativoIO;
	}

	public void setFlgDurativoIO(Boolean flgDurativoIO) {
		this.flgDurativoIO = flgDurativoIO;
	}

	public Boolean getFlgVldXTuttiProcAmmIO() {
		return flgVldXTuttiProcAmmIO;
	}

	public void setFlgVldXTuttiProcAmmIO(Boolean flgVldXTuttiProcAmmIO) {
		this.flgVldXTuttiProcAmmIO = flgVldXTuttiProcAmmIO;
	}

	public String getIdProcessTypeIO() {
		return idProcessTypeIO;
	}

	public void setIdProcessTypeIO(String idProcessTypeIO) {
		this.idProcessTypeIO = idProcessTypeIO;
	}

	public String getNomeProcessTypeIO() {
		return nomeProcessTypeIO;
	}

	public void setNomeProcessTypeIO(String nomeProcessTypeIO) {
		this.nomeProcessTypeIO = nomeProcessTypeIO;
	}

	public String getIdProcessIO() {
		return idProcessIO;
	}

	public void setIdProcessIO(String idProcessIO) {
		this.idProcessIO = idProcessIO;
	}

	public Boolean getFlgModTerminiProcIO() {
		return flgModTerminiProcIO;
	}

	public void setFlgModTerminiProcIO(Boolean flgModTerminiProcIO) {
		this.flgModTerminiProcIO = flgModTerminiProcIO;
	}

	public Boolean getFlgAssociatoTipoDocIO() {
		return flgAssociatoTipoDocIO;
	}

	public void setFlgAssociatoTipoDocIO(Boolean flgAssociatoTipoDocIO) {
		this.flgAssociatoTipoDocIO = flgAssociatoTipoDocIO;
	}

	public String getIdDocTypeIO() {
		return idDocTypeIO;
	}

	public void setIdDocTypeIO(String idDocTypeIO) {
		this.idDocTypeIO = idDocTypeIO;
	}

	public String getNomeDocTypeIO() {
		return nomeDocTypeIO;
	}

	public void setNomeDocTypeIO(String nomeDocTypeIO) {
		this.nomeDocTypeIO = nomeDocTypeIO;
	}

	public String getcIProvEventTypeIO() {
		return cIProvEventTypeIO;
	}

	public void setcIProvEventTypeIO(String cIProvEventTypeIO) {
		this.cIProvEventTypeIO = cIProvEventTypeIO;
	}

	public String getCodApplicazioneIO() {
		return codApplicazioneIO;
	}

	public void setCodApplicazioneIO(String codApplicazioneIO) {
		this.codApplicazioneIO = codApplicazioneIO;
	}

	public String getCodIstApplicazioneIO() {
		return codIstApplicazioneIO;
	}

	public void setCodIstApplicazioneIO(String codIstApplicazioneIO) {
		this.codIstApplicazioneIO = codIstApplicazioneIO;
	}

	public Boolean getFlgInclAnnullatiIO() {
		return flgInclAnnullatiIO;
	}

	public void setFlgInclAnnullatiIO(Boolean flgInclAnnullatiIO) {
		this.flgInclAnnullatiIO = flgInclAnnullatiIO;
	}

	public List<ProcessTypesXMLBean> getListProcessTypesXMLBean() {
		return listProcessTypesXMLBean;
	}

	public void setListProcessTypesXMLBean(List<ProcessTypesXMLBean> listProcessTypesXMLBean) {
		this.listProcessTypesXMLBean = listProcessTypesXMLBean;
	}

}
