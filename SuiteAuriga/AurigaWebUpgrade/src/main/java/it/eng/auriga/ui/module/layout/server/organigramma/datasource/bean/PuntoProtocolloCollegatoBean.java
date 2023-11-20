/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class PuntoProtocolloCollegatoBean implements Comparable<PuntoProtocolloCollegatoBean>{

	@NumeroColonna(numero = "2")
	private String idUo;
	@NumeroColonna(numero = "3")
	private String codRapido;
	@NumeroColonna(numero = "4")
	private String descrizione;
	@NumeroColonna(numero = "5")
	private String descrizioneEstesa;
	@NumeroColonna(numero = "6")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioValidazione;
	@NumeroColonna(numero = "7")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFineValidazione;
	
	private String organigramma;

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public Date getDataInizioValidazione() {
		return dataInizioValidazione;
	}

	public void setDataInizioValidazione(Date dataInizioValidazione) {
		this.dataInizioValidazione = dataInizioValidazione;
	}

	public Date getDataFineValidazione() {
		return dataFineValidazione;
	}

	public void setDataFineValidazione(Date dataFineValidazione) {
		this.dataFineValidazione = dataFineValidazione;
	}

	public String getOrganigramma() {
		return organigramma;
	}

	public void setOrganigramma(String organigramma) {
		this.organigramma = organigramma;
	}

	@Override
	public int compareTo(PuntoProtocolloCollegatoBean o) {
		return idUo.compareTo(o.getIdUo());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof PuntoProtocolloCollegatoBean))
			return false;
		PuntoProtocolloCollegatoBean objConv = (PuntoProtocolloCollegatoBean) obj;
		return idUo.equals(objConv.getIdUo());
	}

}
