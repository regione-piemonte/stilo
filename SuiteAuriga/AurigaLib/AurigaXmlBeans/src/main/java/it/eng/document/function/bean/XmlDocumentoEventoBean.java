/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlDocumentoEventoBean implements Serializable {
	
	private static final long serialVersionUID = 8827729163041008691L;	
	
	@XmlVariabile(nome="#FlgTipoProv", tipo = TipoVariabile.SEMPLICE)
	private TipoProvenienza flgTipoProv;	//se è in entrata metto E altrimenti vuoto
	@XmlVariabile(nome="#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String oggetto;	
	@XmlVariabile(nome="", tipo = TipoVariabile.NESTED)
	private ProtocolloRicevuto protocolloRicevuto;			
	@XmlVariabile(nome="#@RegistrazioniDate", tipo=TipoVariabile.LISTA)
	private List<RegistrazioneData> registrazioniDate;	
	@XmlVariabile(nome="#IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String tipoDocumento;	
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="#DtStesura", tipo = TipoVariabile.SEMPLICE)
	private Date dataStesura;	
	@XmlVariabile(nome = "#NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String nomeDocType;
	
	public TipoProvenienza getFlgTipoProv() {
		return flgTipoProv;
	}
	public void setFlgTipoProv(TipoProvenienza flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}	
	public void setProtocolloRicevuto(ProtocolloRicevuto protocolloRicevuto) {
		this.protocolloRicevuto = protocolloRicevuto;
	}
	public ProtocolloRicevuto getProtocolloRicevuto() {
		return protocolloRicevuto;
	}		
	public List<RegistrazioneData> getRegistrazioniDate() {
		return registrazioniDate;
	}
	public void setRegistrazioniDate(List<RegistrazioneData> registrazioniDate) {
		this.registrazioniDate = registrazioniDate;
	}	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Date getDataStesura() {
		return dataStesura;
	}
	public void setDataStesura(Date dataStesura) {
		this.dataStesura = dataStesura;
	}
	public String getNomeDocType() {
		return nomeDocType;
	}
	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}
	
}
