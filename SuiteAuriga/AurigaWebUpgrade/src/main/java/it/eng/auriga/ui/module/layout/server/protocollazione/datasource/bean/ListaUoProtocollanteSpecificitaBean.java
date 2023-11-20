/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

public class ListaUoProtocollanteSpecificitaBean {

	List<UoProtocollanteBean> uoCollegateUtenteRegistrazioneE;
	List<UoProtocollanteBean> uoCollegateUtenteRegistrazioneUI;
	List<UoProtocollanteBean> uoCollegateUtenteAvviaIterAtto;
	List<UoProtocollanteBean> uoCollegateUtenteSceltaUOLavoro;
	Map<String,String> mappaUoCollegateUtenteRegistrazioneE;
	Map<String,String> mappaUoCollegateUtenteRegistrazioneUI;
	Map<String,String> mappaUoCollegateUtenteAvviaIterAtto;
	Map<String,String> mappaUoCollegateUtenteSceltaUOLavoro;
	Map<String,UoProtocollanteBean> mappaUoCollegateUtenteSceltaUOLavoroValueObject;

	public List<UoProtocollanteBean> getUoCollegateUtenteRegistrazioneE() {
		return uoCollegateUtenteRegistrazioneE;
	}

	public void setUoCollegateUtenteRegistrazioneE(List<UoProtocollanteBean> uoCollegateUtenteRegistrazioneE) {
		this.uoCollegateUtenteRegistrazioneE = uoCollegateUtenteRegistrazioneE;
	}

	public List<UoProtocollanteBean> getUoCollegateUtenteRegistrazioneUI() {
		return uoCollegateUtenteRegistrazioneUI;
	}

	public void setUoCollegateUtenteRegistrazioneUI(List<UoProtocollanteBean> uoCollegateUtenteRegistrazioneUI) {
		this.uoCollegateUtenteRegistrazioneUI = uoCollegateUtenteRegistrazioneUI;
	}

	public List<UoProtocollanteBean> getUoCollegateUtenteAvviaIterAtto() {
		return uoCollegateUtenteAvviaIterAtto;
	}

	public void setUoCollegateUtenteAvviaIterAtto(List<UoProtocollanteBean> uoCollegateUtenteAvviaIterAtto) {
		this.uoCollegateUtenteAvviaIterAtto = uoCollegateUtenteAvviaIterAtto;
	}

	public List<UoProtocollanteBean> getUoCollegateUtenteSceltaUOLavoro() {
		return uoCollegateUtenteSceltaUOLavoro;
	}

	public void setUoCollegateUtenteSceltaUOLavoro(List<UoProtocollanteBean> uoCollegateUtenteSceltaUOLavoro) {
		this.uoCollegateUtenteSceltaUOLavoro = uoCollegateUtenteSceltaUOLavoro;
	}

	public Map<String, String> getMappaUoCollegateUtenteRegistrazioneE() {
		return mappaUoCollegateUtenteRegistrazioneE;
	}

	public void setMappaUoCollegateUtenteRegistrazioneE(Map<String, String> mappaUoCollegateUtenteRegistrazioneE) {
		this.mappaUoCollegateUtenteRegistrazioneE = mappaUoCollegateUtenteRegistrazioneE;
	}

	public Map<String, String> getMappaUoCollegateUtenteRegistrazioneUI() {
		return mappaUoCollegateUtenteRegistrazioneUI;
	}

	public void setMappaUoCollegateUtenteRegistrazioneUI(Map<String, String> mappaUoCollegateUtenteRegistrazioneUI) {
		this.mappaUoCollegateUtenteRegistrazioneUI = mappaUoCollegateUtenteRegistrazioneUI;
	}

	public Map<String, String> getMappaUoCollegateUtenteAvviaIterAtto() {
		return mappaUoCollegateUtenteAvviaIterAtto;
	}

	public void setMappaUoCollegateUtenteAvviaIterAtto(Map<String, String> mappaUoCollegateUtenteAvviaIterAtto) {
		this.mappaUoCollegateUtenteAvviaIterAtto = mappaUoCollegateUtenteAvviaIterAtto;
	}

	public Map<String, String> getMappaUoCollegateUtenteSceltaUOLavoro() {
		return mappaUoCollegateUtenteSceltaUOLavoro;
	}

	public void setMappaUoCollegateUtenteSceltaUOLavoro(Map<String, String> mappaUoCollegateUtenteSceltaUOLavoro) {
		this.mappaUoCollegateUtenteSceltaUOLavoro = mappaUoCollegateUtenteSceltaUOLavoro;
	}

	public Map<String, UoProtocollanteBean> getMappaUoCollegateUtenteSceltaUOLavoroValueObject() {
		return mappaUoCollegateUtenteSceltaUOLavoroValueObject;
	}

	public void setMappaUoCollegateUtenteSceltaUOLavoroValueObject(
			Map<String, UoProtocollanteBean> mappaUoCollegateUtenteSceltaUOLavoroValueObject) {
		this.mappaUoCollegateUtenteSceltaUOLavoroValueObject = mappaUoCollegateUtenteSceltaUOLavoroValueObject;
	}
	
}
