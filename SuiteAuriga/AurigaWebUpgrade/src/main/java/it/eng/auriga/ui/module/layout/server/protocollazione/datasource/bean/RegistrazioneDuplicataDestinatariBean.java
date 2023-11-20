/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.TipoMittente;

public class RegistrazioneDuplicataDestinatariBean {
	private static final long serialVersionUID = 5611310655086324290L;
	@NumeroColonna(numero = "1")
	private String idRubrica;
	@NumeroColonna(numero = "3")
	private TipoMittente tipoSoggetto;
	@NumeroColonna(numero = "4")
	private String denominazioneCognome;
	@NumeroColonna(numero = "5")
	private String nome;
	@NumeroColonna(numero = "6")
	private String codiceFiscale;
	@NumeroColonna(numero = "7")
	private String gruppo;
	
	public String getDenominazioneCognome() {
		return denominazioneCognome;
	}
	public void setDenominazioneCognome(String denominazioneCognome) {
		this.denominazioneCognome = denominazioneCognome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setTipoSoggetto(TipoMittente tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public TipoMittente getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}
	public String getIdRubrica() {
		return idRubrica;
	}
	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}
	public String getGruppo() {
		return gruppo;
	}
}
