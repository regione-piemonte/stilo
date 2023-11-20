/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboReggioUO  implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String descrizione;

    private String idEnte;

	public String getId() {
		return id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getIdEnte() {
		return idEnte;
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

  
}
