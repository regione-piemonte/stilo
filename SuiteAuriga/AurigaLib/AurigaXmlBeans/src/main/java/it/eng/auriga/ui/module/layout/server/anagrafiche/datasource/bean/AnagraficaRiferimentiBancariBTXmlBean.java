/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData.Tipo;
import it.eng.document.TipoData;
import java.util.Date;


public class AnagraficaRiferimentiBancariBTXmlBean {

	@NumeroColonna(numero = "1")
	private String idRiferimento;
	
	@NumeroColonna(numero = "2")
	private String descrizioneRiferimento;
	
	@NumeroColonna(numero = "3")
	private String ragioneSocialeIstitutoFinanziario;
	
	@NumeroColonna(numero = "4")
	private String codiceIBAN;
	
	@NumeroColonna(numero = "5")
	private String codiceABI;
	
	@NumeroColonna(numero = "6")
	private String codiceCAB;
	
	@NumeroColonna(numero = "7")
	private String idUtenteIns;
	
	@NumeroColonna(numero = "9")
	private String descUtenteIns;
	
	@NumeroColonna(numero = "9")
	private String idUtenteUltMod;
	
	@NumeroColonna(numero = "10")
	private String descUtenteUltMod;
	
	@NumeroColonna(numero = "11")
	@TipoData(tipo=Tipo.DATA)
	private Date dataIns;
	
	@NumeroColonna(numero = "12")
	@TipoData(tipo=Tipo.DATA)
	private Date dataUltMod;
	
	@NumeroColonna(numero = "13")
	private Integer flgValido;
	
	@NumeroColonna(numero = "14")
	private Integer flgDiSistema;
	
	@NumeroColonna(numero = "15")
	private Integer flgAssociato;
	
	public String getIdRiferimento() {
		return idRiferimento;
	}

	public void setIdRiferimento(String idRiferimento) {
		this.idRiferimento = idRiferimento;
	}

	public String getDescrizioneRiferimento() {
		return descrizioneRiferimento;
	}

	public void setDescrizioneRiferimento(String descrizioneRiferimento) {
		this.descrizioneRiferimento = descrizioneRiferimento;
	}

	public String getRagioneSocialeIstitutoFinanziario() {
		return ragioneSocialeIstitutoFinanziario;
	}

	public void setRagioneSocialeIstitutoFinanziario(
			String ragioneSocialeIstitutoFinanziario) {
		this.ragioneSocialeIstitutoFinanziario = ragioneSocialeIstitutoFinanziario;
	}

	public String getCodiceIBAN() {
		return codiceIBAN;
	}

	public void setCodiceIBAN(String codiceIBAN) {
		this.codiceIBAN = codiceIBAN;
	}

	public String getCodiceABI() {
		return codiceABI;
	}

	public void setCodiceABI(String codiceABI) {
		this.codiceABI = codiceABI;
	}

	public String getCodiceCAB() {
		return codiceCAB;
	}

	public void setCodiceCAB(String codiceCAB) {
		this.codiceCAB = codiceCAB;
	}

	public String getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(String idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public String getDescUtenteIns() {
		return descUtenteIns;
	}

	public void setDescUtenteIns(String descUtenteIns) {
		this.descUtenteIns = descUtenteIns;
	}

	public String getIdUtenteUltMod() {
		return idUtenteUltMod;
	}

	public void setIdUtenteUltMod(String idUtenteUltMod) {
		this.idUtenteUltMod = idUtenteUltMod;
	}

	public String getDescUtenteUltMod() {
		return descUtenteUltMod;
	}

	public void setDescUtenteUltMod(String descUtenteUltMod) {
		this.descUtenteUltMod = descUtenteUltMod;
	}

	public Date getDataIns() {
		return dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public Date getDataUltMod() {
		return dataUltMod;
	}

	public void setDataUltMod(Date dataUltMod) {
		this.dataUltMod = dataUltMod;
	}

	public Integer getFlgValido() {
		return flgValido;
	}

	public void setFlgValido(Integer flgValido) {
		this.flgValido = flgValido;
	}

	public Integer getFlgDiSistema() {
		return flgDiSistema;
	}

	public void setFlgDiSistema(Integer flgDiSistema) {
		this.flgDiSistema = flgDiSistema;
	}

	public Integer getFlgAssociato() {
		return flgAssociato;
	}

	public void setFlgAssociato(Integer flgAssociato) {
		this.flgAssociato = flgAssociato;
	}
	
	
}
