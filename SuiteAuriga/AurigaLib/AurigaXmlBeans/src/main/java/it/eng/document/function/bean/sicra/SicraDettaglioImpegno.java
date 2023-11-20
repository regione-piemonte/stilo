/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraDettaglioImpegno implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idFornitore;
	private Long idCapitolo;
	private Integer annoCompetenza;
	private Calendar dataValuta;
	private String descrizione;
	private BigInteger codProgetto;
	private BigInteger codCentroCosto;
	private String siglaCPT;
	private String cup;
	private String cig;
	private BigDecimal importo;
	private String cassa;
	private Boolean copertoDaFPV;
	private String siglaPianoFin;
	private Long codLavoro;
	private Long codTipoSpesa;
	private Long codTipoFinanz;
	private Calendar dataValiditaDal;
	private Calendar dataValiditaAl;
	private Boolean creditoDebitoPredefinito;
	private String codificaCapitolo;

	public Long getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}

	public Long getIdCapitolo() {
		return idCapitolo;
	}

	public void setIdCapitolo(Long idCapitolo) {
		this.idCapitolo = idCapitolo;
	}

	public Integer getAnnoCompetenza() {
		return annoCompetenza;
	}

	public void setAnnoCompetenza(Integer annoCompetenza) {
		this.annoCompetenza = annoCompetenza;
	}

	public Calendar getDataValuta() {
		return dataValuta;
	}

	public void setDataValuta(Calendar dataValuta) {
		this.dataValuta = dataValuta;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigInteger getCodProgetto() {
		return codProgetto;
	}

	public void setCodProgetto(BigInteger codProgetto) {
		this.codProgetto = codProgetto;
	}

	public BigInteger getCodCentroCosto() {
		return codCentroCosto;
	}

	public void setCodCentroCosto(BigInteger codCentroCosto) {
		this.codCentroCosto = codCentroCosto;
	}

	public String getSiglaCPT() {
		return siglaCPT;
	}

	public void setSiglaCPT(String siglaCPT) {
		this.siglaCPT = siglaCPT;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getCassa() {
		return cassa;
	}

	public void setCassa(String cassa) {
		this.cassa = cassa;
	}

	public Boolean getCopertoDaFPV() {
		return copertoDaFPV;
	}

	public void setCopertoDaFPV(Boolean copertoDaFPV) {
		this.copertoDaFPV = copertoDaFPV;
	}

	public String getSiglaPianoFin() {
		return siglaPianoFin;
	}

	public void setSiglaPianoFin(String siglaPianoFin) {
		this.siglaPianoFin = siglaPianoFin;
	}

	public Long getCodLavoro() {
		return codLavoro;
	}

	public void setCodLavoro(Long codLavoro) {
		this.codLavoro = codLavoro;
	}

	public Long getCodTipoSpesa() {
		return codTipoSpesa;
	}

	public void setCodTipoSpesa(Long codTipoSpesa) {
		this.codTipoSpesa = codTipoSpesa;
	}

	public Long getCodTipoFinanz() {
		return codTipoFinanz;
	}

	public void setCodTipoFinanz(Long codTipoFinanz) {
		this.codTipoFinanz = codTipoFinanz;
	}

	public Calendar getDataValiditaDal() {
		return dataValiditaDal;
	}

	public void setDataValiditaDal(Calendar dataValiditaDal) {
		this.dataValiditaDal = dataValiditaDal;
	}

	public Calendar getDataValiditaAl() {
		return dataValiditaAl;
	}

	public void setDataValiditaAl(Calendar dataValiditaAl) {
		this.dataValiditaAl = dataValiditaAl;
	}

	public Boolean getCreditoDebitoPredefinito() {
		return creditoDebitoPredefinito;
	}

	public void setCreditoDebitoPredefinito(Boolean creditoDebitoPredefinito) {
		this.creditoDebitoPredefinito = creditoDebitoPredefinito;
	}

	public String getCodificaCapitolo() {
		return codificaCapitolo;
	}

	public void setCodificaCapitolo(String codificaCapitolo) {
		this.codificaCapitolo = codificaCapitolo;
	}

}// SicraDettaglioImpegno
