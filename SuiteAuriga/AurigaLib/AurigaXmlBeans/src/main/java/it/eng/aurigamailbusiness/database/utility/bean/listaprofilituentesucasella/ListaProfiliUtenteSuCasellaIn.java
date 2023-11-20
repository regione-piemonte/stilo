/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.annotation.Obbligatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di input per ListaProfiliUtenteSuCasella
 * @author Rametta
 *
 */
@XmlRootElement
public class ListaProfiliUtenteSuCasellaIn implements Serializable{

	private static final long serialVersionUID = -4811942655210504499L;
	@Obbligatorio
	private String idUtente;
	@Obbligatorio
	private String idCasella;
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}
	public String getIdCasella() {
		return idCasella;
	}
}
