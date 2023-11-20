/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FiltriXML implements Serializable {
	
	@XmlVariabile(nome="@SigleRegistri", tipo=TipoVariabile.LISTA)
	private List<SiglaRegistro> sigleRegistri;
	@XmlVariabile(nome="DataRegEmergenzaDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataRegEmergenzaDal;
	@XmlVariabile(nome="DataRegEmergenzaAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataRegEmergenzaAl;
	@XmlVariabile(nome="NroRegEmergenzaDa", tipo=TipoVariabile.SEMPLICE)
	private String nroRegEmergenzaDa;
	@XmlVariabile(nome="NroRegEmergenzaA", tipo=TipoVariabile.SEMPLICE)
	private String nroRegEmergenzaA;
	@XmlVariabile(nome="RegistratiDaMe", tipo=TipoVariabile.SEMPLICE)
	private String registratiDaMe;
	@XmlVariabile(nome="Mittente", tipo=TipoVariabile.SEMPLICE)
	private String mittente;
	@XmlVariabile(nome="Destinatario", tipo=TipoVariabile.SEMPLICE)
	private String destinatario;
	@XmlVariabile(nome="Oggetto", tipo=TipoVariabile.SEMPLICE)
	private String oggetto;
	
	public List<SiglaRegistro> getSigleRegistri() {
		return sigleRegistri;
	}
	public void setSigleRegistri(List<SiglaRegistro> sigleRegistri) {
		this.sigleRegistri = sigleRegistri;
	}
	public Date getDataRegEmergenzaDal() {
		return dataRegEmergenzaDal;
	}
	public void setDataRegEmergenzaDal(Date dataRegEmergenzaDal) {
		this.dataRegEmergenzaDal = dataRegEmergenzaDal;
	}
	public Date getDataRegEmergenzaAl() {
		return dataRegEmergenzaAl;
	}
	public void setDataRegEmergenzaAl(Date dataRegEmergenzaAl) {
		this.dataRegEmergenzaAl = dataRegEmergenzaAl;
	}
	public String getNroRegEmergenzaDa() {
		return nroRegEmergenzaDa;
	}
	public void setNroRegEmergenzaDa(String nroRegEmergenzaDa) {
		this.nroRegEmergenzaDa = nroRegEmergenzaDa;
	}
	public String getNroRegEmergenzaA() {
		return nroRegEmergenzaA;
	}
	public void setNroRegEmergenzaA(String nroRegEmergenzaA) {
		this.nroRegEmergenzaA = nroRegEmergenzaA;
	}
	public String getRegistratiDaMe() {
		return registratiDaMe;
	}
	public void setRegistratiDaMe(String registratiDaMe) {
		this.registratiDaMe = registratiDaMe;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
}