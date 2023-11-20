/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

public class InfoFirmaMarca implements Serializable {

	private boolean bustaCrittograficaFattaDaAuriga;
	private boolean bustaCrittograficaInComplPassoIter;
	private String tipoBustaCrittografica;
	private String infoBustaCrittografica;
	private boolean firmeNonValideBustaCrittografica;
	private boolean firmeExtraAuriga;
	private Date dataOraVerificaFirma;
	private boolean marcaTemporaleAppostaDaAuriga;
	private Date dataOraMarcaTemporale;
	private String tipoMarcaTemporale;
	private String infoMarcaTemporale;
	private boolean marcaTemporaleNonValida;
	private Date dataOraVerificaMarcaTemporale;

	public boolean isBustaCrittograficaFattaDaAuriga() {
		return bustaCrittograficaFattaDaAuriga;
	}

	public void setBustaCrittograficaFattaDaAuriga(boolean bustaCrittograficaFattaDaAuriga) {
		this.bustaCrittograficaFattaDaAuriga = bustaCrittograficaFattaDaAuriga;
	}
	
	public boolean isBustaCrittograficaInComplPassoIter() {
		return bustaCrittograficaInComplPassoIter;
	}
	
	public void setBustaCrittograficaInComplPassoIter(boolean bustaCrittograficaInComplPassoIter) {
		this.bustaCrittograficaInComplPassoIter = bustaCrittograficaInComplPassoIter;
	}

	public String getTipoBustaCrittografica() {
		return tipoBustaCrittografica;
	}

	public void setTipoBustaCrittografica(String tipoBustaCrittografica) {
		this.tipoBustaCrittografica = tipoBustaCrittografica;
	}

	public String getInfoBustaCrittografica() {
		return infoBustaCrittografica;
	}

	public void setInfoBustaCrittografica(String infoBustaCrittografica) {
		this.infoBustaCrittografica = infoBustaCrittografica;
	}

	public boolean isFirmeNonValideBustaCrittografica() {
		return firmeNonValideBustaCrittografica;
	}

	public void setFirmeNonValideBustaCrittografica(boolean firmeNonValideBustaCrittografica) {
		this.firmeNonValideBustaCrittografica = firmeNonValideBustaCrittografica;
	}

	public boolean isFirmeExtraAuriga() {
		return firmeExtraAuriga;
	}

	public void setFirmeExtraAuriga(boolean firmeExtraAuriga) {
		this.firmeExtraAuriga = firmeExtraAuriga;
	}

	public Date getDataOraVerificaFirma() {
		return dataOraVerificaFirma;
	}

	public void setDataOraVerificaFirma(Date dataOraVerificaFirma) {
		this.dataOraVerificaFirma = dataOraVerificaFirma;
	}

	public boolean isMarcaTemporaleAppostaDaAuriga() {
		return marcaTemporaleAppostaDaAuriga;
	}

	public void setMarcaTemporaleAppostaDaAuriga(boolean marcaTemporaleAppostaDaAuriga) {
		this.marcaTemporaleAppostaDaAuriga = marcaTemporaleAppostaDaAuriga;
	}

	public Date getDataOraMarcaTemporale() {
		return dataOraMarcaTemporale;
	}

	public void setDataOraMarcaTemporale(Date dataOraMarcaTemporale) {
		this.dataOraMarcaTemporale = dataOraMarcaTemporale;
	}

	public String getTipoMarcaTemporale() {
		return tipoMarcaTemporale;
	}

	public void setTipoMarcaTemporale(String tipoMarcaTemporale) {
		this.tipoMarcaTemporale = tipoMarcaTemporale;
	}

	public String getInfoMarcaTemporale() {
		return infoMarcaTemporale;
	}

	public void setInfoMarcaTemporale(String infoMarcaTemporale) {
		this.infoMarcaTemporale = infoMarcaTemporale;
	}

	public boolean isMarcaTemporaleNonValida() {
		return marcaTemporaleNonValida;
	}

	public void setMarcaTemporaleNonValida(boolean marcaTemporaleNonValida) {
		this.marcaTemporaleNonValida = marcaTemporaleNonValida;
	}

	public Date getDataOraVerificaMarcaTemporale() {
		return dataOraVerificaMarcaTemporale;
	}

	public void setDataOraVerificaMarcaTemporale(Date dataOraVerificaMarcaTemporale) {
		this.dataOraVerificaMarcaTemporale = dataOraVerificaMarcaTemporale;
	}

}
