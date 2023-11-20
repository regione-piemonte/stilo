/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProsaDatiProtocollo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String aoo;
	private String casellaEmail;
	private Long idProfilo;
	private String modalita;
	private String registro;
	private boolean stampaTimbro;

	public String getAoo() {
		return aoo;
	}

	public void setAoo(String aoo) {
		this.aoo = aoo;
	}

	public String getCasellaEmail() {
		return casellaEmail;
	}

	public void setCasellaEmail(String casellaEmail) {
		this.casellaEmail = casellaEmail;
	}

	public Long getIdProfilo() {
		return idProfilo;
	}

	public void setIdProfilo(Long idProfilo) {
		this.idProfilo = idProfilo;
	}

	public String getModalita() {
		return modalita;
	}

	public void setModalita(String modalita) {
		this.modalita = modalita;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public boolean isStampaTimbro() {
		return stampaTimbro;
	}

	public void setStampaTimbro(boolean stampaTimbro) {
		this.stampaTimbro = stampaTimbro;
	}

}
