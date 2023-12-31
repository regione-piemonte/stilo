/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PttSwMenu generated by hbm2java
 */
@Entity
@Table(name = "PTT_SW_MENU")
public class PttSwMenu implements java.io.Serializable {

	private String desMenu;
	private String desContesto;
	private String codMenu;
	private String tooltip;
	private String url;
	private BigDecimal nroOrdine;
	private Serializable listaFunzioni;

	public PttSwMenu() {
	}

	public PttSwMenu(String desMenu, String desContesto) {
		this.desMenu = desMenu;
		this.desContesto = desContesto;
	}

	public PttSwMenu(String desMenu, String desContesto, String codMenu,
			String tooltip, String url, BigDecimal nroOrdine,
			Serializable listaFunzioni) {
		this.desMenu = desMenu;
		this.desContesto = desContesto;
		this.codMenu = codMenu;
		this.tooltip = tooltip;
		this.url = url;
		this.nroOrdine = nroOrdine;
		this.listaFunzioni = listaFunzioni;
	}

	@Id
	@Column(name = "DES_MENU", unique = true, nullable = false, length = 50)
	public String getDesMenu() {
		return this.desMenu;
	}

	public void setDesMenu(String desMenu) {
		this.desMenu = desMenu;
	}

	@Column(name = "DES_CONTESTO", nullable = false, length = 50)
	public String getDesContesto() {
		return this.desContesto;
	}

	public void setDesContesto(String desContesto) {
		this.desContesto = desContesto;
	}

	@Column(name = "COD_MENU", length = 10)
	public String getCodMenu() {
		return this.codMenu;
	}

	public void setCodMenu(String codMenu) {
		this.codMenu = codMenu;
	}

	@Column(name = "TOOLTIP", length = 150)
	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	@Column(name = "URL", length = 1000)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "NRO_ORDINE", precision = 22, scale = 0)
	public BigDecimal getNroOrdine() {
		return this.nroOrdine;
	}

	public void setNroOrdine(BigDecimal nroOrdine) {
		this.nroOrdine = nroOrdine;
	}

	@Column(name = "LISTA_FUNZIONI")
	public Serializable getListaFunzioni() {
		return this.listaFunzioni;
	}

	public void setListaFunzioni(Serializable listaFunzioni) {
		this.listaFunzioni = listaFunzioni;
	}

}
