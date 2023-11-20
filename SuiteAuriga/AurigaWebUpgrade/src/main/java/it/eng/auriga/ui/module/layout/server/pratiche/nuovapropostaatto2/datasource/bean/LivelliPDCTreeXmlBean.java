/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author dbe4235
 *
 */

public class LivelliPDCTreeXmlBean {
	
	@NumeroColonna(numero="1")
	private String idNode;
	
	@NumeroColonna(numero="2")
	private String parentId;
	
	@NumeroColonna(numero="3")
	private String nroLivello;
	
	@NumeroColonna(numero="4")
	private String nome;

	public String getIdNode() {
		return idNode;
	}

	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getNroLivello() {
		return nroLivello;
	}

	public void setNroLivello(String nroLivello) {
		this.nroLivello = nroLivello;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
