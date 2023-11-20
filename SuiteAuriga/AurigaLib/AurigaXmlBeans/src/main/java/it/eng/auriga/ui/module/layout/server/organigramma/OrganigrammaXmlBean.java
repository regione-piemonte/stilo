/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author DANCRIST
 *
 */

public class OrganigrammaXmlBean {

	@NumeroColonna(numero = "1")
	private String tipo;
	
	@NumeroColonna(numero = "3")
	private String nome;
	
	@NumeroColonna(numero = "4")
	private String nroLivello;
	
	@NumeroColonna(numero = "5")
	private String tipoUO;
	
	@NumeroColonna(numero = "6")
	private String descrTipo;
	
	@NumeroColonna(numero = "7")
	private String idUoSvUt;
	
	@NumeroColonna(numero = "8")
	private String denominazioneEstesa;
	
	@NumeroColonna(numero = "9")
	private String idUo;
	
	@NumeroColonna(numero = "10")
	private String idUser;
	
	@NumeroColonna(numero = "11")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date tsInizio;
	
	@NumeroColonna(numero = "12")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date tsFine;
	
	@NumeroColonna(numero = "14")
	private String codRapidoUo;
	
	@NumeroColonna(numero = "16")
	private String descrUoSvUt;
	
	@NumeroColonna(numero = "18")
	private String idRuolo;
	
	@NumeroColonna(numero = "19")
	private String ruolo;
	
	@NumeroColonna(numero = "20")
	private String username;
	
	@NumeroColonna(numero = "21")
	private String titolo;
	
	@NumeroColonna(numero = "22")
	private String qualifica;
	
	@NumeroColonna(numero = "23")
	private String competenzefunzioni;
	
	@NumeroColonna(numero = "25")
	private String flgPubblicataSuIpa;
	
	@NumeroColonna(numero = "26")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtPubblicazioneIpa;
	
	@NumeroColonna(numero = "27")
	private String indirizzo;
	
	@NumeroColonna(numero = "28")
	private String telefono;
	
	@NumeroColonna(numero = "29")
	private String email;
	
	@NumeroColonna(numero = "30")
	private String fax;
	
	@NumeroColonna(numero = "31")
	private String tipoRelUtenteUo;
	
	@NumeroColonna(numero = "35")
	private Boolean flgSelXFinalita;
	
	@NumeroColonna(numero = "36")
	private String idUoSup;
	
	@NumeroColonna(numero = "38")
	private String denomUoSup;
	
	@NumeroColonna(numero = "39")
	private BigDecimal score;

	@NumeroColonna(numero = "40")
	private String codRapidoSvUt;
	
	@NumeroColonna(numero = "41")
	private String codRapidoUoXOrd;
	
	@NumeroColonna(numero = "42")
	private String codFiscale;
	
	@NumeroColonna(numero = "43")
	private String idRubrica;
	
	@NumeroColonna(numero = "44")
	private Boolean flgInibitaAssegnazione;
	
	@NumeroColonna(numero = "45")
	private Boolean flgInibitoInvioCC;
	
	@NumeroColonna(numero = "46")
	private String profilo;
	
	@NumeroColonna(numero = "47")
	private Boolean flgPuntoProtocollo;
	
	@NumeroColonna(numero = "50")
	private String nomePostazioneInPrecedenza;
	
	@NumeroColonna(numero = "51")
	private Boolean flgDestinatarioNeiPreferiti;
	
	@NumeroColonna(numero = "52")
	private String flgSelXAssegnazione;
	
	@NumeroColonna(numero = "55")
	private Boolean flgProtocollista;
	
	@NumeroColonna(numero = "56")
	private String collegataAPuntoProt;
	
	@NumeroColonna(numero = "57")
	private Boolean flgInibitaAssegnazioneEmail;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNroLivello() {
		return nroLivello;
	}

	public void setNroLivello(String nroLivello) {
		this.nroLivello = nroLivello;
	}

	public String getTipoUO() {
		return tipoUO;
	}

	public void setTipoUO(String tipoUO) {
		this.tipoUO = tipoUO;
	}

	public String getDescrTipo() {
		return descrTipo;
	}

	public void setDescrTipo(String descrTipo) {
		this.descrTipo = descrTipo;
	}

	public String getIdUoSvUt() {
		return idUoSvUt;
	}

	public void setIdUoSvUt(String idUoSvUt) {
		this.idUoSvUt = idUoSvUt;
	}

	public String getDenominazioneEstesa() {
		return denominazioneEstesa;
	}

	public void setDenominazioneEstesa(String denominazioneEstesa) {
		this.denominazioneEstesa = denominazioneEstesa;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public Date getTsInizio() {
		return tsInizio;
	}

	public void setTsInizio(Date tsInizio) {
		this.tsInizio = tsInizio;
	}

	public Date getTsFine() {
		return tsFine;
	}

	public void setTsFine(Date tsFine) {
		this.tsFine = tsFine;
	}

	public String getCodRapidoUo() {
		return codRapidoUo;
	}

	public void setCodRapidoUo(String codRapidoUo) {
		this.codRapidoUo = codRapidoUo;
	}

	public String getDescrUoSvUt() {
		return descrUoSvUt;
	}

	public void setDescrUoSvUt(String descrUoSvUt) {
		this.descrUoSvUt = descrUoSvUt;
	}

	public String getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(String idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	public String getCompetenzefunzioni() {
		return competenzefunzioni;
	}

	public void setCompetenzefunzioni(String competenzefunzioni) {
		this.competenzefunzioni = competenzefunzioni;
	}

	public String getFlgPubblicataSuIpa() {
		return flgPubblicataSuIpa;
	}

	public void setFlgPubblicataSuIpa(String flgPubblicataSuIpa) {
		this.flgPubblicataSuIpa = flgPubblicataSuIpa;
	}

	public Date getDtPubblicazioneIpa() {
		return dtPubblicazioneIpa;
	}

	public void setDtPubblicazioneIpa(Date dtPubblicazioneIpa) {
		this.dtPubblicazioneIpa = dtPubblicazioneIpa;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTipoRelUtenteUo() {
		return tipoRelUtenteUo;
	}

	public void setTipoRelUtenteUo(String tipoRelUtenteUo) {
		this.tipoRelUtenteUo = tipoRelUtenteUo;
	}

	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}

	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}

	public String getIdUoSup() {
		return idUoSup;
	}

	public void setIdUoSup(String idUoSup) {
		this.idUoSup = idUoSup;
	}

	public String getDenomUoSup() {
		return denomUoSup;
	}

	public void setDenomUoSup(String denomUoSup) {
		this.denomUoSup = denomUoSup;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getCodRapidoSvUt() {
		return codRapidoSvUt;
	}

	public void setCodRapidoSvUt(String codRapidoSvUt) {
		this.codRapidoSvUt = codRapidoSvUt;
	}

	public String getCodRapidoUoXOrd() {
		return codRapidoUoXOrd;
	}

	public void setCodRapidoUoXOrd(String codRapidoUoXOrd) {
		this.codRapidoUoXOrd = codRapidoUoXOrd;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getIdRubrica() {
		return idRubrica;
	}

	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}

	public String getProfilo() {
		return profilo;
	}

	public void setProfilo(String profilo) {
		this.profilo = profilo;
	}

	public Boolean getFlgPuntoProtocollo() {
		return flgPuntoProtocollo;
	}

	public void setFlgPuntoProtocollo(Boolean flgPuntoProtocollo) {
		this.flgPuntoProtocollo = flgPuntoProtocollo;
	}

	public String getNomePostazioneInPrecedenza() {
		return nomePostazioneInPrecedenza;
	}

	public void setNomePostazioneInPrecedenza(String nomePostazioneInPrecedenza) {
		this.nomePostazioneInPrecedenza = nomePostazioneInPrecedenza;
	}

	public Boolean getFlgDestinatarioNeiPreferiti() {
		return flgDestinatarioNeiPreferiti;
	}

	public void setFlgDestinatarioNeiPreferiti(Boolean flgDestinatarioNeiPreferiti) {
		this.flgDestinatarioNeiPreferiti = flgDestinatarioNeiPreferiti;
	}

	public String getFlgSelXAssegnazione() {
		return flgSelXAssegnazione;
	}

	public void setFlgSelXAssegnazione(String flgSelXAssegnazione) {
		this.flgSelXAssegnazione = flgSelXAssegnazione;
	}

	public Boolean getFlgProtocollista() {
		return flgProtocollista;
	}

	public void setFlgProtocollista(Boolean flgProtocollista) {
		this.flgProtocollista = flgProtocollista;
	}

	public String getCollegataAPuntoProt() {
		return collegataAPuntoProt;
	}

	public void setCollegataAPuntoProt(String collegataAPuntoProt) {
		this.collegataAPuntoProt = collegataAPuntoProt;
	}

	public Boolean getFlgInibitaAssegnazione() {
		return flgInibitaAssegnazione;
	}

	public void setFlgInibitaAssegnazione(Boolean flgInibitaAssegnazione) {
		this.flgInibitaAssegnazione = flgInibitaAssegnazione;
	}

	public Boolean getFlgInibitoInvioCC() {
		return flgInibitoInvioCC;
	}

	public void setFlgInibitoInvioCC(Boolean flgInibitoInvioCC) {
		this.flgInibitoInvioCC = flgInibitoInvioCC;
	}

	public Boolean getFlgInibitaAssegnazioneEmail() {
		return flgInibitaAssegnazioneEmail;
	}

	public void setFlgInibitaAssegnazioneEmail(Boolean flgInibitaAssegnazioneEmail) {
		this.flgInibitaAssegnazioneEmail = flgInibitaAssegnazioneEmail;
	}
	
}