/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

/**
 * 
 * @author dbe4235
 *
 */

public class SelezionaAssegnatarioTSOBean extends OperazioneMassivaTSOBean implements Serializable {
			
	private static final long serialVersionUID = 1L;
		
	private String idAssegnatario;
	
	private String descrizioneAssegnatario;

	public String getIdAssegnatario() {
		return idAssegnatario;
	}

	public String getDescrizioneAssegnatario() {
		return descrizioneAssegnatario;
	}

	public void setIdAssegnatario(String idAssegnatario) {
		this.idAssegnatario = idAssegnatario;
	}

	public void setDescrizioneAssegnatario(String descrizioneAssegnatario) {
		this.descrizioneAssegnatario = descrizioneAssegnatario;
	}
	
	
}