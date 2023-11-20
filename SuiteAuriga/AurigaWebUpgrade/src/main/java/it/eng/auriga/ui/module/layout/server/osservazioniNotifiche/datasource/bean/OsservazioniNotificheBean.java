/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import it.eng.core.business.beans.AbstractBean;

public class OsservazioniNotificheBean extends AbstractBean implements Serializable {
			
	private static final long serialVersionUID = 1L;
	
	private String idOsservazioneNotifica;
	private Date   dataOsservazioneNotifica;
	private String mittenteOsservazioneNotifica;
	private String nominativoDelegato;
	private String livelloPriorita;
	private String messaggioOsservazioneNotifica;
	private String messaggioOsservazioneNotificaHTML;
	private String destinatariOsservazioneNotifica;
	private boolean flgPersonaleOsservazioneNotifica;
	private boolean flgEsclusivoOsservazioneNotifica;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getIdOsservazioneNotifica() {
		return idOsservazioneNotifica;
	}
	
	public void setIdOsservazioneNotifica(String idOsservazioneNotifica) {
		this.idOsservazioneNotifica = idOsservazioneNotifica;
	}
	
	public Date getDataOsservazioneNotifica() {
		return dataOsservazioneNotifica;
	}
	
	public void setDataOsservazioneNotifica(Date dataOsservazioneNotifica) {
		this.dataOsservazioneNotifica = dataOsservazioneNotifica;
	}
	
	public String getMittenteOsservazioneNotifica() {
		return mittenteOsservazioneNotifica;
	}
	
	public void setMittenteOsservazioneNotifica(String mittenteOsservazioneNotifica) {
		this.mittenteOsservazioneNotifica = mittenteOsservazioneNotifica;
	}
	
	public String getNominativoDelegato() {
		return nominativoDelegato;
	}
	
	public void setNominativoDelegato(String nominativoDelegato) {
		this.nominativoDelegato = nominativoDelegato;
	}
	
	public String getLivelloPriorita() {
		return livelloPriorita;
	}
	
	public void setLivelloPriorita(String livelloPriorita) {
		this.livelloPriorita = livelloPriorita;
	}
	
	public String getMessaggioOsservazioneNotifica() {
		return messaggioOsservazioneNotifica;
	}
	
	public void setMessaggioOsservazioneNotifica(String messaggioOsservazioneNotifica) {
		this.messaggioOsservazioneNotifica = messaggioOsservazioneNotifica;
	}
	
	public String getDestinatariOsservazioneNotifica() {
		return destinatariOsservazioneNotifica;
	}
	
	public void setDestinatariOsservazioneNotifica(String destinatariOsservazioneNotifica) {
		this.destinatariOsservazioneNotifica = destinatariOsservazioneNotifica;
	}
	
	public boolean isFlgPersonaleOsservazioneNotifica() {
		return flgPersonaleOsservazioneNotifica;
	}
	
	public void setFlgPersonaleOsservazioneNotifica(boolean flgPersonaleOsservazioneNotifica) {
		this.flgPersonaleOsservazioneNotifica = flgPersonaleOsservazioneNotifica;
	}
	
	public boolean isFlgEsclusivoOsservazioneNotifica() {
		return flgEsclusivoOsservazioneNotifica;
	}
	
	public void setFlgEsclusivoOsservazioneNotifica(boolean flgEsclusivoOsservazioneNotifica) {
		this.flgEsclusivoOsservazioneNotifica = flgEsclusivoOsservazioneNotifica;
	}

	public String getMessaggioOsservazioneNotificaHTML() {
		return messaggioOsservazioneNotificaHTML;
	}

	public void setMessaggioOsservazioneNotificaHTML(String messaggioOsservazioneNotificaHTML) {
		this.messaggioOsservazioneNotificaHTML = messaggioOsservazioneNotificaHTML;
	}
				
}
