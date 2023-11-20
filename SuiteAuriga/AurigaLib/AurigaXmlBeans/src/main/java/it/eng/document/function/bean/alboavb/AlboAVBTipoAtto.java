/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBTipoAtto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idTipoAtto;

    private java.lang.String nome;

    private java.lang.String descrizione;

    private int durataPubblicazioneLegale;

	public AlboAVBTipoAtto() {
	}

	public AlboAVBTipoAtto(long idTipoAtto, String nome, String descrizione, int durataPubblicazioneLegale) {
		this.idTipoAtto = idTipoAtto;
		this.nome = nome;
		this.descrizione = descrizione;
		this.durataPubblicazioneLegale = durataPubblicazioneLegale;
	}

	public long getIdTipoAtto() {
		return idTipoAtto;
	}

	public void setIdTipoAtto(long idTipoAtto) {
		this.idTipoAtto = idTipoAtto;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

	public int getDurataPubblicazioneLegale() {
		return durataPubblicazioneLegale;
	}

	public void setDurataPubblicazioneLegale(int durataPubblicazioneLegale) {
		this.durataPubblicazioneLegale = durataPubblicazioneLegale;
	}
}
