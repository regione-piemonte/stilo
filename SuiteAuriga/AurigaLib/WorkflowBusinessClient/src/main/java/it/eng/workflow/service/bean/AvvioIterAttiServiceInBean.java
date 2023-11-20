/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvvioIterAttiServiceInBean implements Serializable {
	
	private static final long serialVersionUID = 1501561829179574666L;
	
	private String idTipoProc;
	private String nomeTipoProc;
	private String idTipoFlussoActiviti;
	private String idTipoDocProposta;
	private String nomeTipoDocProposta;
	private String siglaProposta;
	private String idUd;
	private String idFolder;
	private String idUoProponente;
	private String oggetto;
	private String idUdRichiesta;
	
	public String getIdTipoProc() {
		return idTipoProc;
	}

	public void setIdTipoProc(String idTipoProc) {
		this.idTipoProc = idTipoProc;
	}

	public String getNomeTipoProc() {
		return nomeTipoProc;
	}

	public void setNomeTipoProc(String nomeTipoProc) {
		this.nomeTipoProc = nomeTipoProc;
	}

	public String getIdTipoFlussoActiviti() {
		return idTipoFlussoActiviti;
	}

	public void setIdTipoFlussoActiviti(String idTipoFlussoActiviti) {
		this.idTipoFlussoActiviti = idTipoFlussoActiviti;
	}

	public String getIdTipoDocProposta() {
		return idTipoDocProposta;
	}

	public void setIdTipoDocProposta(String idTipoDocProposta) {
		this.idTipoDocProposta = idTipoDocProposta;
	}

	public String getNomeTipoDocProposta() {
		return nomeTipoDocProposta;
	}

	public void setNomeTipoDocProposta(String nomeTipoDocProposta) {
		this.nomeTipoDocProposta = nomeTipoDocProposta;
	}

	public String getSiglaProposta() {
		return siglaProposta;
	}

	public void setSiglaProposta(String siglaProposta) {
		this.siglaProposta = siglaProposta;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getIdUoProponente() {
		return idUoProponente;
	}

	public void setIdUoProponente(String idUoProponente) {
		this.idUoProponente = idUoProponente;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getIdUdRichiesta() {
		return idUdRichiesta;
	}

	public void setIdUdRichiesta(String idUdRichiesta) {
		this.idUdRichiesta = idUdRichiesta;
	}
	
}
