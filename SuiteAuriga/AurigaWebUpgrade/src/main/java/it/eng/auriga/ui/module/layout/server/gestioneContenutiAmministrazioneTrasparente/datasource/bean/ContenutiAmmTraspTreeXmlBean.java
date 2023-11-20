/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author ottavio passalacqua
 *
 */

public class ContenutiAmmTraspTreeXmlBean {
	
	@NumeroColonna(numero="1")
	private String idNode;
		
	@NumeroColonna(numero="2")
	private String nome;

	@NumeroColonna(numero="3")
	private String parentId;
	
	@NumeroColonna(numero="4")
	private String flgToAbil;
	
	@NumeroColonna(numero="5")
	private String flgToAbilContenutiTabella;
	
	@NumeroColonna(numero="6")
	private String flgSezApertaATutti;	
	
	@NumeroColonna(numero="7")
	private String livelloGerarchico;
	
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getFlgToAbil() {
		return flgToAbil;
	}

	public void setFlgToAbil(String flgToAbil) {
		this.flgToAbil = flgToAbil;
	}

	public String getFlgToAbilContenutiTabella() {
		return flgToAbilContenutiTabella;
	}

	public void setFlgToAbilContenutiTabella(String flgToAbilContenutiTabella) {
		this.flgToAbilContenutiTabella = flgToAbilContenutiTabella;
	}

	public String getFlgSezApertaATutti() {
		return flgSezApertaATutti;
	}

	public void setFlgSezApertaATutti(String flgSezApertaATutti) {
		this.flgSezApertaATutti = flgSezApertaATutti;
	}

	public String getLivelloGerarchico() {
		return livelloGerarchico;
	}

	public void setLivelloGerarchico(String livelloGerarchico) {
		this.livelloGerarchico = livelloGerarchico;
	}

}