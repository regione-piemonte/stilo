/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PttXmlDestinatari generated by hbm2java
 */
@Entity
@Table(name = "PTT_XML_DESTINATARI")
public class PttXmlDestinatari implements java.io.Serializable {

	private PttXmlDestinatariId id;
	private String indTel;
	private String tipo;

	public PttXmlDestinatari() {
	}

	public PttXmlDestinatari(PttXmlDestinatariId id) {
		this.id = id;
	}

	public PttXmlDestinatari(PttXmlDestinatariId id, String indTel, String tipo) {
		this.id = id;
		this.indTel = indTel;
		this.tipo = tipo;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idattach", column = @Column(name = "IDATTACH", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "idemail", column = @Column(name = "IDEMAIL", nullable = false, precision = 22, scale = 0)) })
	public PttXmlDestinatariId getId() {
		return this.id;
	}

	public void setId(PttXmlDestinatariId id) {
		this.id = id;
	}

	@Column(name = "IND_TEL", length = 100)
	public String getIndTel() {
		return this.indTel;
	}

	public void setIndTel(String indTel) {
		this.indTel = indTel;
	}

	@Column(name = "TIPO", length = 25)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
