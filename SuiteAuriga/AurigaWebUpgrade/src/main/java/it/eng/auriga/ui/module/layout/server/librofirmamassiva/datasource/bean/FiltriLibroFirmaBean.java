/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

public class FiltriLibroFirmaBean {
		
	@XmlVariabile(nome="firmatario", tipo=TipoVariabile.SEMPLICE)
	private String firmatario;
	
	@XmlVariabile(nome="InviatiInFirmaDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvioInFirmaDal;
	
	@XmlVariabile(nome="InviatiInFirmaAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvioInFirmaAl;
	
	@XmlVariabile(nome="ListaIdTipiDoc", tipo=TipoVariabile.SEMPLICE)
	private String listaIdTipiDoc;

	public String getFirmatario() {
		return firmatario;
	}

	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}

	public Date getDataInvioInFirmaDal() {
		return dataInvioInFirmaDal;
	}

	public void setDataInvioInFirmaDal(Date dataInvioInFirmaDal) {
		this.dataInvioInFirmaDal = dataInvioInFirmaDal;
	}

	public Date getDataInvioInFirmaAl() {
		return dataInvioInFirmaAl;
	}

	public void setDataInvioInFirmaAl(Date dataInvioInFirmaAl) {
		this.dataInvioInFirmaAl = dataInvioInFirmaAl;
	}

	public String getListaIdTipiDoc() {
		return listaIdTipiDoc;
	}

	public void setListaIdTipiDoc(String listaIdTipiDoc) {
		this.listaIdTipiDoc = listaIdTipiDoc;
	}
	
	
}
