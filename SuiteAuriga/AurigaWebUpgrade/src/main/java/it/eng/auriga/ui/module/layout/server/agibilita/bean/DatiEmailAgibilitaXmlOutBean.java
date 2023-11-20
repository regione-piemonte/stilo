/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class DatiEmailAgibilitaXmlOutBean {
	
	@XmlVariabile(nome = "IndirizzoMittenteEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String indirizzoMittenteEmailRisposta;
	
	@XmlVariabile(nome = "IdCasellaMittenteEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String idCasellaMittenteEmailRisposta;
	
	@XmlVariabile(nome = "DestinatariEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String destinatariEmailRisposta;
	
	@XmlVariabile(nome = "OggettoEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String oggettoEmailRisposta;
	
	@XmlVariabile(nome = "CorpoEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String corpoEmailRisposta;

	public String getIndirizzoMittenteEmailRisposta() {
		return indirizzoMittenteEmailRisposta;
	}

	public String getIdCasellaMittenteEmailRisposta() {
		return idCasellaMittenteEmailRisposta;
	}

	public String getDestinatariEmailRisposta() {
		return destinatariEmailRisposta;
	}

	public String getOggettoEmailRisposta() {
		return oggettoEmailRisposta;
	}

	public String getCorpoEmailRisposta() {
		return corpoEmailRisposta;
	}

	public void setIndirizzoMittenteEmailRisposta(String indirizzoMittenteEmailRisposta) {
		this.indirizzoMittenteEmailRisposta = indirizzoMittenteEmailRisposta;
	}

	public void setIdCasellaMittenteEmailRisposta(String idCasellaMittenteEmailRisposta) {
		this.idCasellaMittenteEmailRisposta = idCasellaMittenteEmailRisposta;
	}

	public void setDestinatariEmailRisposta(String destinatariEmailRisposta) {
		this.destinatariEmailRisposta = destinatariEmailRisposta;
	}

	public void setOggettoEmailRisposta(String oggettoEmailRisposta) {
		this.oggettoEmailRisposta = oggettoEmailRisposta;
	}

	public void setCorpoEmailRisposta(String corpoEmailRisposta) {
		this.corpoEmailRisposta = corpoEmailRisposta;
	}
}
