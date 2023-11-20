/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraLiquidazione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codice;
	private Long codiceAnnuale;
	private BigInteger anno;
	private Calendar data;
	private String oggetto;
	private BigInteger tipoLiquidazione;
	
	public Long getCodice() {
		return codice;
	}
	
	public void setCodice(Long codice) {
		this.codice = codice;
	}
	
	public Long getCodiceAnnuale() {
		return codiceAnnuale;
	}
	
	public void setCodiceAnnuale(Long codiceAnnuale) {
		this.codiceAnnuale = codiceAnnuale;
	}
	
	public BigInteger getAnno() {
		return anno;
	}
	
	public void setAnno(BigInteger anno) {
		this.anno = anno;
	}
	
	public Calendar getData() {
		return data;
	}
	
	public void setData(Calendar data) {
		this.data = data;
	}
	
	public String getOggetto() {
		return oggetto;
	}
	
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public BigInteger getTipoLiquidazione() {
		return tipoLiquidazione;
	}
	
	public void setTipoLiquidazione(BigInteger tipoLiquidazione) {
		this.tipoLiquidazione = tipoLiquidazione;
	}
	
}
