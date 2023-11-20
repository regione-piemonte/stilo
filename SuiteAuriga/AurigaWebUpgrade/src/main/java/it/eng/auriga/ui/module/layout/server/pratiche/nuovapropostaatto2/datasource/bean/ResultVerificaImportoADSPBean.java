/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.eng.document.function.bean.DatiContabiliADSPXmlBean;
import it.eng.document.function.bean.ProposteConcorrenti;

public class ResultVerificaImportoADSPBean {
	
	private String defaultMessage;

	private boolean inError;
	
	private List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP = new ArrayList<DatiContabiliADSPXmlBean>();
	
	private List<ProposteConcorrenti> listaProposteConcorrenti = new ArrayList<ProposteConcorrenti>(); 

	public List<DatiContabiliADSPXmlBean> getListaDatiContabiliADSP() {
		return listaDatiContabiliADSP;
	}

	public void setListaDatiContabiliADSP(List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP) {
		this.listaDatiContabiliADSP = listaDatiContabiliADSP;
	}

	public List<ProposteConcorrenti> getListaProposteConcorrenti() {
		return listaProposteConcorrenti;
	}

	public void setListaProposteConcorrenti(List<ProposteConcorrenti> listaProposteConcorrenti) {
		this.listaProposteConcorrenti = listaProposteConcorrenti;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public boolean isInError() {
		return inError;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public void setInError(boolean inError) {
		this.inError = inError;
	}

}
