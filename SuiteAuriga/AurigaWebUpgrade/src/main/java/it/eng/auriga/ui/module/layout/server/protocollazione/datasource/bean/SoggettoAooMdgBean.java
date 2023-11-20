/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class SoggettoAooMdgBean extends VisualBean{

	@NumeroColonna(numero = "1")
	private String idAooMdg;	
	@NumeroColonna(numero = "2")
	private String codiceRapidoAooMdg;
	@NumeroColonna(numero = "3")
	private String descrizioneAooMdg;
	@NumeroColonna(numero = "4")
	private String descrizioneEstesaAooMdg;
	
	public String getIdAooMdg() {
		return idAooMdg;
	}
	public void setIdAooMdg(String idAooMdg) {
		this.idAooMdg = idAooMdg;
	}
	public String getCodiceRapidoAooMdg() {
		return codiceRapidoAooMdg;
	}
	public void setCodiceRapidoAooMdg(String codiceRapidoAooMdg) {
		this.codiceRapidoAooMdg = codiceRapidoAooMdg;
	}
	public String getDescrizioneAooMdg() {
		return descrizioneAooMdg;
	}
	public void setDescrizioneAooMdg(String descrizioneAooMdg) {
		this.descrizioneAooMdg = descrizioneAooMdg;
	}
	public String getDescrizioneEstesaAooMdg() {
		return descrizioneEstesaAooMdg;
	}
	public void setDescrizioneEstesaAooMdg(String descrizioneEstesaAooMdg) {
		this.descrizioneEstesaAooMdg = descrizioneEstesaAooMdg;
	}
		
}
