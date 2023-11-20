/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraBudget implements Serializable {

	private static final long serialVersionUID = 1L;

	private String parte;
	private Long idCapitolo;
	private String codifica;
	private String descrizione;
	private Integer numCapitolo;
	private Integer annoCompetenza;
	private String sigPianoFinCapitolo;
	private String desPianoFinCapitolo;
	private String sigCPTcapitolo;
	private String desCPTcapitolo;
	private String codRespServCapitolo;
	private String desRespServCapitolo;
	private String codRespProcCapitolo;
	private String desRespProcCapitolo;
	private Integer codCentroCosto;
	private String desCentroCosto;
	private String sigCPT;
	private String desCPT;
	private String sigPianoFin;
	private String desPianoFin;
	private Integer idLavoro;
	private Integer codLavoro;
	private String desLavoro;
	private Integer idTipoSpesa;
	private Integer codTipoSpesa;
	private String desTipoSpesa;
	private Integer idTipoFinanz;
	private Integer codTipoFinanz;
	private String desTipoFinanz;
	private Integer idProgetto;
	private Integer codProgetto;
	private String desProgetto;
	private BigDecimal stanziamento;
	private BigDecimal disponibile;
	private BigDecimal impegnato;
	private BigDecimal mandatiEmessi;
	private BigDecimal documentiAperti;
	private BigDecimal prenotatoDisponibile;
	private BigInteger copFPV;
	private String descrizioneBudget;

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	public Long getIdCapitolo() {
		return idCapitolo;
	}

	public void setIdCapitolo(Long idCapitolo) {
		this.idCapitolo = idCapitolo;
	}

	public String getCodifica() {
		return codifica;
	}

	public void setCodifica(String codifica) {
		this.codifica = codifica;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getNumCapitolo() {
		return numCapitolo;
	}

	public void setNumCapitolo(Integer numCapitolo) {
		this.numCapitolo = numCapitolo;
	}

	public Integer getAnnoCompetenza() {
		return annoCompetenza;
	}

	public void setAnnoCompetenza(Integer annoCompetenza) {
		this.annoCompetenza = annoCompetenza;
	}

	public String getSigPianoFinCapitolo() {
		return sigPianoFinCapitolo;
	}

	public void setSigPianoFinCapitolo(String sigPianoFinCapitolo) {
		this.sigPianoFinCapitolo = sigPianoFinCapitolo;
	}

	public String getDesPianoFinCapitolo() {
		return desPianoFinCapitolo;
	}

	public void setDesPianoFinCapitolo(String desPianoFinCapitolo) {
		this.desPianoFinCapitolo = desPianoFinCapitolo;
	}

	public String getSigCPTcapitolo() {
		return sigCPTcapitolo;
	}

	public void setSigCPTcapitolo(String sigCPTcapitolo) {
		this.sigCPTcapitolo = sigCPTcapitolo;
	}

	public String getDesCPTcapitolo() {
		return desCPTcapitolo;
	}

	public void setDesCPTcapitolo(String desCPTcapitolo) {
		this.desCPTcapitolo = desCPTcapitolo;
	}

	public String getCodRespServCapitolo() {
		return codRespServCapitolo;
	}

	public void setCodRespServCapitolo(String codRespServCapitolo) {
		this.codRespServCapitolo = codRespServCapitolo;
	}

	public String getDesRespServCapitolo() {
		return desRespServCapitolo;
	}

	public void setDesRespServCapitolo(String desRespServCapitolo) {
		this.desRespServCapitolo = desRespServCapitolo;
	}

	public String getCodRespProcCapitolo() {
		return codRespProcCapitolo;
	}

	public void setCodRespProcCapitolo(String codRespProcCapitolo) {
		this.codRespProcCapitolo = codRespProcCapitolo;
	}

	public String getDesRespProcCapitolo() {
		return desRespProcCapitolo;
	}

	public void setDesRespProcCapitolo(String desRespProcCapitolo) {
		this.desRespProcCapitolo = desRespProcCapitolo;
	}

	public Integer getCodCentroCosto() {
		return codCentroCosto;
	}

	public void setCodCentroCosto(Integer codCentroCosto) {
		this.codCentroCosto = codCentroCosto;
	}

	public String getDesCentroCosto() {
		return desCentroCosto;
	}

	public void setDesCentroCosto(String desCentroCosto) {
		this.desCentroCosto = desCentroCosto;
	}

	public String getSigCPT() {
		return sigCPT;
	}

	public void setSigCPT(String sigCPT) {
		this.sigCPT = sigCPT;
	}

	public String getDesCPT() {
		return desCPT;
	}

	public void setDesCPT(String desCPT) {
		this.desCPT = desCPT;
	}

	public String getSigPianoFin() {
		return sigPianoFin;
	}

	public void setSigPianoFin(String sigPianoFin) {
		this.sigPianoFin = sigPianoFin;
	}

	public String getDesPianoFin() {
		return desPianoFin;
	}

	public void setDesPianoFin(String desPianoFin) {
		this.desPianoFin = desPianoFin;
	}

	public Integer getIdLavoro() {
		return idLavoro;
	}

	public void setIdLavoro(Integer idLavoro) {
		this.idLavoro = idLavoro;
	}

	public Integer getCodLavoro() {
		return codLavoro;
	}

	public void setCodLavoro(Integer codLavoro) {
		this.codLavoro = codLavoro;
	}

	public String getDesLavoro() {
		return desLavoro;
	}

	public void setDesLavoro(String desLavoro) {
		this.desLavoro = desLavoro;
	}

	public Integer getIdTipoSpesa() {
		return idTipoSpesa;
	}

	public void setIdTipoSpesa(Integer idTipoSpesa) {
		this.idTipoSpesa = idTipoSpesa;
	}

	public Integer getCodTipoSpesa() {
		return codTipoSpesa;
	}

	public void setCodTipoSpesa(Integer codTipoSpesa) {
		this.codTipoSpesa = codTipoSpesa;
	}

	public String getDesTipoSpesa() {
		return desTipoSpesa;
	}

	public void setDesTipoSpesa(String desTipoSpesa) {
		this.desTipoSpesa = desTipoSpesa;
	}

	public Integer getIdTipoFinanz() {
		return idTipoFinanz;
	}

	public void setIdTipoFinanz(Integer idTipoFinanz) {
		this.idTipoFinanz = idTipoFinanz;
	}

	public Integer getCodTipoFinanz() {
		return codTipoFinanz;
	}

	public void setCodTipoFinanz(Integer codTipoFinanz) {
		this.codTipoFinanz = codTipoFinanz;
	}

	public String getDesTipoFinanz() {
		return desTipoFinanz;
	}

	public void setDesTipoFinanz(String desTipoFinanz) {
		this.desTipoFinanz = desTipoFinanz;
	}

	public Integer getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Integer idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Integer getCodProgetto() {
		return codProgetto;
	}

	public void setCodProgetto(Integer codProgetto) {
		this.codProgetto = codProgetto;
	}

	public String getDesProgetto() {
		return desProgetto;
	}

	public void setDesProgetto(String desProgetto) {
		this.desProgetto = desProgetto;
	}

	public BigDecimal getStanziamento() {
		return stanziamento;
	}

	public void setStanziamento(BigDecimal stanziamento) {
		this.stanziamento = stanziamento;
	}

	public BigDecimal getDisponibile() {
		return disponibile;
	}

	public void setDisponibile(BigDecimal disponibile) {
		this.disponibile = disponibile;
	}

	public BigDecimal getImpegnato() {
		return impegnato;
	}

	public void setImpegnato(BigDecimal impegnato) {
		this.impegnato = impegnato;
	}

	public BigDecimal getMandatiEmessi() {
		return mandatiEmessi;
	}

	public void setMandatiEmessi(BigDecimal mandatiEmessi) {
		this.mandatiEmessi = mandatiEmessi;
	}

	public BigDecimal getDocumentiAperti() {
		return documentiAperti;
	}

	public void setDocumentiAperti(BigDecimal documentiAperti) {
		this.documentiAperti = documentiAperti;
	}

	public BigDecimal getPrenotatoDisponibile() {
		return prenotatoDisponibile;
	}

	public void setPrenotatoDisponibile(BigDecimal prenotatoDisponibile) {
		this.prenotatoDisponibile = prenotatoDisponibile;
	}

	public BigInteger getCopFPV() {
		return copFPV;
	}

	public void setCopFPV(BigInteger copFPV) {
		this.copFPV = copFPV;
	}

	public String getDescrizioneBudget() {
		return descrizioneBudget;
	}

	public void setDescrizioneBudget(String descrizioneBudget) {
		this.descrizioneBudget = descrizioneBudget;
	}

}
