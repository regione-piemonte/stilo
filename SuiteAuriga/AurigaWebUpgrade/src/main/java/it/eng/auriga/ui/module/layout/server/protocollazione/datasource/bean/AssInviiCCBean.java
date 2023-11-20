/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

/**
 * 
 * @author dbe4235
 *
 */

public class AssInviiCCBean {
	
	private ProtocollazioneBean dettaglioProtocollo;
	private List<DestinatarioProtBean> listaDestinatariAssegnazioni;
	private List<DestinatarioProtBean> listaDestinatariInviiCC;
	private List<AssegnazioneBean> listaAssegnazioni;
	private List<DestInvioCCBean> listaDestInvioCC;
	
	public ProtocollazioneBean getDettaglioProtocollo() {
		return dettaglioProtocollo;
	}
	public void setDettaglioProtocollo(ProtocollazioneBean dettaglioProtocollo) {
		this.dettaglioProtocollo = dettaglioProtocollo;
	}
	public List<DestinatarioProtBean> getListaDestinatariAssegnazioni() {
		return listaDestinatariAssegnazioni;
	}
	public List<DestinatarioProtBean> getListaDestinatariInviiCC() {
		return listaDestinatariInviiCC;
	}
	public void setListaDestinatariAssegnazioni(List<DestinatarioProtBean> listaDestinatariAssegnazioni) {
		this.listaDestinatariAssegnazioni = listaDestinatariAssegnazioni;
	}
	public void setListaDestinatariInviiCC(List<DestinatarioProtBean> listaDestinatariInviiCC) {
		this.listaDestinatariInviiCC = listaDestinatariInviiCC;
	}
	public List<AssegnazioneBean> getListaAssegnazioni() {
		return listaAssegnazioni;
	}
	public void setListaAssegnazioni(List<AssegnazioneBean> listaAssegnazioni) {
		this.listaAssegnazioni = listaAssegnazioni;
	}
	public List<DestInvioCCBean> getListaDestInvioCC() {
		return listaDestInvioCC;
	}
	public void setListaDestInvioCC(List<DestInvioCCBean> listaDestInvioCC) {
		this.listaDestInvioCC = listaDestInvioCC;
	}
	
}