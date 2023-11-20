/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class AnagraficaSoggettiXmlBean {

	@NumeroColonna(numero = "1")
	private String idSoggetto;	
	
	@NumeroColonna(numero = "2")
	private String denominazione;
	
	@NumeroColonna(numero = "3")
	private String codiceFiscale;

	@NumeroColonna(numero = "4")
	private String partitaIva;	
	
	@NumeroColonna(numero = "5")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataNascitaIstituzione;
	
	@NumeroColonna(numero = "6")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataCessazione;	
	
	private Integer flgIgnoreWarning;	
	
	@NumeroColonna(numero = "7")
	private Integer flgPersFisica;	
	
	@NumeroColonna(numero = "8")
	private String cognome;
	
	@NumeroColonna(numero = "9")
	private String nome;
	
	@NumeroColonna(numero = "11")
	private String titolo;
	
	@NumeroColonna(numero = "12")
	private String sottotipo;

	@NumeroColonna(numero = "13")
	private String condizioneGiuridica;
	
	@NumeroColonna(numero = "14")
	private String causaleCessazione;	
	
	@NumeroColonna(numero = "15")
	private String comuneNascitaIstituzione;
	
	@NumeroColonna(numero = "16")
	private String statoNascitaIstituzione;	
	
	@NumeroColonna(numero = "17")
	private String cittadinanza;

	@NumeroColonna(numero = "18")	
	private Integer flgAnn;
	
	@NumeroColonna(numero = "19")
	private String altreDenominazioni;	
	
	@NumeroColonna(numero = "20")
	private String vecchieDenominazioni;	
	
	@NumeroColonna(numero = "21")
	private String indirizzo;	
	
	@NumeroColonna(numero = "22")
	private Integer flgCertificato;
	
	@NumeroColonna(numero = "23")
	private String estremiCertificazione;	

	@NumeroColonna(numero = "26")
	private String codiceOrigine;		
	
	@NumeroColonna(numero = "27")
	private String codiceIpa;
	
	@NumeroColonna(numero = "28")
	@TipoData(tipo=Tipo.DATA)
	private Date tsIns;

	@NumeroColonna(numero = "29")
	private String uteIns;
	
	@NumeroColonna(numero = "30")	
	@TipoData(tipo=Tipo.DATA)
	private Date tsLastUpd;

	@NumeroColonna(numero = "31")
	private String uteLastUpd;	
	
	@NumeroColonna(numero = "32")	
	private Integer flgDiSistema;

	@NumeroColonna(numero = "33")
	private Integer flgValido;
	
	@NumeroColonna(numero = "34")	
	private String codiceRapido;
	
	@NumeroColonna(numero = "35")
	private String codiceAmmInIpa;
	
	@NumeroColonna(numero = "36")
	private String codiceAooInIpa;
	
	@NumeroColonna(numero = "38")
	private String acronimo;
	
	@NumeroColonna(numero = "40")
	private String email;
	
	@NumeroColonna(numero = "41")
	private String emailPec;
	
	@NumeroColonna(numero = "42")
	private String emailPeo;

	@NumeroColonna(numero = "43")
	private String flgEmailPecPeo;
	
	@NumeroColonna(numero = "44")
	private String tel;
	
	@NumeroColonna(numero = "45")
	private String fax;
	
	@NumeroColonna(numero = "46")
	private String codTipoSoggInt;
	
	@NumeroColonna(numero = "47")
	private String score;
	
	@NumeroColonna(numero = "49")
	private String idUo;
	
	@NumeroColonna(numero = "50")
	private String idUtente;
	
	@NumeroColonna(numero = "51")	
	private String idScrivania;
	
	@NumeroColonna(numero = "53")
	private Boolean flgSelXFinalita;
	
	@NumeroColonna(numero = "54")
	private Integer flgInOrganigramma;	
	
	@NumeroColonna(numero = "55")
	private String tipo;	
	
	@NumeroColonna(numero = "56")
	private String idCliente;	
	
	@NumeroColonna(numero = "57")	
	private String billingAccount;
	
	@NumeroColonna(numero = "58")
	private String cid;
	
	@NumeroColonna(numero = "59")
	private String ipa;	
	
	// Cacco Federico 07-12-2015
	@NumeroColonna(numero = "60")
	private String gruppoDiRiferimento;
	
	@NumeroColonna(numero = "61")
	private String indirizzoCompleto;		
	
	@NumeroColonna(numero = "62")
	private String datiPagamento;		
		
	private String odaNrContratto;	
	
	private String odaCig;
	
	private String odaCup;
	
	private String odaRifAmmInps;
	
	@NumeroColonna(numero = "64")
	private String flgTipoIdFiscale;
	
	private String posizioneFinanziaria;
	
	private String annoPosizioneFinanziaria;
	
	@NumeroColonna(numero = "67")
	private String esigibilitaIva;
	
	@NumeroColonna(numero = "68")
	private Boolean flgSegnoImporti;
	
	@NumeroColonna(numero = "78")
	private String societa;
	
	@NumeroColonna(numero = "82")
	private String rubricaDi;
	
	@NumeroColonna(numero = "83")
	private String flgVisibSottoUo;
	
	@NumeroColonna(numero = "84")
	private String flgGestibSottoUo;
	
	@NumeroColonna(numero = "85")
	private String username;
	
	public String getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public Integer getFlgPersFisica() {
		return flgPersFisica;
	}
	public void setFlgPersFisica(Integer flgPersFisica) {
		this.flgPersFisica = flgPersFisica;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getCondizioneGiuridica() {
		return condizioneGiuridica;
	}
	public void setCondizioneGiuridica(String condizioneGiuridica) {
		this.condizioneGiuridica = condizioneGiuridica;
	}
	public String getCausaleCessazione() {
		return causaleCessazione;
	}
	public void setCausaleCessazione(String causaleCessazione) {
		this.causaleCessazione = causaleCessazione;
	}
	public String getComuneNascitaIstituzione() {
		return comuneNascitaIstituzione;
	}
	public void setComuneNascitaIstituzione(String comuneNascitaIstituzione) {
		this.comuneNascitaIstituzione = comuneNascitaIstituzione;
	}
	public String getStatoNascitaIstituzione() {
		return statoNascitaIstituzione;
	}
	public void setStatoNascitaIstituzione(String statoNascitaIstituzione) {
		this.statoNascitaIstituzione = statoNascitaIstituzione;
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public Integer getFlgAnn() {
		return flgAnn;
	}
	public void setFlgAnn(Integer flgAnn) {
		this.flgAnn = flgAnn;
	}
	public String getAltreDenominazioni() {
		return altreDenominazioni;
	}
	public void setAltreDenominazioni(String altreDenominazioni) {
		this.altreDenominazioni = altreDenominazioni;
	}
	public String getVecchieDenominazioni() {
		return vecchieDenominazioni;
	}
	public void setVecchieDenominazioni(String vecchieDenominazioni) {
		this.vecchieDenominazioni = vecchieDenominazioni;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Integer getFlgCertificato() {
		return flgCertificato;
	}
	public void setFlgCertificato(Integer flgCertificato) {
		this.flgCertificato = flgCertificato;
	}
	public String getEstremiCertificazione() {
		return estremiCertificazione;
	}
	public void setEstremiCertificazione(String estremiCertificazione) {
		this.estremiCertificazione = estremiCertificazione;
	}
	public String getCodiceOrigine() {
		return codiceOrigine;
	}
	public void setCodiceOrigine(String codiceOrigine) {
		this.codiceOrigine = codiceOrigine;
	}
	public String getCodiceIpa() {
		return codiceIpa;
	}
	public void setCodiceIpa(String codiceIpa) {
		this.codiceIpa = codiceIpa;
	}
	public Date getTsIns() {
		return tsIns;
	}
	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}
	public String getUteIns() {
		return uteIns;
	}
	public void setUteIns(String uteIns) {
		this.uteIns = uteIns;
	}
	public Date getTsLastUpd() {
		return tsLastUpd;
	}
	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}
	public String getUteLastUpd() {
		return uteLastUpd;
	}
	public void setUteLastUpd(String uteLastUpd) {
		this.uteLastUpd = uteLastUpd;
	}
	public Integer getFlgDiSistema() {
		return flgDiSistema;
	}
	public void setFlgDiSistema(Integer flgDiSistema) {
		this.flgDiSistema = flgDiSistema;
	}
	public Integer getFlgValido() {
		return flgValido;
	}
	public void setFlgValido(Integer flgValido) {
		this.flgValido = flgValido;
	}
	public String getCodiceRapido() {
		return codiceRapido;
	}
	public void setCodiceRapido(String codiceRapido) {
		this.codiceRapido = codiceRapido;
	}
	public String getCodiceAmmInIpa() {
		return codiceAmmInIpa;
	}
	public void setCodiceAmmInIpa(String codiceAmmInIpa) {
		this.codiceAmmInIpa = codiceAmmInIpa;
	}
	public String getCodiceAooInIpa() {
		return codiceAooInIpa;
	}
	public void setCodiceAooInIpa(String codiceAooInIpa) {
		this.codiceAooInIpa = codiceAooInIpa;
	}
	public String getAcronimo() {
		return acronimo;
	}
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailPec() {
		return emailPec;
	}
	public void setEmailPec(String emailPec) {
		this.emailPec = emailPec;
	}	
	public String getEmailPeo() {
		return emailPeo;
	}
	public void setEmailPeo(String emailPeo) {
		this.emailPeo = emailPeo;
	}
	public String getFlgEmailPecPeo() {
		return flgEmailPecPeo;
	}
	public void setFlgEmailPecPeo(String flgEmailPecPeo) {
		this.flgEmailPecPeo = flgEmailPecPeo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCodTipoSoggInt() {
		return codTipoSoggInt;
	}
	public void setCodTipoSoggInt(String codTipoSoggInt) {
		this.codTipoSoggInt = codTipoSoggInt;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getIdScrivania() {
		return idScrivania;
	}
	public void setIdScrivania(String idScrivania) {
		this.idScrivania = idScrivania;
	}
	public Integer getFlgIgnoreWarning() {
		return flgIgnoreWarning;
	}
	public void setFlgIgnoreWarning(Integer flgIgnoreWarning) {
		this.flgIgnoreWarning = flgIgnoreWarning;
	}
	public Date getDataNascitaIstituzione() {
		return dataNascitaIstituzione;
	}
	public void setDataNascitaIstituzione(Date dataNascitaIstituzione) {
		this.dataNascitaIstituzione = dataNascitaIstituzione;
	}
	public Date getDataCessazione() {
		return dataCessazione;
	}
	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}
	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}
	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}
	public Integer getFlgInOrganigramma() {
		return flgInOrganigramma;
	}
	public void setFlgInOrganigramma(Integer flgInOrganigramma) {
		this.flgInOrganigramma = flgInOrganigramma;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSottotipo() {
		return sottotipo;
	}
	public void setSottotipo(String sottotipo) {
		this.sottotipo = sottotipo;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getCid() {
		return cid;
	}
	public String getBillingAccount() {
		return billingAccount;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public void setBillingAccount(String billingAccount) {
		this.billingAccount = billingAccount;
	}
	public String getIpa() {
		return ipa;
	}
	public void setIpa(String ipa) {
		this.ipa = ipa;
	}
	public String getOdaNrContratto() {
		return odaNrContratto;
	}
	public String getOdaCig() {
		return odaCig;
	}
	public String getOdaCup() {
		return odaCup;
	}
	public String getOdaRifAmmInps() {
		return odaRifAmmInps;
	}
	public void setOdaNrContratto(String odaNrContratto) {
		this.odaNrContratto = odaNrContratto;
	}
	public void setOdaCig(String odaCig) {
		this.odaCig = odaCig;
	}
	public void setOdaCup(String odaCup) {
		this.odaCup = odaCup;
	}
	public void setOdaRifAmmInps(String odaRifAmmInps) {
		this.odaRifAmmInps = odaRifAmmInps;
	}
	public String getFlgTipoIdFiscale() {
		return flgTipoIdFiscale;
	}
	public void setFlgTipoIdFiscale(String flgTipoIdFiscale) {
		this.flgTipoIdFiscale = flgTipoIdFiscale;
	}
	public String getPosizioneFinanziaria() {
		return posizioneFinanziaria;
	}
	public void setPosizioneFinanziaria(String posizioneFinanziaria) {
		this.posizioneFinanziaria = posizioneFinanziaria;
	}
	public String getAnnoPosizioneFinanziaria() {
		return annoPosizioneFinanziaria;
	}
	public void setAnnoPosizioneFinanziaria(String annoPosizioneFinanziaria) {
		this.annoPosizioneFinanziaria = annoPosizioneFinanziaria;
	}
	public String getEsigibilitaIva() {
		return esigibilitaIva;
	}
	public void setEsigibilitaIva(String esigibilitaIva) {
		this.esigibilitaIva = esigibilitaIva;
	}
	public Boolean getFlgSegnoImporti() {
		return flgSegnoImporti;
	}
	public void setFlgSegnoImporti(Boolean flgSegnoImporti) {
		this.flgSegnoImporti = flgSegnoImporti;
	}
	public String getSocieta() {
		return societa;
	}
	public void setSocieta(String societa) {
		this.societa = societa;
	}
	public String getGruppoDiRiferimento() {
		return gruppoDiRiferimento;
	}
	public void setGruppoDiRiferimento(String gruppoDiRiferimento) {
		this.gruppoDiRiferimento = gruppoDiRiferimento;
	}
	public String getIndirizzoCompleto() {
		return indirizzoCompleto;
	}
	public void setIndirizzoCompleto(String indirizzoCompleto) {
		this.indirizzoCompleto = indirizzoCompleto;
	}
	public String getDatiPagamento() {
		return datiPagamento;
	}
	public void setDatiPagamento(String datiPagamento) {
		this.datiPagamento = datiPagamento;
	}
	public String getRubricaDi() {
		return rubricaDi;
	}
	public void setRubricaDi(String rubricaDi) {
		this.rubricaDi = rubricaDi;
	}
	public String getFlgVisibSottoUo() {
		return flgVisibSottoUo;
	}
	public void setFlgVisibSottoUo(String flgVisibSottoUo) {
		this.flgVisibSottoUo = flgVisibSottoUo;
	}
	public String getFlgGestibSottoUo() {
		return flgGestibSottoUo;
	}
	public void setFlgGestibSottoUo(String flgGestibSottoUo) {
		this.flgGestibSottoUo = flgGestibSottoUo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
