/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class RespVistiPerfezionamentoCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	
	public RespVistiPerfezionamentoCompletaItem() {
		super("respVistiPerfezionamento", "respVistiPerfezionamentoFromLoadDett", "codUoRespVistiPerfezionamento", "desRespVistiPerfezionamento", "flgRespVistiPerfezionamentoFirmatario", "motiviRespVistiPerfezionamento", "flgRiacqVistoInRitornoIterRespVistiPerfezionamento");		
	}

	public String getAltriParamLoadCombo() {
		return null;
	}

	public boolean showFlgFirmatario() {
		return false;
	}
	
	public boolean showMotivi() {
		return false;
	}

	public boolean isRequiredMotivi() {
		return false;
	}
	
	public boolean showFlgRiacqVistoInRitornoIter() {
		return false;
	}
	
}