/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class SmistamentoAttiRagioneriaTreeXmlBean {
	
	@NumeroColonna(numero="1")
	private String idNode;
	
	@NumeroColonna(numero="2")
	private String parentId;
	
	@NumeroColonna(numero="3")
	private String nroLivello;
	
	@NumeroColonna(numero="4")
	private String nome;
	
	@NumeroColonna(numero="5")
	private String conteggiNodo;
	
	@NumeroColonna(numero="6")
	private String idProcessType;
	
	@NumeroColonna(numero="7")
	private String assegnatario;
	
	@NumeroColonna(numero="8")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtInoltroRagioneria;

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

	public String getConteggiNodo() {
		return conteggiNodo;
	}

	public void setConteggiNodo(String conteggiNodo) {
		this.conteggiNodo = conteggiNodo;
	}

	public String getIdProcessType() {
		return idProcessType;
	}

	public void setIdProcessType(String idProcessType) {
		this.idProcessType = idProcessType;
	}

	public String getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}

	public Date getDtInoltroRagioneria() {
		return dtInoltroRagioneria;
	}

	public void setDtInoltroRagioneria(Date dtInoltroRagioneria) {
		this.dtInoltroRagioneria = dtInoltroRagioneria;
	}

}