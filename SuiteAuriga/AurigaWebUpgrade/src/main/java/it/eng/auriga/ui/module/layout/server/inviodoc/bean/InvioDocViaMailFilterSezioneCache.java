/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class InvioDocViaMailFilterSezioneCache {

	@XmlVariabile(nome = "CodApplRichiedenti", tipo = XmlVariabile.TipoVariabile.SEMPLICE)
	private String codApplRichiedenti;

	@XmlVariabile(nome = "IdProvRichiesta", tipo = XmlVariabile.TipoVariabile.SEMPLICE)
	private String idProvRichiesta;

	@XmlVariabile(nome = "XMLRichiesta", tipo = XmlVariabile.TipoVariabile.SEMPLICE)
	private String xmlRichiestaSezCache;

	@XmlVariabile(nome = "StatiRichesta", tipo = XmlVariabile.TipoVariabile.SEMPLICE)
	private String statiRichesta;

	@XmlVariabile(nome = "StatiEmail", tipo = XmlVariabile.TipoVariabile.SEMPLICE)
	private String statiEmail;

	@XmlVariabile(nome = "TsRichiestaDa", tipo = XmlVariabile.TipoVariabile.SEMPLICE)
	@TipoData(tipo = TipoData.Tipo.DATA)
	private String tsRichiestaDa;

	@XmlVariabile(nome = "TsRichiestaA", tipo = XmlVariabile.TipoVariabile.SEMPLICE)
	@TipoData(tipo = TipoData.Tipo.DATA)
	private String tsRichiestaA;

	@XmlVariabile(nome = "TsInvioEmailDa", tipo = XmlVariabile.TipoVariabile.SEMPLICE)
	@TipoData(tipo = TipoData.Tipo.DATA)
	private String tsInvioEmailDa;

	@XmlVariabile(nome = "TsInvioEmailA", tipo = XmlVariabile.TipoVariabile.SEMPLICE)
	@TipoData(tipo = TipoData.Tipo.DATA)
	private String tsInvioEmailA;

	public String getCodApplRichiedenti() {
		return codApplRichiedenti;
	}

	public void setCodApplRichiedenti(String codApplRichiedenti) {
		this.codApplRichiedenti = codApplRichiedenti;
	}

	public String getIdProvRichiesta() {
		return idProvRichiesta;
	}

	public void setIdProvRichiesta(String idProvRichiesta) {
		this.idProvRichiesta = idProvRichiesta;
	}

	public String getStatiRichesta() {
		return statiRichesta;
	}

	public void setStatiRichesta(String statiRichesta) {
		this.statiRichesta = statiRichesta;
	}

	public String getStatiEmail() {
		return statiEmail;
	}

	public void setStatiEmail(String statiEmail) {
		this.statiEmail = statiEmail;
	}

	public String getTsRichiestaDa() {
		return tsRichiestaDa;
	}

	public void setTsRichiestaDa(String tsRichiestaDa) {
		this.tsRichiestaDa = tsRichiestaDa;
	}

	public String getTsRichiestaA() {
		return tsRichiestaA;
	}

	public void setTsRichiestaA(String tsRichiestaA) {
		this.tsRichiestaA = tsRichiestaA;
	}

	public String getTsInvioEmailDa() {
		return tsInvioEmailDa;
	}

	public void setTsInvioEmailDa(String tsInvioEmailDa) {
		this.tsInvioEmailDa = tsInvioEmailDa;
	}

	public String getTsInvioEmailA() {
		return tsInvioEmailA;
	}

	public void setTsInvioEmailA(String tsInvioEmailA) {
		this.tsInvioEmailA = tsInvioEmailA;
	}

	public String getXmlRichiestaSezCache() {
		return xmlRichiestaSezCache;
	}

	public void setXmlRichiestaSezCache(String xmlRichiestaSezCache) {
		this.xmlRichiestaSezCache = xmlRichiestaSezCache;
	}

}
