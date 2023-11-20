/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.List;

public class LimitazioneUO {
	
	@XmlVariabile(nome = "FLG_INIBITA_ASS", tipo=TipoVariabile.SEMPLICE)
	private String flagInibitaAssegnazione;
	
	@XmlVariabile(nome = "FLG_INIBITO_INVIO_CC", tipo=TipoVariabile.SEMPLICE)
	private String flagInibitoInvioCC;
	
	@XmlVariabile(nome = "FLG_PUNTO_PROTOCOLLO", tipo=TipoVariabile.SEMPLICE)
	private String flagPuntoProtocollo;
	
	@XmlVariabile(nome = "DES_BREVE_UO", tipo=TipoVariabile.SEMPLICE)
	private String denominazioneBreveUO;
	
	@XmlVariabile(nome = "ID_UO_PUNTO_PROTOCOLLO_COLL", tipo = TipoVariabile.LISTA)
	private List<PuntoProtocolloCollegatoBean> uoPuntoProtocolloCollegate;

	public String getFlagInibitaAssegnazione() {
		return flagInibitaAssegnazione;
	}

	public void setFlagInibitaAssegnazione(String flagInibitaAssegnazione) {
		this.flagInibitaAssegnazione = flagInibitaAssegnazione;
	}

	public String getFlagInibitoInvioCC() {
		return flagInibitoInvioCC;
	}

	public void setFlagInibitoInvioCC(String flagInibitoInvioCC) {
		this.flagInibitoInvioCC = flagInibitoInvioCC;
	}

	public String getFlagPuntoProtocollo() {
		return flagPuntoProtocollo;
	}

	public void setFlagPuntoProtocollo(String flagPuntoProtocollo) {
		this.flagPuntoProtocollo = flagPuntoProtocollo;
	}

	public List<PuntoProtocolloCollegatoBean> getUoPuntoProtocolloCollegate() {
		return uoPuntoProtocolloCollegate;
	}

	public void setUoPuntoProtocolloCollegate(List<PuntoProtocolloCollegatoBean> uoPuntoProtocolloCollegate) {
		this.uoPuntoProtocolloCollegate = uoPuntoProtocolloCollegate;
	}

	public String getDenominazioneBreveUO() {
		return denominazioneBreveUO;
	}

	public void setDenominazioneBreveUO(String denominazioneBreveUO) {
		this.denominazioneBreveUO = denominazioneBreveUO;
	}

	
	
	
}
