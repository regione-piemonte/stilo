/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.util.Date;

public class AnagraficaRubricaEmailXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idVoceRubrica;
	@NumeroColonna(numero = "2")
	private String nome; 
	@NumeroColonna(numero = "3")
	private String tipoIndirizzo;
	@NumeroColonna(numero = "4")
	private String indirizzoEmail;
	@NumeroColonna(numero = "5")
	private String tipoAccount;
	@NumeroColonna(numero = "6")
	private boolean flgPECverificata;
	@NumeroColonna(numero = "7")
	private boolean flgPresenteInIPA;
	@NumeroColonna(numero = "8")
	private String codiceIPA;
	@NumeroColonna(numero = "9")
	private String tipoSoggettoRif;
	@NumeroColonna(numero = "10")
	private boolean flgValido;
	@NumeroColonna(numero = "11")
	@TipoData(tipo=Tipo.DATA)
	private Date dataIns;
	@NumeroColonna(numero = "12")
	private String utenteIns;
	@NumeroColonna(numero = "13")
	@TipoData(tipo=Tipo.DATA)
	private Date dataUltMod;
	@NumeroColonna(numero = "14")
	private String utenteUltMod;
	@NumeroColonna(numero = "15")
	private String sigillo;
	
	public String getIdVoceRubrica() {
		return idVoceRubrica;
	}
	public void setIdVoceRubrica(String idVoceRubrica) {
		this.idVoceRubrica = idVoceRubrica;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipoIndirizzo() {
		return tipoIndirizzo;
	}
	public void setTipoIndirizzo(String tipoIndirizzo) {
		this.tipoIndirizzo = tipoIndirizzo;
	}
	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}
	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}
	public String getTipoAccount() {
		return tipoAccount;
	}
	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}
	public boolean isFlgPECverificata() {
		return flgPECverificata;
	}
	public void setFlgPECverificata(boolean flgPECverificata) {
		this.flgPECverificata = flgPECverificata;
	}
	public boolean isFlgPresenteInIPA() {
		return flgPresenteInIPA;
	}
	public void setFlgPresenteInIPA(boolean flgPresenteInIPA) {
		this.flgPresenteInIPA = flgPresenteInIPA;
	}
	public String getCodiceIPA() {
		return codiceIPA;
	}
	public void setCodiceIPA(String codiceIPA) {
		this.codiceIPA = codiceIPA;
	}
	public String getTipoSoggettoRif() {
		return tipoSoggettoRif;
	}
	public void setTipoSoggettoRif(String tipoSoggettoRif) {
		this.tipoSoggettoRif = tipoSoggettoRif;
	}
	public boolean isFlgValido() {
		return flgValido;
	}
	public void setFlgValido(boolean flgValido) {
		this.flgValido = flgValido;
	}
	public Date getDataIns() {
		return dataIns;
	}
	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}
	public String getUtenteIns() {
		return utenteIns;
	}
	public void setUtenteIns(String utenteIns) {
		this.utenteIns = utenteIns;
	}
	public Date getDataUltMod() {
		return dataUltMod;
	}
	public void setDataUltMod(Date dataUltMod) {
		this.dataUltMod = dataUltMod;
	}
	public String getUtenteUltMod() {
		return utenteUltMod;
	}
	public void setUtenteUltMod(String utenteUltMod) {
		this.utenteUltMod = utenteUltMod;
	}
	public String getSigillo() {
		return sigillo;
	}
	public void setSigillo(String sigillo) {
		this.sigillo = sigillo;
	}
}