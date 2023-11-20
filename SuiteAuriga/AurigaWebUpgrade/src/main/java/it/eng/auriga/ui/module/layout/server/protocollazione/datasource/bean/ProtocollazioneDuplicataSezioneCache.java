/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.MittentiDocumentoBean;

public class ProtocollazioneDuplicataSezioneCache {
	
	@XmlVariabile(nome = "#FlgTipoProv", tipo = TipoVariabile.SEMPLICE)
	private String flgTipoProv;	
	@XmlVariabile(nome = "#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String oggetto;
	@XmlVariabile(nome = "#DtDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtDocRicevuto;
	@XmlVariabile(nome = "#EstremiRegDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String nroProtRicevuto;
	@XmlVariabile(nome = "#AnnoDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String annoProtRicevuto;
	@XmlVariabile(nome = "#RifDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String rifOrigineProtRicevuto;
	@XmlVariabile(nome = "#DtRaccomandata", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtRaccomandata;
	@XmlVariabile(nome = "#NroRaccomandata", tipo = TipoVariabile.SEMPLICE)
	private String nroRaccomandata;;
	@XmlVariabile(nome = "#@MittentiEsterni", tipo = TipoVariabile.LISTA)
	private List<MittentiDocumentoBean> mittentiEsterni;
	@XmlVariabile(nome = "#@DestinatariEsterni", tipo = TipoVariabile.LISTA)
	private List<RegistrazioneDuplicataDestinatariBean> destinatari;
	@XmlVariabile(nome = "#@File", tipo = TipoVariabile.LISTA)
	private List<RegistrazioneDuplicataFileBean> files;
	public String getFlgTipoProv() {
		return flgTipoProv;
	}
	public void setFlgTipoProv(String flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public Date getDtDocRicevuto() {
		return dtDocRicevuto;
	}
	public void setDtDocRicevuto(Date dtDocRicevuto) {
		this.dtDocRicevuto = dtDocRicevuto;
	}
	public String getNroProtRicevuto() {
		return nroProtRicevuto;
	}
	public void setNroProtRicevuto(String nroProtRicevuto) {
		this.nroProtRicevuto = nroProtRicevuto;
	}
	public String getRifOrigineProtRicevuto() {
		return rifOrigineProtRicevuto;
	}
	public void setRifOrigineProtRicevuto(String rifOrigineProtRicevuto) {
		this.rifOrigineProtRicevuto = rifOrigineProtRicevuto;
	}
	public Date getDtRaccomandata() {
		return dtRaccomandata;
	}
	public void setDtRaccomandata(Date dtRaccomandata) {
		this.dtRaccomandata = dtRaccomandata;
	}
	public String getNroRaccomandata() {
		return nroRaccomandata;
	}
	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}
	public List<MittentiDocumentoBean> getMittentiEsterni() {
		return mittentiEsterni;
	}
	public void setMittentiEsterni(List<MittentiDocumentoBean> mittentiEsterni) {
		this.mittentiEsterni = mittentiEsterni;
	}
	public void setDestinatari(List<RegistrazioneDuplicataDestinatariBean> destinatari) {
		this.destinatari = destinatari;
	}
	public List<RegistrazioneDuplicataDestinatariBean> getDestinatari() {
		return destinatari;
	}
	public void setFiles(List<RegistrazioneDuplicataFileBean> files) {
		this.files = files;
	}
	public List<RegistrazioneDuplicataFileBean> getFiles() {
		return files;
	}
	public String getAnnoProtRicevuto() {
		return annoProtRicevuto;
	}
	public void setAnnoProtRicevuto(String annoProtRicevuto) {
		this.annoProtRicevuto = annoProtRicevuto;
	}
}
