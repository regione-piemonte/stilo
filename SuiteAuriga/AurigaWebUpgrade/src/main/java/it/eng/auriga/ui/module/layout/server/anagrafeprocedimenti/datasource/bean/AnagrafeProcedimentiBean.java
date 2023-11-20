/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AnagrafeProcedimentiBean {

	private String id;
	private String nome;
	private String descrizione;
	private Date dtInizioVld;
	private Date dtFineVld;
	private String idProcessTypeGen; // (combo su se stessa)
	private String nomeProcessTypeGen;
	private BigDecimal durataMax;
	private String uoCompetente;
	private String responsabile;
	private String iniziativa;
	private String rifNormativi;
	private Boolean flgAmministrativo;
	private Boolean flgSospendibile;
	private Integer nroMaxSospensioni;
	private Boolean flgInterrompibile;
	private Integer nroMaxGGXInterr;
	private Boolean flgPartiEsterne;
	private Boolean flgSilenzioAssenso;
	private Integer nrGGSilenzioAssenso;
	private String flgFascSF;
	private Boolean flgLocked;
	private Boolean flgRichAbilXVisualizz;
	private List<FlussiWorkflowAssociatiXmlBean> listaFlussiWfAssociati;
	
	public Date getDtInizioVld() {
		return dtInizioVld;
	}

	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	public Date getDtFineVld() {
		return dtFineVld;
	}

	public void setDtFineVld(Date dtFineVld) {
		this.dtFineVld = dtFineVld;
	}

	public String getIdProcessTypeGen() {
		return idProcessTypeGen;
	}

	public void setIdProcessTypeGen(String idProcessTypeGen) {
		this.idProcessTypeGen = idProcessTypeGen;
	}

	public String getNomeProcessTypeGen() {
		return nomeProcessTypeGen;
	}

	public void setNomeProcessTypeGen(String nomeProcessTypeGen) {
		this.nomeProcessTypeGen = nomeProcessTypeGen;
	}

	public Boolean getFlgSospendibile() {
		return flgSospendibile;
	}

	public void setFlgSospendibile(Boolean flgSospendibile) {
		this.flgSospendibile = flgSospendibile;
	}

	public Integer getNroMaxSospensioni() {
		return nroMaxSospensioni;
	}

	public void setNroMaxSospensioni(Integer nroMaxSospensioni) {
		this.nroMaxSospensioni = nroMaxSospensioni;
	}

	public Boolean getFlgInterrompibile() {
		return flgInterrompibile;
	}

	public void setFlgInterrompibile(Boolean flgInterrompibile) {
		this.flgInterrompibile = flgInterrompibile;
	}

	public Integer getNroMaxGGXInterr() {
		return nroMaxGGXInterr;
	}

	public void setNroMaxGGXInterr(Integer nroMaxGGXInterr) {
		this.nroMaxGGXInterr = nroMaxGGXInterr;
	}

	public Boolean getFlgPartiEsterne() {
		return flgPartiEsterne;
	}

	public void setFlgPartiEsterne(Boolean flgPartiEsterne) {
		this.flgPartiEsterne = flgPartiEsterne;
	}

	public Boolean getFlgSilenzioAssenso() {
		return flgSilenzioAssenso;
	}

	public void setFlgSilenzioAssenso(Boolean flgSilenzioAssenso) {
		this.flgSilenzioAssenso = flgSilenzioAssenso;
	}

	public String getFlgFascSF() {
		return flgFascSF;
	}

	public void setFlgFascSF(String flgFascSF) {
		this.flgFascSF = flgFascSF;
	}

	public Boolean getFlgLocked() {
		return flgLocked;
	}

	public void setFlgLocked(Boolean flgLocked) {
		this.flgLocked = flgLocked;
	}

	public Boolean getFlgRichAbilXVisualizz() {
		return flgRichAbilXVisualizz;
	}

	public void setFlgRichAbilXVisualizz(Boolean flgRichAbilXVisualizz) {
		this.flgRichAbilXVisualizz = flgRichAbilXVisualizz;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public BigDecimal getDurataMax() {
		return durataMax;
	}

	public String getUoCompetente() {
		return uoCompetente;
	}

	public String getResponsabile() {
		return responsabile;
	}

	public String getIniziativa() {
		return iniziativa;
	}

	public String getRifNormativi() {
		return rifNormativi;
	}

	public Integer getNrGGSilenzioAssenso() {
		return nrGGSilenzioAssenso;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setDurataMax(BigDecimal durataMax) {
		this.durataMax = durataMax;
	}

	public void setUoCompetente(String uoCompetente) {
		this.uoCompetente = uoCompetente;
	}

	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}

	public void setIniziativa(String iniziativa) {
		this.iniziativa = iniziativa;
	}

	public void setRifNormativi(String rifNormativi) {
		this.rifNormativi = rifNormativi;
	}

	public void setNrGGSilenzioAssenso(Integer nrGGSilenzioAssenso) {
		this.nrGGSilenzioAssenso = nrGGSilenzioAssenso;
	}

	public Boolean getFlgAmministrativo() {
		return flgAmministrativo;
	}

	public void setFlgAmministrativo(Boolean flgAmministrativo) {
		this.flgAmministrativo = flgAmministrativo;
	}

	public List<FlussiWorkflowAssociatiXmlBean> getListaFlussiWfAssociati() {
		return listaFlussiWfAssociati;
	}

	public void setListaFlussiWfAssociati(List<FlussiWorkflowAssociatiXmlBean> listaFlussiWfAssociati) {
		this.listaFlussiWfAssociati = listaFlussiWfAssociati;
	}

}