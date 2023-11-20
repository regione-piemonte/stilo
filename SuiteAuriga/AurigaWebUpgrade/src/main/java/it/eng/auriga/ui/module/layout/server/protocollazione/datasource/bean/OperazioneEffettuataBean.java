/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

public class OperazioneEffettuataBean extends AbstractBean implements Serializable {
			
	private String idUdFolder;
	private String flgUdFolder;
	private Date tsOperazione;
	private String tsOperazioneXOrd;
	private String effettuatoDa;
	private String aNomeDi;
	private String tipo;
	private String dettagli;
	
	public String getIdUdFolder() {
		return idUdFolder;
	}
	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}
	public String getFlgUdFolder() {
		return flgUdFolder;
	}
	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}
	public Date getTsOperazione() {
		return tsOperazione;
	}
	public void setTsOperazione(Date tsOperazione) {
		this.tsOperazione = tsOperazione;
	}
	public String getEffettuatoDa() {
		return effettuatoDa;
	}
	public void setEffettuatoDa(String effettuatoDa) {
		this.effettuatoDa = effettuatoDa;
	}
	public String getANomeDi() {
		return aNomeDi;
	}
	public void setANomeDi(String aNomeDi) {
		this.aNomeDi = aNomeDi;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDettagli() {
		return dettagli;
	}
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}
	public String getTsOperazioneXOrd() {
		return tsOperazioneXOrd;
	}
	public void setTsOperazioneXOrd(String tsOperazioneXOrd) {
		this.tsOperazioneXOrd = tsOperazioneXOrd;
	}	
				
}
