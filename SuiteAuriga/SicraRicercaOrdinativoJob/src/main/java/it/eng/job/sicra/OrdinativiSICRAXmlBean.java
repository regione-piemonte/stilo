/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class OrdinativiSICRAXmlBean {
	
	@NumeroColonna(numero = "1")
	private int codiceLiquidazione;

	@NumeroColonna(numero = "2")
	private int codiceAnnualeLiquidazione;

	@NumeroColonna(numero = "3")
	private String liquidazioneAnno;
	
	@NumeroColonna(numero = "4")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date liquidazioneData;
	
	@NumeroColonna(numero = "5")
	private String liquidazioneOggetto;
	
	@NumeroColonna(numero = "6")
	private String liquidazioneTipo;
	
	@NumeroColonna(numero = "7")
	private String ordinativoTipo;
	
	@NumeroColonna(numero = "8")
	private int ordinativoNumero;
	
	@NumeroColonna(numero = "9")
	private int ordinativoAnno;
	
	@NumeroColonna(numero = "10")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date ordinativoData;

	@NumeroColonna(numero = "11")
	private String ordinativoCausale;

	@NumeroColonna(numero = "12")
	private int ordinativoAnnullato;

	@NumeroColonna(numero = "13")
	private String benefOridinativoDes;
	
	@NumeroColonna(numero = "14")
	private String benefOridinativoCf;
	
	@NumeroColonna(numero = "15")
	private String benefOridinativoPiva;

	@NumeroColonna(numero = "16")
	private String benefOridinativoImpLord;
	
	@NumeroColonna(numero = "17")
	private String benefOridinativoImpRitenute;
	
	@NumeroColonna(numero = "18")
	private String benefOridinativoImpNetto;
	
	@NumeroColonna(numero = "19")
	private int quietanzaNumero;
	
	@NumeroColonna(numero = "20")
	private int quietanzaAnno;

	@NumeroColonna(numero = "21")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date quietanzaData;

    @NumeroColonna(numero = "22")
	private String quietanzaImporto;

	public int getCodiceLiquidazione() {
		return codiceLiquidazione;
	}

	public void setCodiceLiquidazione(int codiceLiquidazione) {
		this.codiceLiquidazione = codiceLiquidazione;
	}

	public int getCodiceAnnualeLiquidazione() {
		return codiceAnnualeLiquidazione;
	}

	public void setCodiceAnnualeLiquidazione(int codiceAnnualeLiquidazione) {
		this.codiceAnnualeLiquidazione = codiceAnnualeLiquidazione;
	}

	public String getLiquidazioneAnno() {
		return liquidazioneAnno;
	}

	public void setLiquidazioneAnno(String liquidazioneAnno) {
		this.liquidazioneAnno = liquidazioneAnno;
	}

	public Date getLiquidazioneData() {
		return liquidazioneData;
	}

	public void setLiquidazioneData(Date liquidazioneData) {
		this.liquidazioneData = liquidazioneData;
	}

	public String getLiquidazioneOggetto() {
		return liquidazioneOggetto;
	}

	public void setLiquidazioneOggetto(String liquidazioneOggetto) {
		this.liquidazioneOggetto = liquidazioneOggetto;
	}

	public String getLiquidazioneTipo() {
		return liquidazioneTipo;
	}

	public void setLiquidazioneTipo(String liquidazioneTipo) {
		this.liquidazioneTipo = liquidazioneTipo;
	}

	public String getOrdinativoTipo() {
		return ordinativoTipo;
	}

	public void setOrdinativoTipo(String ordinativoTipo) {
		this.ordinativoTipo = ordinativoTipo;
	}

	public int getOrdinativoNumero() {
		return ordinativoNumero;
	}

	public void setOrdinativoNumero(int ordinativoNumero) {
		this.ordinativoNumero = ordinativoNumero;
	}

	public int getOrdinativoAnno() {
		return ordinativoAnno;
	}

	public void setOrdinativoAnno(int ordinativoAnno) {
		this.ordinativoAnno = ordinativoAnno;
	}

	public Date getOrdinativoData() {
		return ordinativoData;
	}

	public void setOrdinativoData(Date ordinativoData) {
		this.ordinativoData = ordinativoData;
	}

	public String getOrdinativoCausale() {
		return ordinativoCausale;
	}

	public void setOrdinativoCausale(String ordinativoCausale) {
		this.ordinativoCausale = ordinativoCausale;
	}

	

	public int getOrdinativoAnnullato() {
		return ordinativoAnnullato;
	}

	public void setOrdinativoAnnullato(int ordinativoAnnullato) {
		this.ordinativoAnnullato = ordinativoAnnullato;
	}

	public String getBenefOridinativoDes() {
		return benefOridinativoDes;
	}

	public void setBenefOridinativoDes(String benefOridinativoDes) {
		this.benefOridinativoDes = benefOridinativoDes;
	}

	public String getBenefOridinativoCf() {
		return benefOridinativoCf;
	}

	public void setBenefOridinativoCf(String benefOridinativoCf) {
		this.benefOridinativoCf = benefOridinativoCf;
	}

	public String getBenefOridinativoPiva() {
		return benefOridinativoPiva;
	}

	public void setBenefOridinativoPiva(String benefOridinativoPiva) {
		this.benefOridinativoPiva = benefOridinativoPiva;
	}

	public String getBenefOridinativoImpLord() {
		return benefOridinativoImpLord;
	}

	public void setBenefOridinativoImpLord(String benefOridinativoImpLord) {
		this.benefOridinativoImpLord = benefOridinativoImpLord;
	}

	public String getBenefOridinativoImpRitenute() {
		return benefOridinativoImpRitenute;
	}

	public void setBenefOridinativoImpRitenute(String benefOridinativoImpRitenute) {
		this.benefOridinativoImpRitenute = benefOridinativoImpRitenute;
	}

	public String getBenefOridinativoImpNetto() {
		return benefOridinativoImpNetto;
	}

	public void setBenefOridinativoImpNetto(String benefOridinativoImpNetto) {
		this.benefOridinativoImpNetto = benefOridinativoImpNetto;
	}

	public int getQuietanzaNumero() {
		return quietanzaNumero;
	}

	public void setQuietanzaNumero(int quietanzaNumero) {
		this.quietanzaNumero = quietanzaNumero;
	}

	public int getQuietanzaAnno() {
		return quietanzaAnno;
	}

	public void setQuietanzaAnno(int quietanzaAnno) {
		this.quietanzaAnno = quietanzaAnno;
	}

	public Date getQuietanzaData() {
		return quietanzaData;
	}

	public void setQuietanzaData(Date quietanzaData) {
		this.quietanzaData = quietanzaData;
	}

	public String getQuietanzaImporto() {
		return quietanzaImporto;
	}

	public void setQuietanzaImporto(String quietanzaImporto) {
		this.quietanzaImporto = quietanzaImporto;
	}

	
	
}
