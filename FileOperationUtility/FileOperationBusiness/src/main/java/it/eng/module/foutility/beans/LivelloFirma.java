/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.eng.module.foutility.beans.generated.SigVerifyResultType;

public class LivelloFirma {
	
	private String tipoFirma;
	private List<SigVerifyResultType> listaSigVerifyResultType = new ArrayList<SigVerifyResultType>();
	private List<SigVerifyResultType> listaSigVerifyResultTypeControfirme = new ArrayList<SigVerifyResultType>();
	
	public List<SigVerifyResultType> getListaSigVerifyResultType() {
		return listaSigVerifyResultType;
	}
	public void setListaSigVerifyResultType(List<SigVerifyResultType> listaSigVerifyResultType) {
		this.listaSigVerifyResultType = listaSigVerifyResultType;
	}
	
	public List<SigVerifyResultType> getListaSigVerifyResultTypeControfirme() {
		return listaSigVerifyResultTypeControfirme;
	}
	public void setListaSigVerifyResultTypeControfirme(List<SigVerifyResultType> listaSigVerifyResultTypeControfirme) {
		this.listaSigVerifyResultTypeControfirme = listaSigVerifyResultTypeControfirme;
	}
	
	public String getTipoFirma() {
		return tipoFirma;
	}
	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	@Override
	public String toString() {
		return "LivelloFirma [listaSigVerifyResultType=" + listaSigVerifyResultType
				+ ", listaSigVerifyResultTypeControfirme=" + listaSigVerifyResultTypeControfirme + "]";
	}
	
	
}
