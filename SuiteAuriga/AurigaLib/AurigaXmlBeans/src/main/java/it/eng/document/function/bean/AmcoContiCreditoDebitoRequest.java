/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class AmcoContiCreditoDebitoRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nomeTipoDoc;
	private String codiceBP;
	private String codiceCapitolo;
	private String entrataUscita;
	
	public String getNomeTipoDoc() {
		return nomeTipoDoc;
	}
	
	public void setNomeTipoDoc(String nomeTipoDoc) {
		this.nomeTipoDoc = nomeTipoDoc;
	}
	
	public String getCodiceBP() {
		return codiceBP;
	}
	
	public void setCodiceBP(String codiceBP) {
		this.codiceBP = codiceBP;
	}
	
	public String getCodiceCapitolo() {
		return codiceCapitolo;
	}
	
	public void setCodiceCapitolo(String codiceCapitolo) {
		this.codiceCapitolo = codiceCapitolo;
	}
	
	public String getEntrataUscita() {
		return entrataUscita;
	}
	
	public void setEntrataUscita(String entrataUscita) {
		this.entrataUscita = entrataUscita;
	}

	@Override
	public String toString() {
		return "AmcoContiCreditoDebitoRequest [nomeTipoDoc=" + nomeTipoDoc + ", codiceBP=" + codiceBP
				+ ", codiceCapitolo=" + codiceCapitolo + ", entrataUscita=" + entrataUscita + "]";
	}
	
}
