/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 5-ott-2015 10.18.12 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TNotAssenzaRegistriXCs generated by hbm2java
 */
@Entity
@Table(name = "T_NOT_ASSENZA_REGISTRI_X_CS")
public class TNotAssenzaRegistriXCs implements java.io.Serializable {

	private static final long serialVersionUID = -6385632067088364869L;

	private TNotAssenzaRegistriXCsId id;

	private String idEmailNotif;

	private String nomeFile;

	@Column(name = "NOME_FILE", nullable = false, length = 4000)
	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public TNotAssenzaRegistriXCs() {
	}

	public TNotAssenzaRegistriXCs(TNotAssenzaRegistriXCsId id, String idEmailNotif) {
		this.id = id;
		this.idEmailNotif = idEmailNotif;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "idServizio", column = @Column(name = "ID_SERVIZIO", nullable = false, length = 64)),
			@AttributeOverride(name = "periodoReg", column = @Column(name = "PERIODO_REG", nullable = false, length = 500)),
			@AttributeOverride(name = "notificaNro", column = @Column(name = "NOTIFICA_NRO", nullable = false, precision = 1, scale = 0)) })
	public TNotAssenzaRegistriXCsId getId() {
		return this.id;
	}

	public void setId(TNotAssenzaRegistriXCsId id) {
		this.id = id;
	}

	@Column(name = "ID_EMAIL_NOTIF", nullable = false, length = 64)
	public String getIdEmailNotif() {
		return this.idEmailNotif;
	}

	public void setIdEmailNotif(String idEmailNotif) {
		this.idEmailNotif = idEmailNotif;
	}

}