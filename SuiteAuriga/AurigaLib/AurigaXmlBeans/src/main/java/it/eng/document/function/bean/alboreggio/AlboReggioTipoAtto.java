/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboReggioTipoAtto  implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String descrizione;

    private String idEnte;

    private int durata;

    private String cartella;

    private String attivo;

    private String sospScad;

	public String getId() {
		return id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public int getDurata() {
		return durata;
	}

	public String getCartella() {
		return cartella;
	}

	public String getAttivo() {
		return attivo;
	}

	public String getSospScad() {
		return sospScad;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public void setCartella(String cartella) {
		this.cartella = cartella;
	}

	public void setAttivo(String attivo) {
		this.attivo = attivo;
	}

	public void setSospScad(String sospScad) {
		this.sospScad = sospScad;
	}
	
}
