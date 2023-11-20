/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import java.util.List;


public class XmlDatiAttoOutBean {
	
	@XmlVariabile(nome="#DesOgg", tipo=TipoVariabile.SEMPLICE)
	private String desOgg;
	@XmlVariabile(nome="#FlgAttoChiuso", tipo=TipoVariabile.SEMPLICE)
	private String flgAttoChiuso;
	@XmlVariabile(nome="#RegNumIniziale.Tipo", tipo=TipoVariabile.SEMPLICE)
	private String tipoRegNumIniziale;
	@XmlVariabile(nome="#RegNumIniziale.Registro", tipo=TipoVariabile.SEMPLICE)
	private String registroRegNumIniziale;
	@XmlVariabile(nome="#RegNumIniziale.Anno", tipo=TipoVariabile.SEMPLICE)
	private String annoRegNumIniziale;
	@XmlVariabile(nome="#RegNumIniziale.Nro", tipo=TipoVariabile.SEMPLICE)
	private String nroRegNumIniziale;
	@TipoData(tipo=Tipo.DATA)
	@XmlVariabile(nome="#RegNumIniziale.TsRegistrazione", tipo=TipoVariabile.SEMPLICE)
	private String tsRegistrazioneRegNumIniziale;
	@XmlVariabile(nome="#RegNumIniziale.DesUser", tipo=TipoVariabile.SEMPLICE)
	private String desUserRegNumIniziale;
	@XmlVariabile(nome="#RegNumFinale.Tipo", tipo=TipoVariabile.SEMPLICE)
	private String tipoRegNumFinale;
	@XmlVariabile(nome="#RegNumFinale.Registro", tipo=TipoVariabile.SEMPLICE)
	private String registroRegNumFinale;
	@XmlVariabile(nome="#RegNumFinale.Anno", tipo=TipoVariabile.SEMPLICE)
	private String annoRegNumFinale;
	@XmlVariabile(nome="#RegNumFinale.Nro", tipo=TipoVariabile.SEMPLICE)
	private String nroRegNumFinale;
	@TipoData(tipo=Tipo.DATA)
	@XmlVariabile(nome="#RegNumFinale.TsRegistrazione", tipo=TipoVariabile.SEMPLICE)
	private String tsRegistrazioneRegNumFinale;
	@XmlVariabile(nome="#RegNumFinale.DesUser", tipo=TipoVariabile.SEMPLICE)
	private String desUserRegNumFinale;
	@XmlVariabile(nome="#@RegistrazioniAutorizzate", tipo=TipoVariabile.LISTA)
	private List<RegDaAnnullareBean> registrazioniAutorizzate;
	
	public String getDesOgg() {
		return desOgg;
	}
	public void setDesOgg(String desOgg) {
		this.desOgg = desOgg;
	}
	public String getFlgAttoChiuso() {
		return flgAttoChiuso;
	}
	public void setFlgAttoChiuso(String flgAttoChiuso) {
		this.flgAttoChiuso = flgAttoChiuso;
	}
	public String getTipoRegNumIniziale() {
		return tipoRegNumIniziale;
	}
	public void setTipoRegNumIniziale(String tipoRegNumIniziale) {
		this.tipoRegNumIniziale = tipoRegNumIniziale;
	}
	public String getRegistroRegNumIniziale() {
		return registroRegNumIniziale;
	}
	public void setRegistroRegNumIniziale(String registroRegNumIniziale) {
		this.registroRegNumIniziale = registroRegNumIniziale;
	}
	public String getAnnoRegNumIniziale() {
		return annoRegNumIniziale;
	}
	public void setAnnoRegNumIniziale(String annoRegNumIniziale) {
		this.annoRegNumIniziale = annoRegNumIniziale;
	}
	public String getNroRegNumIniziale() {
		return nroRegNumIniziale;
	}
	public void setNroRegNumIniziale(String nroRegNumIniziale) {
		this.nroRegNumIniziale = nroRegNumIniziale;
	}
	public String getTsRegistrazioneRegNumIniziale() {
		return tsRegistrazioneRegNumIniziale;
	}
	public void setTsRegistrazioneRegNumIniziale(
			String tsRegistrazioneRegNumIniziale) {
		this.tsRegistrazioneRegNumIniziale = tsRegistrazioneRegNumIniziale;
	}
	public String getDesUserRegNumIniziale() {
		return desUserRegNumIniziale;
	}
	public void setDesUserRegNumIniziale(String desUserRegNumIniziale) {
		this.desUserRegNumIniziale = desUserRegNumIniziale;
	}
	public String getTipoRegNumFinale() {
		return tipoRegNumFinale;
	}
	public void setTipoRegNumFinale(String tipoRegNumFinale) {
		this.tipoRegNumFinale = tipoRegNumFinale;
	}
	public String getRegistroRegNumFinale() {
		return registroRegNumFinale;
	}
	public void setRegistroRegNumFinale(String registroRegNumFinale) {
		this.registroRegNumFinale = registroRegNumFinale;
	}
	public String getAnnoRegNumFinale() {
		return annoRegNumFinale;
	}
	public void setAnnoRegNumFinale(String annoRegNumFinale) {
		this.annoRegNumFinale = annoRegNumFinale;
	}
	public String getNroRegNumFinale() {
		return nroRegNumFinale;
	}
	public void setNroRegNumFinale(String nroRegNumFinale) {
		this.nroRegNumFinale = nroRegNumFinale;
	}
	public String getTsRegistrazioneRegNumFinale() {
		return tsRegistrazioneRegNumFinale;
	}
	public void setTsRegistrazioneRegNumFinale(String tsRegistrazioneRegNumFinale) {
		this.tsRegistrazioneRegNumFinale = tsRegistrazioneRegNumFinale;
	}
	public String getDesUserRegNumFinale() {
		return desUserRegNumFinale;
	}
	public void setDesUserRegNumFinale(String desUserRegNumFinale) {
		this.desUserRegNumFinale = desUserRegNumFinale;
	}
	public List<RegDaAnnullareBean> getRegistrazioniAutorizzate() {
		return registrazioniAutorizzate;
	}
	public void setRegistrazioniAutorizzate(
			List<RegDaAnnullareBean> registrazioniAutorizzate) {
		this.registrazioniAutorizzate = registrazioniAutorizzate;
	}
	
}
