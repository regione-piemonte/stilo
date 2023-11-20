/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaStampaDistintaSpedizioneBean extends OperazioneMassivaArchivioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String>listMezziTrasmissione;

	private String uri;
	
	// numero di fascicoli selezionati
	private int numFascicoli;
	
	private boolean listaDistintaSpedizioneVuota;

	

	public int getNumFascicoli() {
		return numFascicoli;
	}

	

	public void setNumFascicoli(int numFascicoli) {
		this.numFascicoli = numFascicoli;
	}

	public ArrayList<String> getListMezziTrasmissione() {
		return listMezziTrasmissione;
	}



	public void setListMezziTrasmissione(ArrayList<String> listMezziTrasmissione) {
		this.listMezziTrasmissione = listMezziTrasmissione;
	}



	public String getUri() {
		return uri;
	}



	public void setUri(String uri) {
		this.uri = uri;
	}



	public boolean isListaDistintaSpedizioneVuota() {
		return listaDistintaSpedizioneVuota;
	}



	public void setListaDistintaSpedizioneVuota(boolean listaDistintaSpedizioneVuota) {
		this.listaDistintaSpedizioneVuota = listaDistintaSpedizioneVuota;
	}
	
	



	
}
