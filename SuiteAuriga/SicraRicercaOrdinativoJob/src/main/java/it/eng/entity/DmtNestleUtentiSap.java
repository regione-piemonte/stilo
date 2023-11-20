/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The persistent class for the DMT_NESTLE_UTENTI_SAP database table.
 * 
 */
@Entity
@Table(name="DMT_NESTLE_UTENTI_SAP")
public class DmtNestleUtentiSap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_UTENTE")
	private String idUtente;
	
	@Column(name="USERNAME")
	private String username; 
	
	@Column(name="COD_APPL_OWNER")
	private String codapplOwner;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;
	
	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCodapplOwner() {
		return codapplOwner;
	}

	public void setCodapplOwner(String codapplOwner) {
		this.codapplOwner = codapplOwner;
	}

	public BigDecimal getFlgAnn() {
		return flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}
	
}