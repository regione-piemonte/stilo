/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Map;

public class TreeNodeBean {
	
	private BigDecimal nroLivello;
	private BigDecimal nroProgr;
	private String idNode;	
	private String nome;
	private String tipo;	
	private String dettagli;
	private String flgEsplodiNodo;
	private String idFolder;
	private Boolean flgSelXFinalita;
	private Boolean flgPreferiti;		
	private String parentId;	
	private String idLibreria;
	private Map<String, String> altriAttributi;
	
	public BigDecimal getNroLivello() {
		return nroLivello;
	}
	public void setNroLivello(BigDecimal nroLivello) {
		this.nroLivello = nroLivello;
	}	
	public String getIdNode() {
		return idNode;
	}
	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}	
	
	public String getFlgEsplodiNodo() {
		return flgEsplodiNodo;
	}
	public void setFlgEsplodiNodo(String flgEsplodiNodo) {
		this.flgEsplodiNodo = flgEsplodiNodo;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}	
	public Boolean getFlgPreferiti() {
		return flgPreferiti;
	}
	public void setFlgPreferiti(Boolean flgPreferiti) {
		this.flgPreferiti = flgPreferiti;
	}
	public String getDettagli() {
		return dettagli;
	}
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}
	public BigDecimal getNroProgr() {
		return nroProgr;
	}
	public void setNroProgr(BigDecimal nroProgr) {
		this.nroProgr = nroProgr;
	}
	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}
	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}
	public String getIdLibreria() {
		return idLibreria;
	}
	public void setIdLibreria(String idLibreria) {
		this.idLibreria = idLibreria;
	}
	public Map<String, String> getAltriAttributi() {
		return altriAttributi;
	}
	public void setAltriAttributi(Map<String, String> altriAttributi) {
		this.altriAttributi = altriAttributi;
	}	
	
}
