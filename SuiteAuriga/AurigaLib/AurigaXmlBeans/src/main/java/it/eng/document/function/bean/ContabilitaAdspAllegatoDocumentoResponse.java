/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class ContabilitaAdspAllegatoDocumentoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nomeFile;
	private String titolo;
	private Integer tipoAllegato;
	private String uuid;
	
	public String getNomeFile() {
		return nomeFile;
	}
	
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public Integer getTipoAllegato() {
		return tipoAllegato;
	}
	
	public void setTipoAllegato(Integer tipoAllegato) {
		this.tipoAllegato = tipoAllegato;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspAllegatoDocumentoResponse [nomeFile=" + nomeFile + ", titolo=" + titolo
				+ ", tipoAllegato=" + tipoAllegato + ", uuid=" + uuid + "]";
	}
	
}
