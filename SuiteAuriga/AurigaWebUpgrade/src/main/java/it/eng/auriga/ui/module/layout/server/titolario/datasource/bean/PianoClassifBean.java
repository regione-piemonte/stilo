/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class PianoClassifBean extends VisualBean {

	@NumeroColonna(numero="1")
	private String idPianoClassif;
	@NumeroColonna(numero="2")
	private String dataInizio;
	@NumeroColonna(numero="3")
	private String dataFine;
	@NumeroColonna(numero="4")
	private String note;
	@NumeroColonna(numero="5")
	private String flgInVigore;
	@NumeroColonna(numero="10")
	private String display;	
	@NumeroColonna(numero="11")
	private String nroLivelli;
	
	public String getIdPianoClassif() {
		return idPianoClassif;
	}
	public void setIdPianoClassif(String idPianoClassif) {
		this.idPianoClassif = idPianoClassif;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getFlgInVigore() {
		return flgInVigore;
	}
	public void setFlgInVigore(String flgInVigore) {
		this.flgInVigore = flgInVigore;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getNroLivelli() {
		return nroLivelli;
	}
	public void setNroLivelli(String nroLivelli) {
		this.nroLivelli = nroLivelli;
	}
	
}
