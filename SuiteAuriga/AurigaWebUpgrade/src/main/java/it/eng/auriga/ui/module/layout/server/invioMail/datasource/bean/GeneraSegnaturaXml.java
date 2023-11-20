/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;

import java.util.List;

public class GeneraSegnaturaXml {

	@XmlVariabile(nome="RichConferma", tipo=TipoVariabile.SEMPLICE)
	private Flag richConferma;
	@XmlVariabile(nome="@Destinatari", tipo=TipoVariabile.LISTA)
	private List<InvioUDMailOutDestinatariBean> listaDestinatari;
	@XmlVariabile(nome="IndirizzoMittente", tipo=TipoVariabile.SEMPLICE)
	private String indirizzoMittente;
	@XmlVariabile(nome="IndirizziDestinatari", tipo=TipoVariabile.SEMPLICE)
	private String IndirizziDestinatari;
	@XmlVariabile(nome="IndirizziDestinatariCC", tipo=TipoVariabile.SEMPLICE)
	private String IndirizziDestinatariCC;
	
	public Flag getRichConferma() {
		return richConferma;
	}
	public void setRichConferma(Flag richConferma) {
		this.richConferma = richConferma;
	}
	public List<InvioUDMailOutDestinatariBean> getListaDestinatari() {
		return listaDestinatari;
	}
	public void setListaDestinatari(List<InvioUDMailOutDestinatariBean> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}
	public String getIndirizzoMittente() {
		return indirizzoMittente;
	}
	public void setIndirizzoMittente(String indirizzoMittente) {
		this.indirizzoMittente = indirizzoMittente;
	}
	
	public String getIndirizziDestinatari() {
		return IndirizziDestinatari;
	}
	
	public void setIndirizziDestinatari(String indirizziDestinatari) {
		IndirizziDestinatari = indirizziDestinatari;
	}
	
	public String getIndirizziDestinatariCC() {
		return IndirizziDestinatariCC;
	}
	
	public void setIndirizziDestinatariCC(String indirizziDestinatariCC) {
		IndirizziDestinatariCC = indirizziDestinatariCC;
	}
	
	
}
