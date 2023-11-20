/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "T_TIPO_VIA", uniqueConstraints = @UniqueConstraint(columnNames = "CODICE"))
public class TTipoVia implements java.io.Serializable {

	private static final long serialVersionUID = -8567088357088565676L;

	// private Integer id;

	private String codice;
	private String descrizioneShort;
	private String descriptionExtended;

	/*
	 * 
	 * 
	 * CREATE TABLE T_TIPO_VIA (CODICE VARCHAR(10) NOT NULL, DESCRIZIONE_CORTA VARCHAR(50), DESCRIZIONE VARCHAR(100), PRIMARY KEY (CODICE) )
	 */

	public TTipoVia() {
	}

	// @Id
	// // @GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "ID", unique = true, nullable = false)
	// public Integer getId() {
	// return id;
	// }
	//
	// public void setId(Integer id) {
	// this.id = id;
	// }

	@Id
	@Column(name = "CODICE", unique = true, nullable = false, length = 10)
	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Column(name = "DESCRIZIONE_CORTA", nullable = true, length = 50)
	public String getDescrizioneShort() {
		return descrizioneShort;
	}

	public void setDescrizioneShort(String descrizioneShort) {
		this.descrizioneShort = descrizioneShort;
	}

	@Column(name = "DESCRIZIONE_ESTESA", nullable = true, length = 100)
	public String getDescriptionExtended() {
		return descriptionExtended;
	}

	public void setDescriptionExtended(String descriptionExtended) {
		this.descriptionExtended = descriptionExtended;
	}

}
