/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 2-feb-2017 16.02.31 by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TSezioniDiz generated by hbm2java
 */
@Entity
@Table(name = "T_SEZIONI_DIZ", uniqueConstraints = @UniqueConstraint(columnNames = "NOME"))
public class TSezioniDiz implements java.io.Serializable {

	private static final long serialVersionUID = 5646202678011520451L;
	private String idSezDiz;
	private TSezioniDiz TSezioniDiz;
	private String nome;
	private String contenuti;
	private boolean flgDiSistema;
	private boolean flgAnn;
	private Date tsIns;
	private String idUteIns;
	private Date tsUltimoAgg;
	private String idUteUltimoAgg;
	private Set<TSezioniDiz> TSezioniDizs = new HashSet<TSezioniDiz>(0);
	private Set<TValDizionario> TValDizionarios = new HashSet<TValDizionario>(0);

	public TSezioniDiz() {
	}

	public TSezioniDiz(String idSezDiz, String nome, String contenuti, boolean flgDiSistema, boolean flgAnn, Date tsIns, Date tsUltimoAgg) {
		this.idSezDiz = idSezDiz;
		this.nome = nome;
		this.contenuti = contenuti;
		this.flgDiSistema = flgDiSistema;
		this.flgAnn = flgAnn;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public TSezioniDiz(String idSezDiz, TSezioniDiz TSezioniDiz, String nome, String contenuti, boolean flgDiSistema, boolean flgAnn, Date tsIns,
			String idUteIns, Date tsUltimoAgg, String idUteUltimoAgg, Set<TSezioniDiz> TSezioniDizs, Set<TValDizionario> TValDizionarios) {
		this.idSezDiz = idSezDiz;
		this.TSezioniDiz = TSezioniDiz;
		this.nome = nome;
		this.contenuti = contenuti;
		this.flgDiSistema = flgDiSistema;
		this.flgAnn = flgAnn;
		this.tsIns = tsIns;
		this.idUteIns = idUteIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.idUteUltimoAgg = idUteUltimoAgg;
		this.TSezioniDizs = TSezioniDizs;
		this.TValDizionarios = TValDizionarios;
	}

	@Id
	@Column(name = "ID_SEZ_DIZ", unique = true, nullable = false, length = 64)
	public String getIdSezDiz() {
		return this.idSezDiz;
	}

	public void setIdSezDiz(String idSezDiz) {
		this.idSezDiz = idSezDiz;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIALIZZA_SEZ")
	public TSezioniDiz getTSezioniDiz() {
		return this.TSezioniDiz;
	}

	public void setTSezioniDiz(TSezioniDiz TSezioniDiz) {
		this.TSezioniDiz = TSezioniDiz;
	}

	@Column(name = "NOME", unique = true, nullable = false, length = 100)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "CONTENUTI", nullable = false, length = 1000)
	public String getContenuti() {
		return this.contenuti;
	}

	public void setContenuti(String contenuti) {
		this.contenuti = contenuti;
	}

	@Column(name = "FLG_DI_SISTEMA", nullable = false, precision = 1, scale = 0)
	public boolean isFlgDiSistema() {
		return this.flgDiSistema;
	}

	public void setFlgDiSistema(boolean flgDiSistema) {
		this.flgDiSistema = flgDiSistema;
	}

	@Column(name = "FLG_ANN", nullable = false, precision = 1, scale = 0)
	public boolean isFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(boolean flgAnn) {
		this.flgAnn = flgAnn;
	}

	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "ID_UTE_INS", length = 64)
	public String getIdUteIns() {
		return this.idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	@Column(name = "TS_ULTIMO_AGG", nullable = false)
	public Date getTsUltimoAgg() {
		return this.tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	@Column(name = "ID_UTE_ULTIMO_AGG", length = 64)
	public String getIdUteUltimoAgg() {
		return this.idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TSezioniDiz")
	public Set<TSezioniDiz> getTSezioniDizs() {
		return this.TSezioniDizs;
	}

	public void setTSezioniDizs(Set<TSezioniDiz> TSezioniDizs) {
		this.TSezioniDizs = TSezioniDizs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TSezioniDiz")
	public Set<TValDizionario> getTValDizionarios() {
		return this.TValDizionarios;
	}

	public void setTValDizionarios(Set<TValDizionario> TValDizionarios) {
		this.TValDizionarios = TValDizionarios;
	}

}