/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "T_CIVICI_TOPONIMI", uniqueConstraints = @UniqueConstraint(columnNames = "IDC"))
public class TCiviciToponimi implements java.io.Serializable {

	private static final long serialVersionUID = -8567088357088565676L;

	// private Integer id;

	private Integer idc;
	private Integer stateCode;
	private Integer streetCode;
	// private String toponimo;

	private String civicoNumber;
	private String civicoString;
	private String civicoCompleto;

	private String bar;
	private String bar2;
	private Integer cap;
	private String zdn;

	/*
	 * 
	 * 
	 * CREATE TABLE T_CIVICI_TOPONIMI ( ID BIGINT NOT NULL IDENTITY, TOPONIMO VARCHAR(500) NOT NULL, STREET_CODE INTEGER, STATE_CODE VARCHAR(2), IDC INTEGER,
	 * CIVICO_NUMBER VARCHAR(10), CIVICO_STRING VARCHAR(10), CIVICO_COMPLETO VARCHAR(20), BAR VARCHAR(10), BAR2 VARCHAR(10), ZDN VARCHAR(25), CAP INTEGER,
	 * PRIMARY KEY (ID) )
	 */

	public TCiviciToponimi() {
	}

	// @Id
	// @Column(name = "ID", unique = true, nullable = false)
	// public Integer getId() {
	// return id;
	// }

	// @Column(name = "TOPONIMO", nullable = false, length = 500)
	// public String getToponimo() {
	// return toponimo;
	// }
	//
	// public void setToponimo(String toponimo) {
	// this.toponimo = toponimo;
	// }

	// public void setId(Integer id) {
	// this.id = id;
	// }

	@Column(name = "STREET_CODE", unique = true, nullable = false, length = 25)
	public Integer getStreetCode() {
		return streetCode;
	}

	public void setStreetCode(Integer streetCode) {
		this.streetCode = streetCode;
	}

	@Id
	@Column(name = "IDC", nullable = true, length = 10)
	public Integer getIdc() {
		return idc;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	@Column(name = "STATE_CODE", nullable = true, length = 2)
	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * Numero del civico
	 * 
	 * @return
	 */
	@Column(name = "CIVICO_NUMBER", nullable = true, length = 10)
	public String getCivicoNumber() {
		return civicoNumber;
	}

	public void setCivicoNumber(String civicoNumber) {
		this.civicoNumber = civicoNumber;
	}

	/**
	 * Parte aggiuntiva letterale del numero civico
	 * 
	 * @return
	 */
	@Column(name = "CIVICO_STRING", nullable = true, length = 10)
	public String getCivicoString() {
		return civicoString;
	}

	@Column(name = "CIVICO_COMPLETO", nullable = true, length = 10)
	public String getCivicoCompleto() {
		return civicoCompleto;
	}

	public void setCivicoCompleto(String civicoCompleto) {
		this.civicoCompleto = civicoCompleto;
	}

	public void setCivicoString(String civicoString) {
		this.civicoString = civicoString;
	}

	/**
	 * Parte dopo il barrato del numero civico senza barra
	 * 
	 * @return
	 */
	@Column(name = "BAR", nullable = true, length = 10)
	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	/**
	 * Seconda parte dopo il barrato del numero civico senza barra
	 */
	@Column(name = "BAR2", nullable = true, length = 10)
	public String getBar2() {
		return bar2;
	}

	public void setBar2(String bar2) {
		this.bar2 = bar2;
	}

	@Column(name = "CAP", nullable = true)
	public Integer getCap() {
		return cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}

	/**
	 * Nuova zona di decentramento
	 * 
	 * @return
	 */
	@Column(name = "ZDN", nullable = true, length = 10)
	public String getZdn() {
		return zdn;
	}

	public void setZdn(String zdn) {
		this.zdn = zdn;
	}

}
