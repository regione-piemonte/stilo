/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FolderCustom {

	@NumeroColonna(numero = "1")
	private String id;
	
	@NumeroColonna(numero = "2")
	private String path;
	
	@NumeroColonna(numero = "3")
	private String tipoRelazione;
	
	@NumeroColonna(numero = "4")
	private String flgCapofila;
	
	@NumeroColonna(numero = "5")
	private String estremiDocCapofila;
	
	@NumeroColonna(numero = "6")
	private String nroSecondario;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTipoRelazione() {
		return tipoRelazione;
	}

	public void setTipoRelazione(String tipoRelazione) {
		this.tipoRelazione = tipoRelazione;
	}

	public String getFlgCapofila() {
		return flgCapofila;
	}

	public void setFlgCapofila(String flgCapofila) {
		this.flgCapofila = flgCapofila;
	}

	public String getEstremiDocCapofila() {
		return estremiDocCapofila;
	}

	public void setEstremiDocCapofila(String estremiDocCapofila) {
		this.estremiDocCapofila = estremiDocCapofila;
	}

	public String getNroSecondario() {
		return nroSecondario;
	}

	public void setNroSecondario(String nroSecondario) {
		this.nroSecondario = nroSecondario;
	}
}
