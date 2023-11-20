/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.ValueBean;

public class DatiUdOutBean {

	@XmlVariabile(nome="#DesOgg", tipo=TipoVariabile.SEMPLICE)
	private String oggetto;

	@XmlVariabile(nome = "@MittentiEsibenti", tipo = TipoVariabile.LISTA)
	private List<ValueBean> listaMittentiEsibenti;
	
	@XmlVariabile(nome = "FLG_UD_DA_DATA_ENTRY_SCANSIONI", tipo = TipoVariabile.LISTA)
	private Flag flgUdDaDataEntryScansioni;
	
	@XmlVariabile(nome = "#FlgNextDocAllegato", tipo = TipoVariabile.SEMPLICE)
	private Flag flgNextDocAllegato; 
	
	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public List<ValueBean> getListaMittentiEsibenti() {
		return listaMittentiEsibenti;
	}

	public void setListaMittentiEsibenti(List<ValueBean> listaMittentiEsibenti) {
		this.listaMittentiEsibenti = listaMittentiEsibenti;
	}

	public Flag getFlgUdDaDataEntryScansioni() {
		return flgUdDaDataEntryScansioni;
	}

	public void setFlgUdDaDataEntryScansioni(Flag flgUdDaDataEntryScansioni) {
		this.flgUdDaDataEntryScansioni = flgUdDaDataEntryScansioni;
	}

	public Flag getFlgNextDocAllegato() {
		return flgNextDocAllegato;
	}

	public void setFlgNextDocAllegato(Flag flgNextDocAllegato) {
		this.flgNextDocAllegato = flgNextDocAllegato;
	}

}
