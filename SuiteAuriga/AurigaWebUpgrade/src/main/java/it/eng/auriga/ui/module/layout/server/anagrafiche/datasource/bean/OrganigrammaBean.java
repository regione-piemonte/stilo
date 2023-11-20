/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

import java.util.Date;

public class OrganigrammaBean extends VisualBean {

	private String id;

	@NumeroColonna(numero = "1")
	private String typeNodo;

	@NumeroColonna(numero = "2")
	private String idUo;

	@NumeroColonna(numero = "3")
	private String codice;

	@NumeroColonna(numero = "4")
	private String descrizione;

	@NumeroColonna(numero = "5")
	private String descrizioneEstesa;

	@NumeroColonna(numero = "8")
	private String flgSelXFinalita;

	@NumeroColonna(numero = "9")
	private Boolean flgPuntoProtocollo;

	@NumeroColonna(numero = "10")
	private String tipoUo;

	private Date dataInizioValidita;
	private Date dataFineValidita;

	private String descrizioneOrig;

	private String iconaTypeNodo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeNodo() {
		return typeNodo;
	}

	public void setTypeNodo(String typeNodo) {
		this.typeNodo = typeNodo;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public String getFlgSelXFinalita() {
		return flgSelXFinalita;
	}

	public void setFlgSelXFinalita(String flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}

	public Boolean getFlgPuntoProtocollo() {
		return flgPuntoProtocollo;
	}

	public void setFlgPuntoProtocollo(Boolean flgPuntoProtocollo) {
		this.flgPuntoProtocollo = flgPuntoProtocollo;
	}

	public String getTipoUo() {
		return tipoUo;
	}

	public void setTipoUo(String tipoUo) {
		this.tipoUo = tipoUo;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getDescrizioneOrig() {
		return descrizioneOrig;
	}

	public void setDescrizioneOrig(String descrizioneOrig) {
		this.descrizioneOrig = descrizioneOrig;
	}

	public String getIconaTypeNodo() {
		return iconaTypeNodo;
	}

	public void setIconaTypeNodo(String iconaTypeNodo) {
		this.iconaTypeNodo = iconaTypeNodo;
	}

}
