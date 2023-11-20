/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.math.BigDecimal;
import java.util.List;

public class RecuperaFatturaFileBean {

	private BigDecimal idUd;

	@XmlVariabile(nome = "#IdDocPrimario", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idDocPrimario;

	@XmlVariabile(nome = "#FilePrimario", tipo = TipoVariabile.NESTED)
	private RecuperaFatturaFilePrimarioBean filePrimario;

	@XmlVariabile(nome = "#@Allegati", tipo = TipoVariabile.LISTA)
	private List<RecuperaFatturaFileAllegatoBean> allegati;

	@XmlVariabile(nome = "#@RicevuteSdI", tipo = TipoVariabile.LISTA)
	private List<RecuperaFatturaRicevutaSdIBean> ricevuteSdI;

	public BigDecimal getIdUd() {
		return idUd;
	}
	
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	
	public BigDecimal getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(BigDecimal idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public RecuperaFatturaFilePrimarioBean getFilePrimario() {
		return filePrimario;
	}

	public void setFilePrimario(RecuperaFatturaFilePrimarioBean filePrimario) {
		this.filePrimario = filePrimario;
	}

	public List<RecuperaFatturaFileAllegatoBean> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<RecuperaFatturaFileAllegatoBean> allegati) {
		this.allegati = allegati;
	}
	
	public List<RecuperaFatturaRicevutaSdIBean> getRicevuteSdI() {
		return ricevuteSdI;
	}
	
	public void setRicevuteSdI(List<RecuperaFatturaRicevutaSdIBean> ricevuteSdI) {
		this.ricevuteSdI = ricevuteSdI;
	}

	@Override
	public String toString() {
		return String.format("RecuperaFatturaFileBean [idUd=%s, idDocPrimario=%s, filePrimario=%s, allegati=%s, ricevuteSdI=%s]", idUd,
				idDocPrimario, filePrimario, allegati, ricevuteSdI);
	}
}
