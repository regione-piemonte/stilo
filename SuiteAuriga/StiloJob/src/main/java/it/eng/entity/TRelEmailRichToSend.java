/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 13-giu-2014 14.47.52 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TRelEmailRichToSend generated by hbm2java
 */
@Entity
@Table(name = "T_REL_EMAIL_RICH_TO_SEND")
public class TRelEmailRichToSend implements java.io.Serializable {

	private static final long serialVersionUID = 3842434916264439791L;

	private String idEmail;
	
	private String idRichiesta;

	public TRelEmailRichToSend() {
	}

	public TRelEmailRichToSend(String idEmail, String idRichiesta) {
		this.idEmail = idEmail;
		this.idRichiesta = idRichiesta;
	}


	@Id
	@Column(name = "ID_EMAIL", nullable = false, length = 64)
	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
	@Column(name = "ID_RICHIESTA", nullable = false, length = 64)
	public String getIdRichiesta() {
		return this.idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

}