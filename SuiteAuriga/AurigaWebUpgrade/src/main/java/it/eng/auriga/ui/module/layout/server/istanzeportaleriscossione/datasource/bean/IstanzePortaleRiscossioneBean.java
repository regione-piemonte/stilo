/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

import java.math.BigDecimal;
import java.util.List;

public class IstanzePortaleRiscossioneBean extends IstanzePortaleRiscossioneXmlBean {

	private BigDecimal idDocPrimario;
	private String nomeFilePrimario;
	private String uriFilePrimario;
	private Boolean remoteUriFilePrimario;
	private MimeTypeFirmaBean infoFile;
	private List<AllegatoProtocolloBean> listaAllegati;
	
	private String nome;
	private String segnatura;

	public BigDecimal getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(BigDecimal idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}

	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}

	public String getUriFilePrimario() {
		return uriFilePrimario;
	}

	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}

	public Boolean getRemoteUriFilePrimario() {
		return remoteUriFilePrimario;
	}

	public void setRemoteUriFilePrimario(Boolean remoteUriFilePrimario) {
		this.remoteUriFilePrimario = remoteUriFilePrimario;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}

}