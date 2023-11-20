/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

public class ArchivioTreeNodeBean extends TreeNodeBean {	
	
	private String nomeFascicolo;	
	private Integer annoFascicolo;
	private String idClassifica;
	private String indiceClassifica;
	private String nroFascicolo;
	private String nroSottofascicolo;
	private String nroInserto;
	private String livelloRiservatezza;
	private String percorsoNome;
	private String nroSecondario;
	private String estremiDocCapofila;

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}
	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}
	public Integer getAnnoFascicolo() {
		return annoFascicolo;
	}
	public void setAnnoFascicolo(Integer annoFascicolo) {
		this.annoFascicolo = annoFascicolo;
	}
	public String getIdClassifica() {
		return idClassifica;
	}
	public void setIdClassifica(String idClassifica) {
		this.idClassifica = idClassifica;
	}
	public String getIndiceClassifica() {
		return indiceClassifica;
	}
	public void setIndiceClassifica(String indiceClassifica) {
		this.indiceClassifica = indiceClassifica;
	}
	public String getNroFascicolo() {
		return nroFascicolo;
	}
	public void setNroFascicolo(String nroFascicolo) {
		this.nroFascicolo = nroFascicolo;
	}
	public String getNroSottofascicolo() {
		return nroSottofascicolo;
	}
	public void setNroSottofascicolo(String nroSottofascicolo) {
		this.nroSottofascicolo = nroSottofascicolo;
	}
	public String getNroInserto() {
		return nroInserto;
	}
	public void setNroInserto(String nroInserto) {
		this.nroInserto = nroInserto;
	}
	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}
	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}
	public String getPercorsoNome() {
		return percorsoNome;
	}
	public void setPercorsoNome(String percorsoNome) {
		this.percorsoNome = percorsoNome;
	}
	public String getNroSecondario() {
		return nroSecondario;
	}
	public void setNroSecondario(String nroSecondario) {
		this.nroSecondario = nroSecondario;
	}
	public String getEstremiDocCapofila() {
		return estremiDocCapofila;
	}
	public void setEstremiDocCapofila(String estremiDocCapofila) {
		this.estremiDocCapofila = estremiDocCapofila;
	}
	
}
