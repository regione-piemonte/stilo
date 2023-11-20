/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class ClientConfig {

	@XmlVariabile(nome = "endpoint", tipo = TipoVariabile.SEMPLICE)
	private String endpoint;
	@XmlVariabile(nome = "metodoRicercaStrada", tipo = TipoVariabile.SEMPLICE)
	private String metodoRicercaStrada;
	@XmlVariabile(nome = "metodoEsistenzaNumCivico", tipo = TipoVariabile.SEMPLICE)
	private String metodoEsistenzaNumCivico;
	@XmlVariabile(nome = "token", tipo = TipoVariabile.SEMPLICE)
	private String token;
	@XmlVariabile(nome = "civicoObbligatorio", tipo = TipoVariabile.SEMPLICE)
	private String civicoObbligatorio;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getMetodoRicercaStrada() {
		return metodoRicercaStrada;
	}

	public void setMetodoRicercaStrada(String metodoRicercaStrada) {
		this.metodoRicercaStrada = metodoRicercaStrada;
	}

	public String getMetodoEsistenzaNumCivico() {
		return metodoEsistenzaNumCivico;
	}

	public void setMetodoEsistenzaNumCivico(String metodoEsistenzaNumCivico) {
		this.metodoEsistenzaNumCivico = metodoEsistenzaNumCivico;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getCivicoObbligatorio() {
		return civicoObbligatorio;
	}

	public void setCivicoObbligatorio(String civicoObbligatorio) {
		this.civicoObbligatorio = civicoObbligatorio;
	}

}
