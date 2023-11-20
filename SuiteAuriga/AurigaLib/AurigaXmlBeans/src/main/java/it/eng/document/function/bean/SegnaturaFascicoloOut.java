/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SegnaturaFascicoloOut implements Serializable {
	@XmlVariabile(nome="Anno", tipo=TipoVariabile.SEMPLICE)
	private String anno;
	@XmlVariabile(nome="IdClassifica", tipo=TipoVariabile.SEMPLICE)
	private String idClassifica;
	@XmlVariabile(nome="IndiceClassifica", tipo=TipoVariabile.SEMPLICE)
	private String indiceClassifica;
	@XmlVariabile(nome="DesClassifica", tipo=TipoVariabile.SEMPLICE)
	private String desClassifica;
	@XmlVariabile(nome="NroFasc", tipo=TipoVariabile.SEMPLICE)
	private String nroFasc;
	@XmlVariabile(nome="NroSottofasc", tipo=TipoVariabile.SEMPLICE)
	private String nroSottofasc;
	@XmlVariabile(nome="NroInserto", tipo=TipoVariabile.SEMPLICE)
	private String nroInserto;
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getIndiceClassifica() {
		return indiceClassifica;
	}
	public void setIndiceClassifica(String indiceClassifica) {
		this.indiceClassifica = indiceClassifica;
	}
	public String getDesClassifica() {
		return desClassifica;
	}
	public void setDesClassifica(String desClassifica) {
		this.desClassifica = desClassifica;
	}
	public String getNroFasc() {
		return nroFasc;
	}
	public void setNroFasc(String nroFasc) {
		this.nroFasc = nroFasc;
	}
	public String getNroSottofasc() {
		return nroSottofasc;
	}
	public void setNroSottofasc(String nroSottofasc) {
		this.nroSottofasc = nroSottofasc;
	}
	public String getNroInserto() {
		return nroInserto;
	}
	public void setNroInserto(String nroInserto) {
		this.nroInserto = nroInserto;
	}
	public String getIdClassifica() {
		return idClassifica;
	}
	public void setIdClassifica(String idClassifica) {
		this.idClassifica = idClassifica;
	}
	
}
