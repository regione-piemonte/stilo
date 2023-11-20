/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class DestinatariOsservazioniXmlBean {
	
	/**
	 * (obblig.) Indicatore del tipo di destinastario
		Valori possibili:
		UT 	= Utente
		SV	= Scrivania virtuale
		UO	= Unità organizzativa (vale a dire tutte le scrivanie virtuali ad essa afferenti)
		G	= Gruppo
		R	= Ruolo amministrativo contestualizzato 
		R@	= Soggetti interni che hanno un certo ruolo rispetto al documento / folder
	 */
	@NumeroColonna(numero = "1")
	private String tipoDestinatario;

	/**
	 * ID_UO di DMT_STRUTTURA_ORG se colonna 1=UO,
	   ID_GRUPPO di DMT_GRUPPI se colonna 1 =G
	   ID_USER di DMT_USERS se colonna 1 =UT
	   ID_SCRIVANIA di DMT_SCRIVANIE_VIRTUALI se colonna 1 =SV
	   ID_RUOLO_AMM di DMT_RUOLI_AMM se colonna 1 =R
	   COD_NATURA_REL di DMT_REL_SOGG_INT_DOC/DMT_REL_SOGG_INT_FOLDER
	 */
	@NumeroColonna(numero = "2")
	private String idDestinatario;

	@NumeroColonna(numero = "3")
	private String nomeDestinatario;
	
	/**
	 * Flag che indica la modalità di accesso da dare al destinatario
	 * FC = Full control;
	 * M = Modifica metadati e files/contenuti;
	 * MM = Modifica soli metadati; 
	 * V = Sola visualizzazione; 
	 * VM = Visualizzazione dei soli metadati (non dei files)
	 */
	@NumeroColonna(numero = "19")
	private String flgModalitaAccesso;
	
	@NumeroColonna(numero = "28")
	private String tipoNotifica;

	public String getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	
	public String getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}
	
	public String getNomeDestinatario() {
		return nomeDestinatario;
	}

	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}
	
	public String getFlgModalitaAccesso() {
		return flgModalitaAccesso;
	}
	
	public void setFlgModalitaAccesso(String flgModalitaAccesso) {
		this.flgModalitaAccesso = flgModalitaAccesso;
	}

	public String getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

}