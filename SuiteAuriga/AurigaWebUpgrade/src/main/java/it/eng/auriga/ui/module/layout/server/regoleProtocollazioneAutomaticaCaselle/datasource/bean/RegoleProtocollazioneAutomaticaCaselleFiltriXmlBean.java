package it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean;

import java.io.Serializable;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class RegoleProtocollazioneAutomaticaCaselleFiltriXmlBean implements Serializable {
	
	@XmlVariabile(nome="NomeRegola", tipo=TipoVariabile.SEMPLICE)
	private String nomeRegola;
	
	@XmlVariabile(nome="DesRegola", tipo=TipoVariabile.SEMPLICE)
	private String descrizioneRegola;
	
	
	@XmlVariabile(nome="Stati", tipo=TipoVariabile.SEMPLICE)
	private String statiRegola;


	public String getNomeRegola() {
		return nomeRegola;
	}


	public void setNomeRegola(String nomeRegola) {
		this.nomeRegola = nomeRegola;
	}


	public String getStatiRegola() {
		return statiRegola;
	}


	public void setStatiRegola(String statiRegola) {
		this.statiRegola = statiRegola;
	}


	public String getDescrizioneRegola() {
		return descrizioneRegola;
	}


	public void setDescrizioneRegola(String descrizioneRegola) {
		this.descrizioneRegola = descrizioneRegola;
	}
	
}
