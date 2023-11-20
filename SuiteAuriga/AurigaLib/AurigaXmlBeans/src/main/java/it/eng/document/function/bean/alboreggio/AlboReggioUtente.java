/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboReggioUtente  implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String nome;

    private String password;

    private String amministratore;

    private String idEnte;

    private String attivo;

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getPassword() {
		return password;
	}

	public String getAmministratore() {
		return amministratore;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public String getAttivo() {
		return attivo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAmministratore(String amministratore) {
		this.amministratore = amministratore;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public void setAttivo(String attivo) {
		this.attivo = attivo;
	}


}
