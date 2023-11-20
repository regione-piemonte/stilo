/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "T_TOPONIMI", uniqueConstraints = @UniqueConstraint(columnNames = "STREET_CODE"))
public class TToponimi implements java.io.Serializable {

	private static final long serialVersionUID = -8567088357088565676L;

	// private Integer id;

	private String toponimi;
	private String toponimo1;
	private String toponimo2;
	private String toponimo3;
	private String toponimo4;
	private String toponimo5;
	private String tipoVia;
	private Integer streetCode;
	private Integer stateCode;

	/*
	 * 
	 * 
	 * CREATE TABLE T_TOPONIMI ( ID BIGINT NOT NULL IDENTITY, TOPONIMO VARCHAR(500) NOT NULL, STREET_CODE INTEGER, TOPONIMO_1 VARCHAR(255), TOPONIMO_2
	 * VARCHAR(255), TOPONIMO_3 VARCHAR(255), TOPONIMO_4 VARCHAR(255), TOPONIMO_5 VARCHAR(255), PRIMARY KEY (ID) )
	 */

	public TToponimi() {
	}

	// @Id
	// // @GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "ID", unique = true, nullable = false)
	// public Integer getId() {
	// return id;
	// }

	@Column(name = "TOPONIMO", nullable = false, length = 500)
	public String getToponimi() {
		return toponimi;
	}

	public void setToponimi(String toponimi) {
		this.toponimi = toponimi;
	}

	@Column(name = "TOPONIMO_1", nullable = true, length = 255)
	public String getToponimo1() {
		return toponimo1;
	}

	public void setToponimo1(String toponimo1) {
		this.toponimo1 = toponimo1;
	}

	@Column(name = "TOPONIMO_2", nullable = true, length = 255)
	public String getToponimo2() {
		return toponimo2;
	}

	public void setToponimo2(String toponimo2) {
		this.toponimo2 = toponimo2;
	}

	@Column(name = "TOPONIMO_3", nullable = true, length = 255)
	public String getToponimo3() {
		return toponimo3;
	}

	public void setToponimo3(String toponimo3) {
		this.toponimo3 = toponimo3;
	}

	@Column(name = "TOPONIMO_4", nullable = true, length = 255)
	public String getToponimo4() {
		return toponimo4;
	}

	public void setToponimo4(String toponimo4) {
		this.toponimo4 = toponimo4;
	}

	@Column(name = "TOPONIMO_5", nullable = true, length = 255)
	public String getToponimo5() {
		return toponimo5;
	}

	public void setToponimo5(String toponimo5) {
		this.toponimo5 = toponimo5;
	}

	// public void setId(Integer id) {
	// this.id = id;
	// }

	@Id
	@Column(name = "STREET_CODE", unique = true, nullable = false, length = 25)
	public Integer getStreetCode() {
		return streetCode;
	}

	public void setStreetCode(Integer streetCode) {
		this.streetCode = streetCode;
	}

	@Column(name = "TIPO_VIA", nullable = true, length = 100)
	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	@Column(name = "STATE_CODE", nullable = true)
	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

}
