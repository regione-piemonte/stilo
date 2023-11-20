/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmcoContiImputazioneRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nomeTipoDoc;
	private String codiceCapitolo;
	private String codiceConto;
	private String descConto;
    private String entrataUscita;
    
	public String getNomeTipoDoc() {
		return nomeTipoDoc;
	}
	
	public void setNomeTipoDoc(String nomeTipoDoc) {
		this.nomeTipoDoc = nomeTipoDoc;
	}
	
	public String getCodiceCapitolo() {
		return codiceCapitolo;
	}
	
	public void setCodiceCapitolo(String codiceCapitolo) {
		this.codiceCapitolo = codiceCapitolo;
	}
	
	public String getCodiceConto() {
		return codiceConto;
	}
	
	public void setCodiceConto(String codiceConto) {
		this.codiceConto = codiceConto;
	}
	
	public String getDescConto() {
		return descConto;
	}
	
	public void setDescConto(String descConto) {
		this.descConto = descConto;
	}
	
	public String getEntrataUscita() {
		return entrataUscita;
	}
	
	public void setEntrataUscita(String entrataUscita) {
		this.entrataUscita = entrataUscita;
	}

	@Override
	public String toString() {
		return "AmcoContiImputazioneRequest [nomeTipoDoc=" + nomeTipoDoc + ", codiceCapitolo=" + codiceCapitolo
				+ ", codiceConto=" + codiceConto + ", descConto=" + descConto + ", entrataUscita=" + entrataUscita
				+ "]";
	}
	
}
