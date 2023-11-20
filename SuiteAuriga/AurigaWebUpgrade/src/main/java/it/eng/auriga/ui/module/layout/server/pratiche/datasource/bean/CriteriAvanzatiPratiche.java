/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;

public class CriteriAvanzatiPratiche implements Serializable {
		
	@XmlVariabile(nome="UseridProponente", tipo=TipoVariabile.SEMPLICE)
	private String useridProponente;
	
	@XmlVariabile(nome="ProfiloProponente", tipo=TipoVariabile.SEMPLICE)
	private String profiloProponente;
	
	@XmlVariabile(nome="SezioneHomeProponente", tipo=TipoVariabile.SEMPLICE)
	private String sezioneHomeProponente;

	@XmlVariabile(nome="IdProcess", tipo=TipoVariabile.SEMPLICE)
	private String idProcess;

	public String getUseridProponente() {
		return useridProponente;
	}

	public void setUseridProponente(String useridProponente) {
		this.useridProponente = useridProponente;
	}

	public String getProfiloProponente() {
		return profiloProponente;
	}

	public void setProfiloProponente(String profiloProponente) {
		this.profiloProponente = profiloProponente;
	}

	public String getSezioneHomeProponente() {
		return sezioneHomeProponente;
	}

	public void setSezioneHomeProponente(String sezioneHomeProponente) {
		this.sezioneHomeProponente = sezioneHomeProponente;
	}

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}	
		
}