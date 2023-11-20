/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RequestAttributesClearoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private RequestFieldAnnoClearoBean field_anno;
	private RequestFieldSemestreClearoBean field_semestre;
	private RequestFieldNProvvedimentoClearoBean field_n_provvedimento;
	private RequestFieldDataProvvedimentoClearoBean field_data_provvedimento;
	private RequestFieldIndiceFascicoloClearoBean field_indice_fascicolo;
	private RequestFieldOggettoClearoBean field_oggetto;
	private RequestFieldProvvedimentoClearoBean field_provvedimento;
	private RequestFieldSpesaPrevistaClearoBean field_spesa_prevista;
	private RequestFieldTipoProvvedimentoClearoBean field_tipo_provvedimento;
	
	public RequestFieldAnnoClearoBean getField_anno() {
		return field_anno;
	}
	
	public void setField_anno(RequestFieldAnnoClearoBean field_anno) {
		this.field_anno = field_anno;
	}
	
	public RequestFieldSemestreClearoBean getField_semestre() {
		return field_semestre;
	}
	
	public void setField_semestre(RequestFieldSemestreClearoBean field_semestre) {
		this.field_semestre = field_semestre;
	}
	
	public RequestFieldNProvvedimentoClearoBean getField_n_provvedimento() {
		return field_n_provvedimento;
	}
	
	public void setField_n_provvedimento(RequestFieldNProvvedimentoClearoBean field_n_provvedimento) {
		this.field_n_provvedimento = field_n_provvedimento;
	}
	
	public RequestFieldDataProvvedimentoClearoBean getField_data_provvedimento() {
		return field_data_provvedimento;
	}
	
	public void setField_data_provvedimento(RequestFieldDataProvvedimentoClearoBean field_data_provvedimento) {
		this.field_data_provvedimento = field_data_provvedimento;
	}
	
	public RequestFieldIndiceFascicoloClearoBean getField_indice_fascicolo() {
		return field_indice_fascicolo;
	}
	
	public void setField_indice_fascicolo(RequestFieldIndiceFascicoloClearoBean field_indice_fascicolo) {
		this.field_indice_fascicolo = field_indice_fascicolo;
	}
	
	public RequestFieldOggettoClearoBean getField_oggetto() {
		return field_oggetto;
	}
	
	public void setField_oggetto(RequestFieldOggettoClearoBean field_oggetto) {
		this.field_oggetto = field_oggetto;
	}
	
	public RequestFieldProvvedimentoClearoBean getField_provvedimento() {
		return field_provvedimento;
	}
	
	public void setField_provvedimento(RequestFieldProvvedimentoClearoBean field_provvedimento) {
		this.field_provvedimento = field_provvedimento;
	}
	
	public RequestFieldSpesaPrevistaClearoBean getField_spesa_prevista() {
		return field_spesa_prevista;
	}
	
	public void setField_spesa_prevista(RequestFieldSpesaPrevistaClearoBean field_spesa_prevista) {
		this.field_spesa_prevista = field_spesa_prevista;
	}
	
	public RequestFieldTipoProvvedimentoClearoBean getField_tipo_provvedimento() {
		return field_tipo_provvedimento;
	}
	
	public void setField_tipo_provvedimento(RequestFieldTipoProvvedimentoClearoBean field_tipo_provvedimento) {
		this.field_tipo_provvedimento = field_tipo_provvedimento;
	}

	@Override
	public String toString() {
		return "RequestAttributesClearoBean [field_anno=" + field_anno + ", field_semestre=" + field_semestre
				+ ", field_n_provvedimento=" + field_n_provvedimento + ", field_data_provvedimento="
				+ field_data_provvedimento + ", field_indice_fascicolo=" + field_indice_fascicolo + ", field_oggetto="
				+ field_oggetto + ", field_provvedimento=" + field_provvedimento + ", field_spesa_prevista="
				+ field_spesa_prevista + ", field_tipo_provvedimento=" + field_tipo_provvedimento + "]";
	}
	
}
