/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

/**
 * 
 * @author DANCRIST
 *
 */

public class ProponentiBean {

	private String idUo;
	private String codRapido;
	private String descrizione;
	private String descrizioneEstesa;
	private String flgUfficioGare;
	private String ufficioProponente;
	private String organigramma;
	private String organigrammaFromLoadDett;
	private List<ScrivaniaProponentiBean> listaRdP;
	private List<ScrivaniaProponentiBean> listaDirigenti;
	private List<ScrivaniaProponentiBean> listaDirettori;
	
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
	public String getFlgUfficioGare() {
		return flgUfficioGare;
	}
	public void setFlgUfficioGare(String flgUfficioGare) {
		this.flgUfficioGare = flgUfficioGare;
	}
	public String getUfficioProponente() {
		return ufficioProponente;
	}
	public void setUfficioProponente(String ufficioProponente) {
		this.ufficioProponente = ufficioProponente;
	}
	public String getOrganigramma() {
		return organigramma;
	}
	public void setOrganigramma(String organigramma) {
		this.organigramma = organigramma;
	}
	public String getOrganigrammaFromLoadDett() {
		return organigrammaFromLoadDett;
	}
	public void setOrganigrammaFromLoadDett(String organigrammaFromLoadDett) {
		this.organigrammaFromLoadDett = organigrammaFromLoadDett;
	}
	public List<ScrivaniaProponentiBean> getListaRdP() {
		return listaRdP;
	}
	public void setListaRdP(List<ScrivaniaProponentiBean> listaRdP) {
		this.listaRdP = listaRdP;
	}
	public List<ScrivaniaProponentiBean> getListaDirigenti() {
		return listaDirigenti;
	}
	public void setListaDirigenti(List<ScrivaniaProponentiBean> listaDirigenti) {
		this.listaDirigenti = listaDirigenti;
	}
	public List<ScrivaniaProponentiBean> getListaDirettori() {
		return listaDirettori;
	}
	public void setListaDirettori(List<ScrivaniaProponentiBean> listaDirettori) {
		this.listaDirettori = listaDirettori;
	}

}