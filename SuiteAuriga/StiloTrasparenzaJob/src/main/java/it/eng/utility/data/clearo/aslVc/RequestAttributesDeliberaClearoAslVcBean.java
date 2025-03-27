/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.data.clearo.aslVc;

import java.io.Serializable;

public class RequestAttributesDeliberaClearoAslVcBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private RequestFieldAnnoClearoAslVcBean field_anno;
	private RequestFieldMeseClearoAslVcBean field_mese;
	private RequestFieldTipologiaClearoAslVcBean field_tipologia;
	private RequestFieldDeliberaNumeroClearoAslVcBean field_delibera_numero;
	private RequestFieldDeliberaDelClearoAslVcBean field_delibera_del;
	private RequestFieldOggettoClearoAslVcBean field_oggetto;
	private RequestFieldDecorrenzaDalClearoAslVcBean field_decorrenza_dal;
	private RequestFieldDecorrenzaAlClearoAslVcBean field_decorrenza_al;
	private RequestFieldArtOggettoClearoAslVcBean field_art23_oggetto;
	private RequestFieldArtTipologiaClearoAslVcBean field_art23_tipologia;
	private RequestFieldArtContenutoClearoAslVcBean field_art23_contenuto;
	private RequestFieldArtEstremiClearoAslVcBean field_art23_estremi;
	private RequestFieldArtImportoClearoAslVcBean field_art23_importo;
	private RequestFieldArtNoteClearoAslVcBean field_art23_note;
	
	public RequestFieldAnnoClearoAslVcBean getField_anno() {
		return field_anno;
	}
	
	public void setField_anno(RequestFieldAnnoClearoAslVcBean field_anno) {
		this.field_anno = field_anno;
	}
	
	public RequestFieldMeseClearoAslVcBean getField_mese() {
		return field_mese;
	}
	
	public void setField_mese(RequestFieldMeseClearoAslVcBean field_mese) {
		this.field_mese = field_mese;
	}
	
	public RequestFieldTipologiaClearoAslVcBean getField_tipologia() {
		return field_tipologia;
	}
	
	public void setField_tipologia(RequestFieldTipologiaClearoAslVcBean field_tipologia) {
		this.field_tipologia = field_tipologia;
	}
	
	public RequestFieldDeliberaNumeroClearoAslVcBean getField_delibera_numero() {
		return field_delibera_numero;
	}
	
	public void setField_delibera_numero(RequestFieldDeliberaNumeroClearoAslVcBean field_delibera_numero) {
		this.field_delibera_numero = field_delibera_numero;
	}
	
	public RequestFieldDeliberaDelClearoAslVcBean getField_delibera_del() {
		return field_delibera_del;
	}
	
	public void setField_delibera_del(RequestFieldDeliberaDelClearoAslVcBean field_delibera_del) {
		this.field_delibera_del = field_delibera_del;
	}
	
	public RequestFieldOggettoClearoAslVcBean getField_oggetto() {
		return field_oggetto;
	}
	
	public void setField_oggetto(RequestFieldOggettoClearoAslVcBean field_oggetto) {
		this.field_oggetto = field_oggetto;
	}
	
	public RequestFieldDecorrenzaDalClearoAslVcBean getField_decorrenza_dal() {
		return field_decorrenza_dal;
	}
	
	public void setField_decorrenza_dal(RequestFieldDecorrenzaDalClearoAslVcBean field_decorrenza_dal) {
		this.field_decorrenza_dal = field_decorrenza_dal;
	}
	
	public RequestFieldDecorrenzaAlClearoAslVcBean getField_decorrenza_al() {
		return field_decorrenza_al;
	}
	
	public void setField_decorrenza_al(RequestFieldDecorrenzaAlClearoAslVcBean field_decorrenza_al) {
		this.field_decorrenza_al = field_decorrenza_al;
	}
	
	public RequestFieldArtOggettoClearoAslVcBean getField_art23_oggetto() {
		return field_art23_oggetto;
	}
	
	public void setField_art23_oggetto(RequestFieldArtOggettoClearoAslVcBean field_art23_oggetto) {
		this.field_art23_oggetto = field_art23_oggetto;
	}
	
	public RequestFieldArtTipologiaClearoAslVcBean getField_art23_tipologia() {
		return field_art23_tipologia;
	}
	
	public void setField_art23_tipologia(RequestFieldArtTipologiaClearoAslVcBean field_art23_tipologia) {
		this.field_art23_tipologia = field_art23_tipologia;
	}
	
	public RequestFieldArtContenutoClearoAslVcBean getField_art23_contenuto() {
		return field_art23_contenuto;
	}
	
	public void setField_art23_contenuto(RequestFieldArtContenutoClearoAslVcBean field_art23_contenuto) {
		this.field_art23_contenuto = field_art23_contenuto;
	}
	
	public RequestFieldArtEstremiClearoAslVcBean getField_art23_estremi() {
		return field_art23_estremi;
	}
	
	public void setField_art23_estremi(RequestFieldArtEstremiClearoAslVcBean field_art23_estremi) {
		this.field_art23_estremi = field_art23_estremi;
	}
	
	public RequestFieldArtImportoClearoAslVcBean getField_art23_importo() {
		return field_art23_importo;
	}
	
	public void setField_art23_importo(RequestFieldArtImportoClearoAslVcBean field_art23_importo) {
		this.field_art23_importo = field_art23_importo;
	}
	
	public RequestFieldArtNoteClearoAslVcBean getField_art23_note() {
		return field_art23_note;
	}
	
	public void setField_art23_note(RequestFieldArtNoteClearoAslVcBean field_art23_note) {
		this.field_art23_note = field_art23_note;
	}

	@Override
	public String toString() {
		return "RequestAttributesDeliberaClearoAslVcBean [field_anno=" + field_anno + ", field_mese=" + field_mese
				+ ", field_tipologia=" + field_tipologia + ", field_delibera_numero=" + field_delibera_numero
				+ ", field_delibera_del=" + field_delibera_del + ", field_oggetto=" + field_oggetto
				+ ", field_decorrenza_dal=" + field_decorrenza_dal + ", field_decorrenza_al=" + field_decorrenza_al
				+ ", field_art23_oggetto=" + field_art23_oggetto + ", field_art23_tipologia=" + field_art23_tipologia
				+ ", field_art23_contenuto=" + field_art23_contenuto + ", field_art23_estremi=" + field_art23_estremi
				+ ", field_art23_importo=" + field_art23_importo + ", field_art23_note=" + field_art23_note + "]";
	}
	
}
